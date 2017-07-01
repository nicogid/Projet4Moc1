from rest_framework import serializers
from .models import Distributor, Sensor, User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

class DistributorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Distributor
        fields = '__all__'

class SensorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Sensor
        fields = '__all__'


