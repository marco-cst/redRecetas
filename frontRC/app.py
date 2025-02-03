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