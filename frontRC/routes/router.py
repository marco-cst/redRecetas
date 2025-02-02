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

    #dat

@router.route('/admin/resenia/list')
def admin_resenia_list():
    r = requests.get("http://localhost:8086/api/resenia/list")
    data = r.json()
    return render_template('resenia/reseniaList.html', list=data["data"])

@router.route('/admin/resenia/register')
def view_register_resenia():
    r = requests.get("http://localhost:8086/api/resenia/list")
    data = r.json()
    return render_template('resenia/nwResenia.html', list=data["data"])



@router.route('/admin/resenia/saveResenia', methods=["POST"])
def save_resenia():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "comentario": form["comt"], #ape
        "calificacion": form["calf"], #nom
        # "fecha": form["fecha"],
    }
    
    r = requests.post("http://localhost:8086/api/resenia/save", data= json.dumps(dataF), headers=headers)
    dat = r.json()
    
    if r.status_code == 200:
        flash("Resenia guardado correctamente", category='info')
    else:
        flash(str(dat["data"]), category='error')

    return redirect(url_for("router.admin_resenia_list"))

    # return redirect("/admin/resenia/list")
    
#     headers = {'Content-type': 'application/json'}
#     form = request.form
#     print("Datos del formulario:", form)
#     try:
#         dataF = {
#             "nombre": form["nombre"],
#             "latitud": float(form["latitud"]),
#             "longitud": float(form["longitud"]),
#             "tipo": form["tipo"],
#         }
#         print("Datos enviados al backend:", dataF) 
#         r = requests.post("http://localhost:8090/api/universidad/save", data=json.dumps(dataF), headers=headers)
#         dat = r.json()
#         print("Respuesta de la API:", dat)
#         if r.status_code == 200:
#             flash("Sintética guardada correctamente", category='info')
#         else:
#             flash("Error al guardar la sintética", category='error')

#     except ValueError:
#         flash("Error: La latitud y longitud deben ser valores numéricos", category='error')

#     return redirect(url_for("router.list_sintetica"))





@router.route('/admin/resenia/edit/<int:id>', methods=['GET'])
def view_edit_resenia(id):
    r = requests.get("http://localhost:8086/api/resenia/list")
    data = r.json()
    
    # r = requests.get("http://localhost:8086/api/receta/listType")
    # data = r.json() #lista resenia = r json()
    
    r1 = requests.get(f"http://localhost:8086/api/resenia/get/{id}")
    # data1 = r1.json()
   
    if r1.status_code == 200:
        data1 = r1.json()
        if "data" in data1 and data1["data"]:
            resenia = data1["data"]
            return render_template('resenia/actualizar.html', list=data["data"], resenia=resenia)
        else:
            flash("No se encontraron datos para la receta.", category='error')
            return redirect("/admin/resenia/list")
    else:
        flash("Error al obtener la resenia", category='error')
        return redirect("/admin/resenia/list")


@router.route('/admin/resenia/update', methods=['POST'])
def update_resenia():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    dataF = {
        "idResenia": int(form["id"]),
        "comentario": form["comt"],
        "calificacion": int(form["calf"]),
    }
    
    r = requests.post("http://localhost:8086/api/resenia/update", data=json.dumps(dataF), headers=headers)
    # dat = r.json()
    
    if r.status_code == 200:
        # print("Respuesta de la API:", dat)
        flash("resenia actualizado correctamente", category='info')
        return redirect('/admin/resenia/list')
    else:
        error_msg = r.json().get("data", "Error al actualizar la receta")
        flash(error_msg, category='error')
        # flash(str(dat["data"]), category='error')
        return redirect("/admin/resenia/list")


    # if r_receta.status_code == 200:

    #     flash("Receta actualizada correctamente", category='info')
    #     return redirect('/admin/receta/list')
    # else:
    #     error_msg = r_receta.json().get("data", "Error al actualizar la receta")
    #     flash(error_msg, category='error')
    #     return redirect('/admin/receta/list')
    
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
