<<<<<<< HEAD
from flask import Flask

def create_app():
    app = Flask(__name__, instance_relative_config=False)
<<<<<<< HEAD
<<<<<<< HEAD
=======
    app.secret_key = "xnxx"
>>>>>>> feature/Receta
=======
    app.secret_key = "xnxx"
>>>>>>> feature/Ingredientes
    with app.app_context():
        from routes.router import router
        app.register_blueprint(router)
    return app
=======
from flask import Flask
def create_app():
    app = Flask(__name__, instance_relative_config=False)
    app.config['SESSION_TYPE'] = 'filesystem'  # Almacenar la sesión en el sistema de archivos
    app.secret_key = 'una_clave_secreta_muy_segura'  # Clave secreta para la sesión
    with app.app_context():
        from routes.route import router
        from routes.routePersona import router1
        from routes.routeCuenta import router2
        app.register_blueprint(router)
        app.register_blueprint(router1)
        app.register_blueprint(router2)
    return app

>>>>>>> feature/Cuenta
