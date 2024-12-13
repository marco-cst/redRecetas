from flask import Flask, json, flash, Blueprint, url_for, jsonify, make_response, request, render_template, redirect, abort
import requests
import datetime
from urllib.parse import quote
from transacciones import Transaccion, guardar_transaccion

router = Blueprint('router', __name__)

@router.route('/')
def admin():
    return render_template('index.html')

@router.route('/admin/receta/register')
def rec_register(msg=''):
    r_receta = requests.get("http://localhost:8086/api/receta/list")
    data_receta = r_receta.json()
    print(data_receta)
    return render_template('registro.html', lista_receta=data_receta["data"])

@router.route('/admin/receta/list')
def list_receta():
    r_receta = requests.get("http://localhost:8086/api/receta/list")
    data_receta = r_receta.json()
    print(data_receta)
    return render_template('lista.html', lista_receta=data_receta["data"])

@router.route('/admin/receta/save', methods=['POST'])
def save_receta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_receta = {
        "nombre": form["nom"],
        "preparacion": form["pre"],
        "porciones": int(form["por"]),
    }

    r_receta = requests.post("http://localhost:8086/api/receta/save", data=json.dumps(data_receta), headers=headers)
    if r_receta.status_code == 200:
        transaccion = Transaccion("Guardar receta", "Se ha guardado una receta nueva.")
        guardar_transaccion(transaccion)

        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/receta/list')
    else:
        error_msg = r_receta.json().get("data", "Error al guardar la receta")
        flash(error_msg, category='error')
        return redirect('/admin/receta/list')

@router.route('/admin/receta/edit/<int:id>', methods=['GET'])
def view_edit_person(id):
    # Obtener la lista de tipos de recetas
    r = requests.get("http://localhost:8086/api/receta/listType")
    lista_tipos = r.json()

    # Obtener los datos de la receta por ID
    r1 = requests.get(f"http://localhost:8086/api/receta/get/{id}")

    if r1.status_code == 200:
        data_receta = r1.json()
        if "data" in data_receta and data_receta["data"]:
            receta = data_receta["data"]
            return render_template('editar.html', lista=lista_tipos["data"], receta=receta)
        else:
            flash("No se encontraron datos para la receta.", category='error')
            return redirect("/admin/receta/list")
    else:
        flash("Error al obtener la receta", category='error')
        return redirect("/admin/receta/list")

@router.route('/admin/receta/update', methods=['POST'])
def update_receta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Crear el diccionario para actualizar la receta
    data_receta = {
        "idReceta": int(form["id"]),
        "nombre": form["nom"],
        "preparacion": form["pre"],
        "porciones": int(form["por"]),
        "favoritos": form.get("favoritos") == 'on'
    }
    # Enviar datos a la API de actualización
    r_receta = requests.post("http://localhost:8086/api/receta/update", data=json.dumps(data_receta), headers=headers)

    if r_receta.status_code == 200:
        transaccion = Transaccion("Actualización receta", {"id": form["id"]})
        guardar_transaccion(transaccion)

        flash("Receta actualizada correctamente", category='info')
        return redirect('/admin/receta/list')
    else:
        error_msg = r_receta.json().get("data", "Error al actualizar la receta")
        flash(error_msg, category='error')
        return redirect('/admin/receta/list')

@router.route('/admin/receta/eliminar/<int:id>', methods=['POST'])
def delete_receta(id):
    r_receta = requests.delete(f"http://localhost:8086/api/receta/delete/{id}")

    if r_receta.status_code == 200:
        transaccion = Transaccion("Eliminar receta", {"id": id})
        guardar_transaccion(transaccion)
        flash("Receta eliminada correctamente", category='info')
    elif r_receta.status_code == 404:
        flash("Receta no encontrada o no pudo ser eliminada", category='warning')
    else:
        flash("Error al intentar eliminar la receta", category='error')

    return redirect('/admin/receta/list')

@router.route('/admin/receta/favoritos/<int:id>', methods=['POST'])
def toggle_favoritos(id):
    # Obtener la receta actual por ID
    r_receta = requests.get(f"http://localhost:8086/api/receta/get/{id}")
    
    if r_receta.status_code == 200:
        receta = r_receta.json().get("data")
        if receta:
            # Invertir el estado de favoritos
            receta["favoritos"] = not receta["favoritos"]
            
            # Actualizar la receta
            headers = {'Content-Type': 'application/json'}
            r_update = requests.post("http://localhost:8086/api/receta/update", 
                                     data=json.dumps(receta), headers=headers)
            
            if r_update.status_code == 200:
                flash("Estado de favoritos actualizado correctamente", category='info')
            else:
                flash("Error al actualizar el estado de favoritos", category='error')
        else:
            flash("Receta no encontrada", category='error')
    else:
        flash("Error al obtener la receta", category='error')
    
    return redirect('/admin/receta/list')

