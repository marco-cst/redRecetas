<<<<<<< HEAD
<<<<<<< HEAD
from app import create_app
app = create_app()
if __name__ == '__main__':
<<<<<<< HEAD
<<<<<<< HEAD
    app.secret_key = 'topsecret'
=======
>>>>>>> feature/Receta
=======
>>>>>>> feature/Ingredientes
    app.run(debug = True, host="0.0.0.0")
=======
from app import create_app
app = create_app()
if __name__ =='__main__':
    app.secret_key='super_secret'
    app.config['SESSION_TYPE'] = 'filesystem'
    app.run(debug=True, host="0.0.0.0")     
>>>>>>> feature/Cuenta
=======
from app import create_app
app = create_app()
if __name__ == '__main__':
    app.run(debug = True, host="0.0.0.0")
>>>>>>> feature/Categoria
