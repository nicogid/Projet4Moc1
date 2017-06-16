from rest_framework import serializers
from .models import Distributor, Sensor, User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'


class SensorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Sensor
        fields = {'id', 'date'}


class DistributorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Distributor
        fields = {'id', 'id_user', 'id_distributor'}
