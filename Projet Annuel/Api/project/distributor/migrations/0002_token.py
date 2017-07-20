# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
import distributor.models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('distributor', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Token',
            fields=[
                ('id', models.AutoField(verbose_name='ID', primary_key=True, serialize=False, auto_created=True)),
                ('hash', models.CharField(max_length=100)),
                ('expiration_date', models.DateTimeField(default=distributor.models.get_expiration_date)),
                ('user', models.OneToOneField(to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
