from datetime import datetime
import json

class Transaccion:
    def __init__(self, tipo, detalles):
        self.tipo = tipo
        self.fecha = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        self.detalles = detalles

def guardar_transaccion(transaccion):
    # Asegúrate de que el archivo 'transacciones.json' exista
    try:
        with open('transacciones.json', 'a') as file:
            file.write(json.dumps(transaccion.__dict__) + "\n")  # Guarda cada transacción en una nueva línea
    except Exception as e:
        print(f"Error al guardar la transacción: {e}")
