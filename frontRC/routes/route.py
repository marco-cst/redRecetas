from flask import Blueprint, abort, request, render_template, redirect, flash, url_for, session
import requests  # Asegúrate de que "requests" esté correctamente importado
from functools import wraps

router = Blueprint('router', __name__)

# Decorador para proteger rutas
def login_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if not session.get('logged_in'):
            flash("Esta direccion esta desabilitada", category='error')
            return redirect('/admin/account/login')
        return f(*args, **kwargs)
    return decorated_function


@router.route('/admin/list/account')
@login_required
def account_list():
    r = requests.get("http://localhost:8086/api/account/list")
    data = r.json() 
    return render_template('fragmento/inicial/cuenta/Admin.html',lista = data["data"])



@router.route('/')
def admin():
    return render_template('index.html')



