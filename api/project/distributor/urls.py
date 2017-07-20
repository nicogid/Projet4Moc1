from . import views
from django.conf.urls import url

urlpatterns = [
    url(r'^$', views.home_test),
    url(r'^sensors$', views.list_sensor, name="listsensor"),
    url(r'^addsensor$', views.add_sensor, name="addsensor"),
    url(r'^login/$', views.login, name='login'),
    url(r'^users/$', views.users, name='user'),
    url(r'^sensorauth$',views.sensorsauth, name='sensor-auth'),
    url(r'^sensors/(?P<pk>[0-9]+)/$', views.sensors_detail),
]
