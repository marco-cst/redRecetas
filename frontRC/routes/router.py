from flask import Flask, json, flash, Blueprint, url_for, jsonify, make_response, request, render_template, redirect, abort
import requests
import datetime
from urllib.parse import quote

router = Blueprint('router', __name__)

@router.route('/admin')
def admin():
    return render_template('index.html')

@router.route('/admin/recipe')
def admin_recipe():
    return render_template('recipe.html')

@router.route('/admin/aboutme')
def admin_about_me():
    return render_template('about-me.html')

@router.route('/admin/blog')
def admin_blog():
    return render_template('blog.html')

@router.route('/admin/categories')
def admin_categories():
    return render_template('categories.html')

@router.route('/admin/contact')
def admin_contact():
    return render_template('contact.html')

@router.route('/admin/template')
def admin_template():
    return render_template('template.html')


    #EJEMPLO CRUD

# @router.route('/admin/inversionista/list')
# def list_inversionista():
#     r = requests.get("http://localhost:8020/api/inversionista/list")
#     data = r.json()
#     return render_template('fragmento/inversionista/lista.html', list=data["data"])

# @router.route('/admin/inversionista/edit/<int:id>')
# def view_edit_inversionista(id):
#     r = requests.get("http://localhost:8020/api/inversionista/list")
#     data = r.json()
#     r1 = requests.get(f"http://localhost:8020/api/inversionista/get/{id}")
    
#     if r1.status_code == 200:
#         data1 = r1.json()
#         return render_template('fragmento/inversionista/actualizar.html', list=data["data"], inversionista=data1["data"])
#     else:
#         flash("Inversionista no encontrado", category='error')
#         return redirect("/admin/inversionista/list")

# @router.route('/admin/inversionista/register')
# def view_register_inversionista():
#     r = requests.get("http://localhost:8020/api/inversionista/list")
#     data = r.json()
#     return render_template('fragmento/inversionista/nwProyecto.html', list=data["data"])

# @router.route('/admin/inversionista/save', methods=["POST"])
# def save_inversionista():
#     headers = {'Content-type': 'application/json'}
#     form = request.form
#     dataF = {
#         "apellido": form["ape"],
#         "nombre": form["nom"],
#         "dni": form["dni"],
#     }
    
#     r = requests.post("http://localhost:8020/api/inversionista/save", data=json.dumps(dataF), headers=headers)
#     dat = r.json()
    
#     if r.status_code == 200:
#         flash("Inversionista guardado correctamente", category='info')
#         registrar_historial("crear", "inversionista", f"Inversionista {form['nom']} {form['ape']} creado")
#     else:
#         flash(str(dat["data"]), category='error')

#     return redirect("/admin/inversionista/list")

# @router.route('/admin/inversionista/delete/<int:id>', methods=["POST"])
# def delete_inversionista(id):
#     r = requests.delete(f"http://localhost:8020/api/inversionista/delete/{id}")
    
#     if r.status_code == 200:
#         flash("Inversionista eliminado exitosamente.", category='info')
#         registrar_historial("eliminar", "inversionista", f"Inversionista con ID {id} eliminado")
#     else:
#         flash("No se pudo eliminar el inversionista.", category='error')
    
#     return redirect(url_for('router.list_inversionista'))

# @router.route('/admin/inversionista/update', methods=["POST"])
# def update_inversionista():
#     headers = {'Content-type': 'application/json'}
#     form = request.form
#     dataF = {
#         "idInversionista": form["id"],
#         "apellido": form["ape"],
#         "nombre": form["nom"],
#         "dni": form["dni"],
#     }
    
#     r = requests.post("http://localhost:8020/api/inversionista/update", data=json.dumps(dataF), headers=headers)
#     dat = r.json()
    
#     if r.status_code == 200:
#         flash("Inversionista actualizado correctamente", category='info')
#         registrar_historial("actualizar", "inversionista", f"Inversionista {form['nom']} {form['ape']} actualizado")
#     else:
#         flash(str(dat["data"]), category='error')

#     return redirect("/admin/inversionista/list")
