package controller.tda.list;

import controller.tda.list.LinkedList;
import models.Receta;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class LinkedList<E> {
    private Node<E> header; // Nodo cabecera (el primer nodo de la lista)
    private Node<E> last; // Nodo último (el último nodo de la lista)
    private Integer size; // Tamaño de la lista (cuenta el número de nodos en la lista)
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    public LinkedList<E> clonar() {
        LinkedList<E> nuevaLista = new LinkedList<>();
        Node<E> actual = this.header; // Suponiendo que tienes un nodo cabeza en tu LinkedList
        while (actual != null) {
            nuevaLista.add(actual.getInfo()); // Asumiendo que `add` agrega un elemento a la lista
            actual = actual.getNext(); // Asumiendo que `getSiguiente` da acceso al siguiente nodo
        }
        return nuevaLista;
    }

    // Constructor de la clase LinkedList
    public LinkedList() {
        this.header = null; // Inicialmente, la cabecera es nula (no hay nodos en la lista)
        this.last = null; // Inicialmente, el último nodo es nulo
        this.size = 0; // Inicialmente, el tamaño de la lista es 0
    }

    // Método para verificar si la lista está vacía
    public Boolean isEmpty() {
        // Retorna verdadero si la cabecera es nula o el tamaño es 0, es decir, si la
        // lista está vacía
        return (this.header == null || this.size == 0);
    }

    // Método privado para agregar un elemento al principio de la lista
    protected void addHeader(E dato) {
        Node<E> help; // Nodo de ayuda para insertar el nuevo dato
        if (isEmpty()) {
            help = new Node<>(dato); // Crea un nuevo nodo con el dato
            this.header = help; // El nuevo nodo se convierte en el nodo cabecera
            this.last = help; // Al ser el primero tambien es el ultimo
        } else {
            // Si la lista no está vacía
            Node<E> healpHeader = this.header; // Guarda el nodo cabecera actual en una variable auxiliar
            help = new Node<>(dato, healpHeader); // Crea un nuevo nodo que apunta al nodo cabecera actual
            this.header = help; // El nuevo nodo se convierte en la nueva cabecera
        }
        this.size++; // Incrementa el tamaño de la lista
    }

    public void addLast(E info) {
        Node<E> help; // Nodo para ayudar a agregar el nuevo elemento
        if (isEmpty()) { // Verificar si la lista está vacía
            addHeader(info); // Crear un nuevo nodo
        } else {
            help = new Node<>(info, null); // Crear un nuevo nodo
            last.setNext(help); // Conectar el último nodo al nuevo nodo
            last = help; // Actualizar 'last' al nuevo nodo
            this.size++; // Incrementar el tamaño de la lista
        }
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return header.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BYPOSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data \n");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header; // Devuelve el nodo cabecera
    }

    // esta bien
    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }

        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) { // Recibe un array de objetos
        reset(); // Reinicia la lista
        for (int i = 0; i < matrix.length; i++) { // Recorre el array
            this.add(matrix[i]); // Agrega cada objeto del array a un nodo de la lista
        }
        return this; // Devuelve la instancia LinkedList
    }

    // public void update(E object, Integer index) {
    //     if (index < 0 || index >= size) { // Verifica el rango del índice
    //         throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
    //     }
    
    //     Node<E> current = header; // Comienza desde la cabecera
    //     for (int i = 0; i < index; i++) {
    //         current = current.getNext(); // Moverse al nodo en el índice deseado
    //     }
    
    //     current.setInfo(object); // Actualizar el dato del nodo usando el setter
    // }

    //new

    // public LinkedList<E> order() throws Exception {
    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    // REMOVE AGREGADO 24/OCT/2024

    public int getLength() {
        return this.size.intValue();
    }

    public E removeFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> help = header.getNext();
            header = help;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E removeLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> help = getNode(size - 2);
            if (help == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = help;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }
//removeId new
    public E remove(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (post == 0) {
            return removeFirst();
        } else if (post == (size - 1)) {
            return removeLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;

        }
    }
 //Remove Element Original
    public boolean removeElement(E element) {
        if (isEmpty()) return false;
        
        if (header.getInfo().equals(element)) { // Si el elemento está en la cabecera
            header = header.getNext();
            size--;
            return true;
        }
        
        Node<E> current = header;
        while (current.getNext() != null) {
            if (current.getNext().getInfo().equals(element)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        
        return false; // Elemento no encontrado
    }

    public LinkedList<E> order(Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Number || data instanceof String) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++) {
                    E aux = lista[i]; // valor a ordenar
                    int j = i - 1; // índice anterior
                    while (j >= 0 && compare(lista[j], aux, type)) {
                        lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                    }
                    lista[j + 1] = aux; // inserta el valor en su posición correcta
                }
                this.toList(lista);
            }
        }
        return this;
    }

    public LinkedList<E> order(String att, Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++) {
                    E aux = lista[i]; // valor a ordenar
                    int j = i - 1; // índice anterior
                    while (j >= 0 && atrribute_compare(att, lista[j], aux, type)) {
                        lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                    }
                    lista[j + 1] = aux; // inserta el valor en su posición correcta
                }
                this.toList(lista);
            }
        }
        return this;
    }

    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
                // break;

            default:
                // mayor a menor
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
                // break;
        }

    }

    // compare class
    private Boolean atrribute_compare(String att, E a, E b, Integer type) throws Exception {
        return compare(exist_att(a, att), exist_att(b, att), type);
    }

    private Object exist_att(E a, String att) throws Exception {
        Method method = null;
        att = att.substring(0, 1).toUpperCase() + att.substring(1);
        att = "get" + att;
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().contains(att)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().contains(att)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {
            return method.invoke(a);
        }

        return null;
    }

    //Metodos de ordeenamiento
    // Quicksort
    public LinkedList<E> qs(int ordertype) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            qsHelper(lista, 0, lista.length - 1, ordertype);
            this.toList(lista);
        }
        return this;
    }

    private void qsHelper(E[] lista, int low, int high, int ordertype) throws Exception {
        if (low < high) {
            int pi = partition(lista, low, high, ordertype);
            qsHelper(lista, low, pi - 1, ordertype);
            qsHelper(lista, pi + 1, high, ordertype);
        }
    }

    private int partition(E[] lista, int low, int high, int ordertype) throws Exception {
        E pivot = lista[high];
        int i = (low - 1); // Indice del elemento más pequeño

        for (int j = low; j < high; j++) {
            if (compare(lista[j], pivot, ordertype)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }

        E temp = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = temp;

        return i + 1;
    }

    public LinkedList<E> qs(String att, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            qsHelperatt(lista, 0, lista.length - 1, att, type);
            this.toList(lista);
        }
        return this;
    }

    private void qsHelperatt(E[] lista, int low, int high, String att, Integer type) throws Exception {
        if (low < high) {
            int pi = partitionatt(lista, low, high, att, type);
            qsHelperatt(lista, low, pi - 1, att, type);
            qsHelperatt(lista, pi + 1, high, att, type);
        }
    }

    private int partitionatt(E[] lista, int low, int high, String att, Integer type) throws Exception {
        E pivot = lista[high];
        int i = (low - 1); // Indice del elemento más pequeño

        for (int j = low; j < high; j++) {
            if (atrribute_compare(att, lista[j], pivot, type)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }

        E temp = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = temp;

        return i + 1;
    }

    // ms
    public LinkedList<E> ms(int ordertype) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            lista = msHelper(lista, ordertype);
            this.toList(lista);
        }
        return this;
    }

    private E[] msHelper(E[] lista, int ordertype) throws Exception {
        if (lista.length <= 1) {
            return lista;
        }

        int middle = lista.length / 2;
        E[] left = Arrays.copyOfRange(lista, 0, middle);
        E[] right = Arrays.copyOfRange(lista, middle, lista.length);

        left = msHelper(left, ordertype);
        right = msHelper(right, ordertype);

        return merge(left, right, ordertype);
    }

    private E[] merge(E[] left, E[] right, int ordertype) throws Exception {
        E[] result = (E[]) new Object[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j], ordertype)) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    public LinkedList<E> ms(String att, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            lista = msHelperatt(lista, att, type);
            this.toList(lista);
        }
        return this;
    }

    private E[] msHelperatt(E[] lista, String att, Integer type) throws Exception {
        if (lista.length <= 1) {
            return lista;
        }

        int middle = lista.length / 2;
        E[] left = Arrays.copyOfRange(lista, 0, middle);
        E[] right = Arrays.copyOfRange(lista, middle, lista.length);

        left = msHelperatt(left, att, type);
        right = msHelperatt(right, att, type);

        return mergeatt(left, right, att, type);
    }

    private E[] mergeatt(E[] left, E[] right, String att, Integer type) throws Exception {
        E[] result = (E[]) new Object[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (atrribute_compare(att, left[i], right[j], type)) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    // shell sort
    public LinkedList<E> shellsort(String att, Integer ordertype) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            int n = lista.length;
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    E temp = lista[i];
                    int j;
                    for (j = i; j >= gap && compare(lista[j - gap], temp, ordertype); j -= gap) {
                        lista[j] = lista[j - gap];
                    }
                    lista[j] = temp;
                }
            }
            this.toList(lista);
        }
        return this;
    }

    public LinkedList<E> shellSort(String att, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray(); // Convertir a un arreglo la lista enlazada
            int n = lista.length;

            // Inicialización del intervalo de Shell Sort
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    E temp = lista[i];
                    int j;

                    // Comparar elementos en el intervalo
                    for (j = i; j >= gap && atrribute_compare(att, lista[j - gap], temp, type); j -= gap) {
                        lista[j] = lista[j - gap];
                    }
                    lista[j] = temp;
                }
            }

            this.toList(lista); // Convertir el array de vuelta a lista enlazada
        }
        return this;
    }
}