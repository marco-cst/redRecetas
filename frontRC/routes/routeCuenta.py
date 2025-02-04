from flask import Blueprint, session, request, render_template, redirect, flash, url_for
import requests
import json
from functools import wraps

router2 = Blueprint('router2', __name__)

# Decorador para proteger rutas
def login_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        # Verifica si el usuario está logueado
        if not session.get('logged_in'):
            flash("Por favor, inicia sesión para acceder.", category='error')
            return redirect(url_for('router2.account_login'))  # Redirigir al login
        return f(*args, **kwargs)
    return decorated_function


@router2.route('/')
def admin():
    return render_template('register.html')

@router2.route('/usuario')
def usuario():
    r = requests.get("http://localhost:8086/api/person/list")
    data = r.json()
    print(r.json())
    return render_template('LoginExitoso/base.html', lista=data["data"])

@router2.route('/logeado')
@login_required
def logeado_page():
    return render_template('LoginExitoso/base.html')  # Página protegida

@router2.route('/admin/account/register')
def account_register():
    return render_template('fragmento/inicial/cuenta/registro.html')

@router2.route('/admin/account/login')
def account_login():
    if session.get('logged_in'):
        return redirect('/admin/account/data')
    return render_template('fragmento/inicial/cuenta/login.html')


@router2.route('/admin/account/validation', methods=['POST'])
def logeado():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Datos de las credenciales específicas
    credenciales_especificas = {
        "correo": "admin@gmail.com",
        "clave": "Admin123@"
    }

    # Verificar si las credenciales son las específicas
    if form["correo"] == credenciales_especificas["correo"] and form["clave"] == credenciales_especificas["clave"]:
        # Guardar los datos del usuario logueado en la sesión
        session['logged_in'] = True
        session['user'] = {
            "correo": credenciales_especificas["correo"],
            "idCuenta": 1,  # Puedes ajustar este valor según tu lógica
        }
        flash("Login exitoso como administrador", category='info')
        return redirect('/admin/list/account')  # Redirigir a la lista de cuentas
    else:
        # Validar otras credenciales con la API
        data_cuenta = {
            "correo": form["correo"],
            "clave": form["clave"],
        }

        # Realizar la solicitud POST para validar las credenciales
        r_cuenta = requests.post("http://localhost:8086/api/account/login", data=json.dumps(data_cuenta), headers=headers)
        
        if r_cuenta.status_code == 200:
            # Guardar los datos del usuario logueado en la sesión
            cuenta_data = r_cuenta.json()
            session['logged_in'] = True
            session['user'] = {
                "correo": cuenta_data.get("correo"),
                "idCuenta": cuenta_data.get("idCuenta"),
            }
            flash("Login exitoso", category='info')
            return redirect('/logeado')  # Redirigir a la página de usuario logeado
        else:
            error_msg = r_cuenta.json().get("msg", "Datos incorrectos")
            flash(error_msg, category='error')
            return redirect('/admin/account/login')  # Redirigir al login en caso de error
        






@router2.route('/admin/account/save', methods=['POST'])
def save_receta():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    data_cuenta = {
        "person": form["person"],
        "correo": form["correo"],
        "clave": form["clave"],
    }
    r_cuenta = requests.post("http://localhost:8086/api/account/save", data=json.dumps(data_cuenta), headers=headers)
    if r_cuenta.status_code == 200:
        flash("Registro guardado correctamente", category='info')
        return redirect('/admin/account/login')
    else:
        error_msg = r_cuenta.json().get("data", "Error faltan datos")
        flash(error_msg, category='error')
        return redirect('/admin/account/register')
    
@router2.route('/admin/account/data')
@login_required
def data_login():
    # Obtener detalles de la cuenta del usuario logueado
    user = session.get('user')
    if not user:
        flash("No se pudo obtener información del usuario", category='error')
        return redirect('/admin/account/login')

    cuenta_id = user.get("idCuenta")
    
    if cuenta_id:
        # Consultar los datos de la cuenta
        r_cuenta = requests.get(f"http://localhost:8086/api/account/get/{cuenta_id}")
        if r_cuenta.status_code == 200:
            cuenta_data = r_cuenta.json().get("data", {})
            

            # Obtener el idPersona asociado a la cuenta
            id_persona = cuenta_data.get("idPersona")
            if id_persona:
                # Consultar los datos de la persona
                r_persona = requests.get(f"http://localhost:8086/api/person/get/{id_persona}")
                if r_persona.status_code == 200:
                    persona_data = r_persona.json().get("data", {})
                    

                    # Renderizar ambos conjuntos de datos en la plantilla
                    return render_template('LoginExitoso/datosCuenta.html', cuenta=cuenta_data, persona=persona_data)
                else:
                    flash("No se pudieron obtener los datos de la persona", category='error')
                    return redirect('/logeado')
            else:
                flash("La cuenta no tiene una persona asociada", category='error')
                return redirect('/logeado')
        else:
            flash("No se pudieron obtener los datos de la cuenta", category='error')
            return redirect('/logeado')
    else:
        flash("ID de cuenta no válido", category='error')
        return redirect('/admin/account/login')
    


@router2.route('/admin/account/delete/<id>')
@login_required
def delete_account(id):
    r_cuenta = requests.delete("http://localhost:8086/api/account/delete/"+id )
    
    if r_cuenta.status_code == 200:
        flash("Registro eliminado correctamente", category='info')
        return redirect('/admin/list/account')
    else:
        error_msg = r_cuenta.json().get("data", "Error al eliminar la cuenta")
        flash(error_msg, category='error')
        return redirect('/admin/list/account')




@router2.route('/public/receta/list')
def list_receta():
    try:
        # Obtener recetas
        r_receta = requests.get("http://localhost:8086/api/receta/list")
        data_receta = r_receta.json()

        # Obtener reseñas
        r_resenia = requests.get("http://localhost:8086/api/resenia/list")
        data_resenia = r_resenia.json()

        # Pasar la data al template sin asociar recetas con reseñas
        return render_template(
            'receta/lista.html',
            lista_receta=data_receta["data"],
            lista_resenias=data_resenia["data"]  # Se pasa como lista sin agrupar
        )

    except requests.RequestException as e:
        return f"Error al obtener datos: {str(e)}", 500


@router2.route('/logout')
def logout():
    session.clear()
    flash("Sesión cerrada exitosamente", category='info')
    return redirect('/admin/account/login')


