from app import create_app
app = create_app()
if __name__ == '__main__':
<<<<<<< HEAD
    app.secret_key = 'topsecret'
=======
>>>>>>> feature/Receta
    app.run(debug = True, host="0.0.0.0")