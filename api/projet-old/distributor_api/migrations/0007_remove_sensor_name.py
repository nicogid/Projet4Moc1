# -*- coding: utf-8 -*-
# Generated by Django 1.11.1 on 2017-06-20 13:06
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('distributor_api', '0006_auto_20170620_1458'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='sensor',
            name='name',
        ),
    ]
