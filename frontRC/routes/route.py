from flask import Blueprint, abort, request, render_template, redirect, flash, url_for, session, jsonify, json, Flask, make_response, Response

import requests  # Asegúrate de que "requests" esté correctamente importado
from functools import wraps
import datetime
from urllib.parse import quote

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


# ========= resenia ============

@router.route('/admin/resenia/list')
def admin_resenia_list():
    r = requests.get("http://localhost:8086/api/resenia/list")
    data = r.json()
    return render_template('resenia/reseniaList.html', list=data["data"])

@router.route('/admin/resenia/register')
def view_register_resenia():
    r = requests.get("http://localhost:8086/api/resenia/list")
    data = r.json()
    resenia = data.get("resenia", {})  # Obtén resenia de la respuesta JSON
    return render_template('resenia/nwResenia.html', list=data["data"], resenia=resenia)

@router.route('/admin/resenia/saveResenia', methods=["POST"])
def save_resenia():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "comentario": form["comt"], #ape
        "calificacion": form["calf"], #nom
    }
   
    r = requests.post("http://localhost:8086/api/resenia/save", data= json.dumps(dataF), headers=headers)
    dat = r.json()
    
    if r.status_code == 200:
        flash("Resenia guardado correctamente", category='info')
    else:
        flash(str(dat["data"]), category='error')

    return redirect(url_for("router.admin_resenia_list"))

@router.route('/admin/resenia/edit/<int:id>', methods=['GET'])
def view_edit_resenia(id):
    r = requests.get("http://localhost:8086/api/resenia/list")
    data = r.json()

    r1 = requests.get(f"http://localhost:8086/api/resenia/get/{id}")
   
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

@router.route('/admin/resenia/eliminar/<int:id>', methods=['POST'])
def delete_resenia(id):
    r_receta = requests.delete(f"http://localhost:8086/api/resenia/delete/{id}")

    if r_receta.status_code == 200:
        flash("Resenia eliminada correctamente", category='info')
    elif r_receta.status_code == 404:
        flash("Resenia no encontrada o no pudo ser eliminada", category='warning')
    else:
        flash("Error al intentar eliminar la receta", category='error')

    return redirect('/admin/resenia/list')

# ============ categoria ==============

@router.route('/admin/categoria/list')
def admin_categoria_list():
    r = requests.get("http://localhost:8086/api/categoria/list")
    data = r.json()
    return render_template('categoria/categoriaList.html', list=data["data"])

@router.route('/admin/proyecto/list/search/<categoria>')
def view_buscar_receta(categoria):
    try:
        if categoria not in ["SALADO", "DULCE"]:
            return jsonify({"error": "Categoría de búsqueda no válida"}), 400

        # Construir la URL de la API (corregida)
        api_url = f"http://localhost:8086/api/categoria/list/search/lineal/categoria/{categoria}"

        # Hacer la solicitud a la API
        r = requests.get(api_url)
        data = r.json()

        # Verificar la respuesta de la API
        if r.status_code == 200 and "data" in data:
            categorias = data["data"]
            return jsonify(categorias)  # Devolver los datos en formato JSON
        else:
            return jsonify({"error": "No se encontraron resultados"}), 404

    except requests.RequestException as e:
        return jsonify({"error": f"Error de conexión: {str(e)}"}), 500
    
# ============ receta ==============

@router.route('/admin/receta/register')
def rec_register(msg=''):
    r_receta = requests.get("http://localhost:8086/api/receta/list")
    data_receta = r_receta.json()
    print(data_receta)
    return render_template('receta/registro.html', lista_receta=data_receta["data"])

@router.route('/admin/receta/list')
def list_receta():
    r_receta = requests.get("http://localhost:8086/api/receta/list")
    data_receta = r_receta.json()
    print(data_receta)
    return render_template('receta/lista.html', lista_receta=data_receta["data"])

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
            return render_template('receta/editar.html', lista=lista_tipos["data"], receta=receta)
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

# =========== ingredientes ===========

@router.route('/admin/ingre/register')
def ingre_register(msg=''):
    r_ingre = requests.get("http://localhost:8086/api/ingredientes/list")
    data_ingre = r_ingre.json()
    print(data_ingre)
    return render_template('ingredientes/registro.html', lista_ingre=data_ingre["data"])

@router.route('/admin/ingre/list')
def list_ingre():
    r_ingre = requests.get("http://localhost:8086/api/ingredientes/list")
    data_ingre = r_ingre.json()
    print(data_ingre)
    return render_template('ingredientes/lista.html', lista_ingre=data_ingre["data"])

@router.route('/admin/ingre/save', methods=['POST'])
def save_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_ingre = {
        "nombre": form["nom"],
        "cantidad": float(form["can"]),
        "medida": form["med"],
    }

    r_ingre = requests.post("http://localhost:8086/api/ingredientes/save", data=json.dumps(data_ingre), headers=headers)

    if r_ingre.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/ingre/list')
    else:
        flash(r_ingre.json().get("data", "Error"), category='error')
        return redirect('/admin/ingre/list')


@router.route('/admin/ingre/edit/<int:id>', methods=['GET'])
def view_edit_person(id):
    r = requests.get("http://localhost:8086/api/ingredientes/listType")
    lista_tipos = r.json()

    r1 = requests.get(f"http://localhost:8086/api/ingredientes/get/{id}")

    if r1.status_code == 200:
        data_ingre = r1.json()
        if "data" in data_ingre and data_ingre["data"]:
            ingre = data_ingre["data"]
            return render_template('ingredientes/editar.html', lista=lista_tipos["data"], ingre=ingre)
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

    data_ingre = {
        "idIngrediente": int(form["id"]),
        "nombre": form["nom"],
        "cantidad": float(form["can"]),
        "unidadMedida": form["med"],
    }
    r_ingre = requests.post("http://localhost:8086/api/ingredientes/update", data=json.dumps(data_ingre), headers=headers)

    if r_ingre.status_code == 200:
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
        flash("ingre eliminada correctamente", category='info')
    elif r_ingre.status_code == 404:
        flash("ingre no encontrada o no pudo ser eliminada", category='warning')
    else:
        flash("Error al intentar eliminar la ingre", category='error')

    return redirect('/admin/ingre/list')