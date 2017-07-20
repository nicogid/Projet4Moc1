from django.db import models
import datetime

# Create your models here.
from django.utils import timezone

def get_expiration_date():
    return timezone.now() + datetime.timedelta(days=1)

class Sensor(models.Model):
    name = models.CharField(max_length=20)
    date = models.DateTimeField(auto_now=True)

class Token(models.Model):
    user = models.OneToOneField('auth.User', on_delete=models.CASCADE, unique=True)
    hash = models.CharField(max_length=100)
    expiration_date = models.DateTimeField(default=get_expiration_date)

    def is_expired(self):
        return self.expiration_date < timezone.now()

    def __str__(self):
        return "%s" % self.hash