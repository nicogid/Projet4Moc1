from django.contrib import admin

from .models import User, Distributor,Sensor

admin.site.register(User)
admin.site.register(Distributor)
admin.site.register(Sensor)