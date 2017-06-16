from django.db import models


class User(models.Model):
    firstname = models.CharField(max_length=20)
    lastname = models.CharField(max_length=20)
    email = models.CharField(max_length=50)

class Sensor(models.Model):
    date = models.DateTimeField(auto_now=True)

class Distributor(models.Model):
    id_user = models.ForeignKey(User)
    id_distributor = models.ForeignKey(Sensor)





