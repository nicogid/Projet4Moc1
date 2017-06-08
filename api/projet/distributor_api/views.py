from _datetime import datetime
from django.shortcuts import render

#Pour importer du html ou du json
from django.http import HttpResponse

def home_test(request):
    return render(request, 'distributor_api/home.html',{'date':datetime.now()})

