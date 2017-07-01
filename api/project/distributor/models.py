from django.db import models

# Create your models here.

class Sensor(models.Model):
    name = models.CharField(max_length=20)
    date = models.DateTimeField(auto_now=True)
