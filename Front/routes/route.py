from flask import Blueprint, abort, request, render_template, redirect, flash
import requests  # Asegúrate de que "requests" esté correctamente importado
import json

router = Blueprint('router', __name__)

@router.route('/')
def admin():
    return render_template('index.html')


