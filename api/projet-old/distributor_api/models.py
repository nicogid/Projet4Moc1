from django.db import models


class User(models.Model):
    firstname = models.CharField(max_length=20)
    lastname = models.CharField(max_length=20)
    email = models.CharField(max_length=50)

class Distributor(models.Model):
    id_user = models.ForeignKey(User)

class Sensor(models.Model):
    id_distributor = models.ForeignKey(Distributor,default=1)
    name = models.CharField(max_length=20,default='PIR')
    date = models.DateTimeField(auto_now=True)







