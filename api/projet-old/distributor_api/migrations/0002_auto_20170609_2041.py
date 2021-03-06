# -*- coding: utf-8 -*-
# Generated by Django 1.11.1 on 2017-06-09 18:41
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('distributor_api', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Distributor',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
            ],
        ),
        migrations.CreateModel(
            name='Sensor',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('date', models.DateTimeField(auto_now=True)),
            ],
        ),
        migrations.AddField(
            model_name='distributor',
            name='id_distributor',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='distributor_api.Sensor'),
        ),
        migrations.AddField(
            model_name='distributor',
            name='id_user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='distributor_api.User'),
        ),
    ]
