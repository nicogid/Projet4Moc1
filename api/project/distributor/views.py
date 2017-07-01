from _datetime import datetime

from django.shortcuts import render

# Pour importer du html ou du json
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework import status
from rest_framework.exceptions import ParseError
from rest_framework.parsers import JSONParser

from .models import  Sensor
from .serializers import SensorSerializer


def home_test(request):
    return render(request, 'distributor/home.html', {'date': datetime.now()})


@csrf_exempt
def list_sensor(request):
    if request.method == 'GET':
        sensor = Sensor.objects.all()
        serializer = SensorSerializer(sensor, many=True)
        return JsonResponse(serializer.data, safe=False, status=status.HTTP_200_OK)

@csrf_exempt
def add_sensor(request):
    if request.method == 'POST':
        try:
            data = JSONParser().parse(request)
        except ParseError:
            return HttpResponse(status=400)
        serializer = SensorSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return JsonResponse(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return HttpResponse(status=status.HTTP_405_METHOD_NOT_ALLOWED)
