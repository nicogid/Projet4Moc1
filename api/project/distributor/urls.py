from . import views
from django.conf.urls import url

urlpatterns = [
    url(r'^$', views.home_test),
    url(r'^sensors$', views.list_sensor, name="listsensor"),
    url(r'^addsensor$', views.add_sensor, name="addsensor"),
]
