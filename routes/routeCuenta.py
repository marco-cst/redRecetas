from flask import Blueprint, abort, request, render_template, redirect, flash
import requests  # Asegúrate de que "requests" esté correctamente importado
import json

router = Blueprint('router', __name__)

@router.route('/')
def admin():
    return render_template('index.html')

@router.route('/admin/account/register')
def account_register():
    r_cuenta = requests.get("http://localhost:8086/api/account/list")
    data_cuenta = r_cuenta.json()
    print(data_cuenta)
    return render_template('fragmento/inicial/cuenta/registro.html', lista_receta=data_cuenta["data"])



@router.route('/admin/account/list')
def list_receta():
    r_cuenta= requests.get("http://localhost:8086/api/account/list")
    data_cuenta= r_cuenta.json()
    print(data_cuenta)
    return render_template('fragmento/inicial/cuenta/lista.html', lista_receta=data_cuenta["data"])


@router.route('/admin/account/save', methods=['POST'])
def save_receta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_cuenta= {
        "correo": form["correo"],
        "clave": form["clave"],
        
    }

    r_cuenta= requests.post("http://localhost:8086/api/account/save", data=json.dumps(data_cuenta), headers=headers)
    if r_cuenta.status_code == 200:
       
        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/account/list')
    else:
        error_msg = r_cuenta.json().get("data", "Error al guardar la receta")
        flash(error_msg, category='error')
        return redirect('/admin/account/list')
