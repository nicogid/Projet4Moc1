from django.shortcuts import render
from django.http import HttpResponse

def home_test(request):
    """ Exemple de page HTML, non valide pour que l'exemple soit concis """

    text = """<h1>Bienvenue sur l'api !</h1>"""

    return HttpResponse(text)
# Create your views here.
