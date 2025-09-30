package CodigoListas.ListaDoble;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaDoble<T extends Comparable<T>> implements Iterable<T>
{
    private NodoDoble<T> nodoPrimero;
    private NodoDoble<T> nodoUltimo;
    private int tamanio;

    public ListaDoble()
    {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.tamanio = 0;
    }

    // # 8. Verifica si la lista no contiene elementos.
    public boolean estaVacia()
    {
        return this.tamanio == 0;
    }

    // # 1. Agrega un elemento al inicio.
    public void agregarInicio(T valor)
    {
        NodoDoble<T> nuevo = new NodoDoble<>(valor);
        if (estaVacia()) {
            // # si está vacía, el nuevo nodo es tanto el inicio como el fin.
            this.nodoPrimero = this.nodoUltimo = nuevo;
        } else {
            // # aquí enlazamos el nuevo nodo con el que era el primero.
            nuevo.setSiguiente(this.nodoPrimero);
            this.nodoPrimero.setAnterior(nuevo);
            this.nodoPrimero = nuevo;
        }
        this.tamanio++;
    }

    // # 2. Agrega un elemento al final.
    public void agregarFinal(T valor)
    {
        // # si la lista está vacía, agregar al final es como agregar al inicio.
        if (estaVacia()) {
            agregarInicio(valor);
            return;
        }

        NodoDoble<T> nuevo = new NodoDoble<>(valor);
        this.nodoUltimo.setSiguiente(nuevo);
        nuevo.setAnterior(this.nodoUltimo);
        this.nodoUltimo = nuevo;
        this.tamanio++;
    }

    // # 3. Agrega un elemento en una posición específica.
    public void agregarIndice(int indice, T valor)
    {
        // # primero, manejamos los casos de los extremos.
        if (indice == 0) {
            agregarInicio(valor);
            return;
        }
        if (indice == this.tamanio) {
            agregarFinal(valor);
            return;
        }

        // # aquí manejamos la inserción en una posición intermedia.
        if (indiceValido(indice)) {
            NodoDoble<T> actual = obtenerNodo(indice);
            NodoDoble<T> previo = actual.getAnterior();
            NodoDoble<T> nuevo = new NodoDoble<>(valor);

            previo.setSiguiente(nuevo);
            nuevo.setAnterior(previo);
            nuevo.setSiguiente(actual);
            actual.setAnterior(nuevo);

            this.tamanio++;
        }
    }

    // # 5. Obtiene un nodo basado en su posición.
    public NodoDoble<T> obtenerNodo(int indice)
    {
        if (!indiceValido(indice)) {
            return null;
        }
        
        NodoDoble<T> nodoBuscado;
        // # para optimizar, decidimos desde qué extremo empezar a buscar.
        if (indice < this.tamanio / 2) {
            nodoBuscado = this.nodoPrimero;
            for (int i = 0; i < indice; i++) {
                nodoBuscado = nodoBuscado.getSiguiente();
            }
        } else {
            nodoBuscado = this.nodoUltimo;
            for (int i = this.tamanio - 1; i > indice; i--) {
                nodoBuscado = nodoBuscado.getAnterior();
            }
        }
        return nodoBuscado;
    }

    // # 4. Obtiene el valor de un nodo en una posición.
    public T obtenerValorNodo(int indice)
    {
        NodoDoble<T> nodo = obtenerNodo(indice);
        // # usamos un operador ternario para el retorno.
        return (nodo != null) ? nodo.getValor() : null;
    }

    // # 6. Obtiene la posición de un nodo por su valor.
    public int obtenerPosicionNodo(T valor)
    {
        int i = 0;
        for (NodoDoble<T> aux = this.nodoPrimero; aux != null; aux = aux.getSiguiente()) {
            if (aux.getValor().equals(valor)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // # 7. Valida si un índice es válido.
    public boolean indiceValido(int indice)
    {
        // # para inserciones, el índice puede ser igual al tamaño.
        return indice >= 0 && indice <= this.tamanio;
    }

    // # 9. Elimina el primer elemento.
    public void eliminarPrimero()
    {
        if (estaVacia()) return;

        if (this.tamanio == 1) {
            borrarLista();
        } else {
            this.nodoPrimero = this.nodoPrimero.getSiguiente();
            this.nodoPrimero.setAnterior(null);
            this.tamanio--;
        }
    }

    // # 10. Elimina el último elemento.
    public void eliminarUltimo()
    {
        if (estaVacia()) return;

        if (this.tamanio == 1) {
            borrarLista();
        } else {
            this.nodoUltimo = this.nodoUltimo.getAnterior();
            this.nodoUltimo.setSiguiente(null);
            this.tamanio--;
        }
    }

    // # 11. Elimina un elemento por su valor.
    public void eliminar(T valor)
    {
        if (estaVacia()) return;
        
        // # revisamos si es el primer o último nodo para reusar la lógica.
        if (this.nodoPrimero.getValor().equals(valor)) {
            eliminarPrimero();
            return;
        }
        if (this.nodoUltimo.getValor().equals(valor)) {
            eliminarUltimo();
            return;
        }
        
        // # buscamos el nodo en una posición intermedia.
        NodoDoble<T> actual = this.nodoPrimero.getSiguiente();
        while(actual != null) {
            if (actual.getValor().equals(valor)) {
                actual.getAnterior().setSiguiente(actual.getSiguiente());
                actual.getSiguiente().setAnterior(actual.getAnterior());
                this.tamanio--;
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    // # 12. Modifica el valor de un nodo.
    public void modificarNodo(int indice, T nuevoValor)
    {
        NodoDoble<T> nodo = obtenerNodo(indice);
        if (nodo != null) {
            nodo.setValor(nuevoValor);
        }
    }
    
    // # 13. Ordena la lista (método burbuja).
    public void ordenarLista()
    {
        if (this.tamanio < 2) return;
        
        for (int i = 0; i < this.tamanio; i++) {
            NodoDoble<T> actual = this.nodoPrimero;
            while(actual.getSiguiente() != null) {
                if (actual.getValor().compareTo(actual.getSiguiente().getValor()) > 0) {
                    T temp = actual.getValor();
                    actual.setValor(actual.getSiguiente().getValor());
                    actual.getSiguiente().setValor(temp);
                }
                actual = actual.getSiguiente();
            }
        }
    }

    // # 14. Imprime la lista.
    public void imprimirLista()
    {
        // # usamos un StringBuilder para construir la cadena de salida.
        StringBuilder sb = new StringBuilder();
        NodoDoble<T> aux = this.nodoPrimero;
        while(aux != null) {
            sb.append(aux.getValor()).append(" <-> ");
            aux = aux.getSiguiente();
        }
        sb.append("null");
        System.out.println(sb.toString());
    }

    // # 16. Borra toda la lista.
    public void borrarLista()
    {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.tamanio = 0;
    }

    // # 15. Provee un iterador.
    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>() {
            private NodoDoble<T> actual = nodoPrimero;
            public boolean hasNext() { return actual != null; }
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T valor = actual.getValor();
                actual = actual.getSiguiente();
                return valor;
            }
        };
    }
    
    // # 17. Invierte el contenido de la lista.
    public void invertirContenido()
    {
        if (this.tamanio < 2) return;
        
        NodoDoble<T> actual = this.nodoPrimero;
        NodoDoble<T> temp = null;
        
        // # intercambiamos los punteros de cada nodo.
        while (actual != null) {
            temp = actual.getAnterior();
            actual.setAnterior(actual.getSiguiente());
            actual.setSiguiente(temp);
            actual = actual.getAnterior(); // # avanzamos al que ahora es el siguiente.
        }
        
        // # finalmente, intercambiamos la cabeza y la cola.
        temp = this.nodoPrimero;
        this.nodoPrimero = this.nodoUltimo;
        this.nodoUltimo = temp;
    }

    public int getTamanio()
    {
        return this.tamanio;
    }
}