from _datetime import datetime

from django.shortcuts import render

# Pour importer du html ou du json
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework import status
from rest_framework.exceptions import ParseError
from rest_framework.parsers import JSONParser

from .models import User, Distributor, Sensor
from .serializers import UserSerializer, SensorSerializer, DistributorSerializer


def home_test(request):
    return render(request, 'distributor_api/home.html', {'date': datetime.now()})


@csrf_exempt
def user_list(request):
    if request.method == 'GET':
        users = User.objects.all()
        serializer = UserSerializer(users, many=True)
        return JsonResponse(serializer.data, safe=False, status=status.HTTP_200_OK)


@csrf_exempt
def user_detail(request, pk):
    try:
        user =User.objects.get(pk=pk)
    except User.DoesNotExist:
        return HttpResponse(status=status.HTTP_404_NOT_FOUND)
    if request.method == 'GET':
        serializer = UserSerializer(user)
        return JsonResponse(serializer.data,status=status.HTTP_200_OK)
    elif request.method == 'PUT':
        try:
            data = JSONParser().parse(request)
        except ParseError:
            return HttpResponse(status=status.HTTP_400_BAD_REQUEST)
        serializer =UserSerializer(user,data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data,status=status.HTTP_200_OK)
        else:
            return JsonResponse(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method =='DELETE':
        user.delete()
        return HttpResponse(status=status.HTTP_204_NO_CONTENT)

    else:
        return HttpResponse(status=status.HTTP_405_METHOD_NOT_ALLOWED)

@csrf_exempt
def test_sensor(request,pk):
    try:
        user = User.objects.get(pk=pk)

    except User.DoesNotExist:
        return HttpResponse(status=status.HTTP_404_NOT_FOUND)
    if request.method == 'GET':
        try:
            distributor = Distributor.objects.get(id_user__exact=user.id)
        except Distributor.DoesNotExist:
            return HttpResponse(status=status.HTTP_404_NOT_FOUND)
        try:
            sensor = Sensor.objects.filter(id_distributor__exact=distributor.id)
        except Sensor.DoesNotExist:
            return HttpResponse(status=status.HTTP_404_NOT_FOUND)
        #serializer = SensorSerializer(s,many=True)
        # return JsonResponse(serializer.data,status=status.HTTP_200_OK)
        serializer = SensorSerializer(sensor)
        return JsonResponse(serializer.data,safe=False,status=status.HTTP_200_OK)

    else:
        return HttpResponse(status=status.HTTP_405_METHOD_NOT_ALLOWED)


# a corriger post qui doit ecrir dedans

@csrf_exempt
def add_sensor(request,pk):
    if request.method == 'POST':
        try:
            user = User.objects.get(pk=pk)
            try:
                Distributor.objects.get(id_user__exact=user.id)
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
            except Distributor.DoesNotExist:
                return HttpResponse(status=status.HTTP_404_NOT_FOUND)
        except User.DoesNotExist:
            return HttpResponse(status=status.HTTP_404_NOT_FOUND)
    else:
        return HttpResponse(status=status.HTTP_405_METHOD_NOT_ALLOWED)


#test add sensor partiel

@csrf_exempt
def add_sensor_any_user(request):
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