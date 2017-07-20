from _datetime import datetime
from base64 import b64decode

from django.contrib.auth import authenticate
from django.contrib.auth.models import User
from django.shortcuts import render

# Pour importer du html ou du json
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework import status
from rest_framework.exceptions import ParseError
from rest_framework.parsers import JSONParser

from .auth import get_or_create_token, get_basic_auth, check_request_token
from .models import  Sensor
from .serializers import SensorSerializer, UserSerializer


def home_test(request):
    return render(request, 'distributor/home.html', {'date': datetime.now()})

@csrf_exempt
def sensors_detail(request, pk):
    """
    Retrieve, update or delete a code snippet.
    """
    try:
        sensor = Sensor.objects.get(pk=pk)
    except Sensor.DoesNotExist:
        return HttpResponse(status=404)

    if request.method == 'GET':
        serializer = SensorSerializer(sensor)
        return JsonResponse(serializer.data)

    elif request.method == 'DELETE':
        sensor.delete()
        return HttpResponse(status=204)

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


def create_user(request):
    try:
        data = JSONParser().parse(request)
    except ParseError:
        return HttpResponse(status=400)
    serializer = UserSerializer(data=data, context={'request': request})
    if serializer.is_valid():
        user = User.objects.create_user(username=data['username'], password=data['password'])
        token = get_or_create_token(user)
        return JsonResponse(data={'token': token.hash})
    else:
        return JsonResponse(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@csrf_exempt
def users(request):
    if request.method == 'POST':
        return create_user(request)
    else:
        return HttpResponse(status=status.HTTP_405_METHOD_NOT_ALLOWED)


@csrf_exempt
def login(request):
    basic = get_basic_auth(request)
    if basic is not None:
        log = b64decode(bytes(basic, 'ascii')).decode('ascii').split(':')
        user = authenticate(username=log[0], password=log[1])
        if user is not None:
            token = get_or_create_token(user)
            return JsonResponse(data={'token': token.hash})
    return HttpResponse(status=status.HTTP_400_BAD_REQUEST)


def listSensor(request):
    sensor = Sensor.objects.all()
    serializer = SensorSerializer(sensor, many=True)
    return JsonResponse(serializer.data, safe=False, status=status.HTTP_200_OK)


def create_sensor(request):
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


@csrf_exempt
def sensorsauth(request):
    if request.method == 'GET':
        return listSensor(request)
    elif request.method == 'POST':
        authorized = check_request_token(request)
        if authorized:
            return create_sensor(request)
        else:
            return HttpResponse(status=status.HTTP_401_UNAUTHORIZED)
    else:
        return HttpResponse(status=status.HTTP_405_METHOD_NOT_ALLOWED)
