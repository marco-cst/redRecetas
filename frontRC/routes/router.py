from flask import Flask, json, flash, Blueprint, url_for, jsonify, make_response, request, render_template, redirect, abort
import requests
import datetime
from urllib.parse import quote
from transacciones import Transaccion, guardar_transaccion

router = Blueprint('router', __name__)

@router.route('/')
def admin():
    return render_template('index.html')

@router.route('/admin/ingre/register')
def ingre_register(msg=''):
    r_ingre = requests.get("http://localhost:8086/api/ingredientes/list")
    data_ingre = r_ingre.json()
    print(data_ingre)
    return render_template('registro.html', lista_ingre=data_ingre["data"])

@router.route('/admin/ingre/list')
def list_ingre():
    r_ingre = requests.get("http://localhost:8086/api/ingredientes/list")
    data_ingre = r_ingre.json()
    print(data_ingre)
    return render_template('lista.html', lista_ingre=data_ingre["data"])

@router.route('/admin/ingre/save', methods=['POST'])
def save_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Crear el diccionario para la familia
    data_ingre = {
        "nombre": form["nom"],
        "cantidad": float(form["can"]),
        "medida": form["med"],
    }

    # Hacer la petición para guardar la familia
    r_ingre = requests.post("http://localhost:8086/api/ingredientes/save", data=json.dumps(data_ingre), headers=headers)

    transaccion = Transaccion("Guardar Ingrediente", "Se ha guardado.")
    guardar_transaccion(transaccion)

    if r_ingre.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/ingre/list')
    else:
        flash(r_ingre.json().get("data", "Error"), category='error')
        return redirect('/admin/ingre/list')


@router.route('/admin/ingre/edit/<int:id>', methods=['GET'])
def view_edit_person(id):
    # Obtener la lista de tipos de ingres
    r = requests.get("http://localhost:8086/api/ingredientes/listType")
    lista_tipos = r.json()

    # Obtener los datos de la ingre por ID
    r1 = requests.get(f"http://localhost:8086/api/ingredientes/get/{id}")

    if r1.status_code == 200:
        data_ingre = r1.json()
        if "data" in data_ingre and data_ingre["data"]:
            ingre = data_ingre["data"]
            return render_template('editar.html', lista=lista_tipos["data"], ingre=ingre)
        else:
            flash("No se encontraron datos para la ingre.", category='error')
            return redirect("/admin/ingre/list")
    else:
        flash("Error al obtener la ingre", category='error')
        return redirect("/admin/ingre/list")

@router.route('/admin/ingre/update', methods=['POST'])
def update_ingre():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Crear el diccionario para actualizar la ingre
    data_ingre = {
        "idIngrediente": int(form["id"]),
        "nombre": form["nom"],
        "cantidad": float(form["can"]),
        "unidadMedida": form["med"],
    }
    # Enviar datos a la API de actualización
    r_ingre = requests.post("http://localhost:8086/api/ingredientes/update", data=json.dumps(data_ingre), headers=headers)

    if r_ingre.status_code == 200:
        transaccion = Transaccion("Actualización ingre", {"id": form["id"]})
        guardar_transaccion(transaccion)

        flash("ingre actualizada correctamente", category='info')
        return redirect('/admin/ingre/list')
    else:
        error_msg = r_ingre.json().get("data", "Error al actualizar la ingre")
        flash(error_msg, category='error')
        return redirect('/admin/ingre/list')

@router.route('/admin/ingre/eliminar/<int:id>', methods=['POST'])
def delete_ingre(id):
    r_ingre = requests.delete(f"http://localhost:8086/api/ingredientes/delete/{id}")

    if r_ingre.status_code == 200:
        transaccion = Transaccion("Eliminar ingre", {"id": id})
        guardar_transaccion(transaccion)
        flash("ingre eliminada correctamente", category='info')
    elif r_ingre.status_code == 404:
        flash("ingre no encontrada o no pudo ser eliminada", category='warning')
    else:
        flash("Error al intentar eliminar la ingre", category='error')

    return redirect('/admin/ingre/list')


