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

    #categoria

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


# no necesarios CREATE, DELETE, ya que la categoria es fija, no debe ser cambiada,
# borrada por el usuario.

# @router.route('/admin/categoria/register')
# def view_register_categoria():
#     r = requests.get("http://localhost:8086/api/categoria/list")
#     data = r.json()
#     return render_template('categoria/nwCategoria.html', list=data["data"])

# @router.route('/admin/categoria/save', methods=["POST"])
# def save_rssenia():
#     headers = {'Content-type': 'application/json'}
#     form = request.form
#     dataF = {
#         "tipo": form["tp"], #ape
#         "estado": form["est"], #nom
#         # "fecha": form["fecha"],
#     }
    
#     r = requests.post("http://localhost:8086/api/categoria/save", data=json.dumps(dataF), headers=headers)
#     dat = r.json()
    
#     if r.status_code == 200:
#         flash("categoria guardado correctamente", category='info')
#     else:
#         flash(str(dat["data"]), category='error')

#     return redirect("/admin/categoria/list")

# @router.route('/admin/categoria/edit')
# def view_edit_inversionista():
#     r = requests.get("http://localhost:8086/api/categoria/list")
#     data = r.json()
    
#     return render_template('categoria/actualizar.html', list=data["data"], categoria=data["data"])


# @router.route('/admin/categoria/update', methods=["POST"])
# def update_categoria():
#     headers = {'Content-type': 'application/json'}
#     form = request.form
#     dataF = {
#         "comentario": form["comt"],
#         "calificacion": form["calf"],
#     }
    
#     r = requests.post("http://localhost:8086/api/categoria/update", data=json.dumps(dataF), headers=headers)
#     dat = r.json()
    
#     if r.status_code == 200:
#         flash("Inversionista actualizado correctamente", category='info')
#         # registrar_historial("actualizar", "inversionista", f"Inversionista {form['nom']} {form['ape']} actualizado")
#     else:
#         flash(str(dat["data"]), category='error')

#     return redirect("/admin/categoria/list")
