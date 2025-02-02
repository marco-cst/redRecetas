package controller.tda.list;

public class LinkedList<E> {
    private Node<E> header; // Nodo cabecera (el primer nodo de la lista)
    private Node<E> last;   // Nodo último (el último nodo de la lista)
    private Integer size;   // Tamaño de la lista (cuenta el número de nodos en la lista)

    // Constructor de la clase LinkedList
    public LinkedList() {
        this.header = null; // Inicialmente, la cabecera es nula (no hay nodos en la lista)
        this.last = null;   // Inicialmente, el último nodo es nulo
        this.size = 0;      // Inicialmente, el tamaño de la lista es 0
    }

    // Método para verificar si la lista está vacía
    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    // Método privado para agregar un elemento al principio de la lista
    private void addHeader(E dato) {
        Node<E> help;

        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.size++;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    public void addLast(E info) {  //cambio a public
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            header = help;
            last = help;
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
        }
        this.size++;
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
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

    private E getFirst() throws ListEmptyException {
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

   
    
   public E removeFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        } else {
            E elemnt = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            } 
            size--;
            return elemnt;
            }
        }
        
        
    public E removeLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        } else{
            E elemnt = last.getInfo();
            Node<E> aux = getNode(size -2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                }else {
                header = null;
                }
            }else{
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return elemnt;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (post == 0) {
            return removeFirst(); // Elimina el primer nodo
        } else if (post == (size - 1)) {
            return removeLast(); // Elimina el último nodo
        } else {
            Node<E> previous = getNode(post - 1); // Nodo anterior al nodo a eliminar
            Node<E> current = previous.getNext(); // Nodo actual (a eliminar)
            E element = current.getInfo(); // Información del nodo a eliminar
            Node<E> next = current.getNext(); // Nodo siguiente al nodo a eliminar
    
            previous.setNext(next); // Elimina el nodo actual enlazando el nodo anterior al siguiente
            size--; // Disminuye el tamaño de la lista
            return element; // Devuelve la información del nodo eliminado
        }
    }
    
    // public boolean remove(E element) {
    //     if (isEmpty()) return false;
        
    //     if (header.getInfo().equals(element)) { // Si el elemento está en la cabecera
    //         header = header.getNext();
    //         size--;
    //         return true;
    //     }
        
    //     Node<E> current = header;
    //     while (current.getNext() != null) {
    //         if (current.getNext().getInfo().equals(element)) {
    //             current.setNext(current.getNext().getNext());
    //             size--;
    //             return true;
    //         }
    //         current = current.getNext();
    //     }
        
    //     return false; // Elemento no encontrado
    // }

    
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
    

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
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
        return header;
    }

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

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
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

    public LinkedList<E> order() throws Exception {
        if (isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Number || data instanceof String) {
                E[] lista = this.toArray();
                reset();
                for (int i = 0; i < lista.length; i++) {
                    E aux = lista[i];
                    int j = i - 1;
                    while (j >= 0 && compare(lista[j], aux)) {
                        lista[j + 1] = lista[j--];
                    }
                    lista[j + 1] = aux;
                }
                this.toList(lista);
            } else{
                System.out.println("Objeto");
            }
        }
        return this;
    } 

    private Boolean compare(E a, E b){
        if (a instanceof  Number) {
            Number a1 = (Number) a;
            Number b1 = (Number) b;
            return a1.doubleValue() > b1.doubleValue();
        } else {
            return (a.toString().compareTo(b.toString()) > 0);
        }
    }

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
}



