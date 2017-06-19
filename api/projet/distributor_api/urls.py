from . import views
from django.conf.urls import url

urlpatterns = [
    url(r'^$', views.home_test),
    url(r'^users', views.user_list,name="user-list"),
    url(r'^user/(?P<pk>[0-9]+)/$',views.user_detail,name="user-detail"),
]