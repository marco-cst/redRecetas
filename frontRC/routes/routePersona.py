from flask import Blueprint, abort, request, render_template, redirect, flash
import requests  # Asegúrate de que "requests" esté correctamente importado
import json

router1 = Blueprint('router1', __name__)


@router1.route('/')
def admin():
    return render_template('base.html')




@router1.route('/admin/person/register')
def account_register():
    
        return render_template('fragmento/inicial/cuenta/loginPersona.html')

@router1.route('/admin/person/list')
def family_list():
    r = requests.get("http://localhost:8086/api/person/list")
    data = r.json()
    print(r.json())
    return render_template('/admin/person/register', lista = data["data"])



@router1.route('/admin/family/search/ident/<texto>')
def view_asignar(texto):
        url = "http://localhost:8086/api/person/list/search/ident/"
        r = requests.get(url + texto)
        data1 = r.json()
        
        if(r.status_code == 200):
            if type(data1["data"]) is dict:
                list=[]
                list.append(data1["data"])
                return render_template('/admin/person/register', lista = list)
            else:
                return render_template('/admin/person/register', lista = data1["data"])
        else:
                return render_template("/admin/person/register", error_message=str(data1["data"]))
        

@router1.route('/admin/person/save', methods=['POST'])
def save_receta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_cuenta= {
        "nombre": form["nombre"],
        "apellido": form["apellido"],
        "apodo": form["apodo"],
        "dni": form["dni"],
        
    }

    r_cuenta= requests.post("http://localhost:8086/api/person/save", data=json.dumps(data_cuenta), headers=headers)
    if r_cuenta.status_code == 200:
        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/account/register')
    else:
        error_msg = r_cuenta.json().get("data", "Error al guardar la receta")
        flash(error_msg, category='error')
        return redirect('/admin/person/register')






    