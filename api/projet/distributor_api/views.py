from _datetime import datetime
from django.shortcuts import render

#Pour importer du html ou du json
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework import status

from .models import User
from .serializers import UserSerializer


def home_test(request):
    return render(request, 'distributor_api/home.html',{'date':datetime.now()})

@csrf_exempt
def user_list(request):
    if request.method == 'GET':
        users = User.objects.all()
        serializer = UserSerializer(users,many= True)
        return JsonResponse(serializer.data,safe=False,status=status.HTTP_200_OK)
