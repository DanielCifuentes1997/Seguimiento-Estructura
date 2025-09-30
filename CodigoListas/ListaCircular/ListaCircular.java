package CodigoListas.ListaCircular;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaCircular<T extends Comparable<T>> implements Iterable<T>
{
    private Nodo<T> nodoPrimero;
    private Nodo<T> nodoUltimo;
    private int tamanio;

    public ListaCircular()
    {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.tamanio = 0;
    }

    // # 8. Revisa si la lista está vacía.
    public boolean estaVacia()
    {
        return this.tamanio == 0;
    }
    
    // # 1. Agrega un nodo al inicio.
    public void agregarInicio(T valor)
    {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (estaVacia()) {
            // # si es el primer nodo, se apunta a sí mismo.
            this.nodoPrimero = this.nodoUltimo = nuevo;
            this.nodoUltimo.setNodoSiguiente(this.nodoPrimero);
        } else {
            // # aquí enlazamos el nuevo nodo y reajustamos el ciclo.
            nuevo.setNodoSiguiente(this.nodoPrimero);
            this.nodoPrimero = nuevo;
            this.nodoUltimo.setNodoSiguiente(this.nodoPrimero);
        }
        this.tamanio++;
    }

    // # 2. Agrega un nodo al final.
    public void agregarFinal(T valor)
    {
        if (estaVacia()) {
            // # si está vacía, es igual que agregar al inicio.
            agregarInicio(valor);
            return;
        }

        Nodo<T> nuevo = new Nodo<>(valor);
        this.nodoUltimo.setNodoSiguiente(nuevo);
        this.nodoUltimo = nuevo;
        this.nodoUltimo.setNodoSiguiente(this.nodoPrimero); // # aquí cerramos el ciclo.
        this.tamanio++;
    }
    
    // # 3. Agrega un nodo en una posición específica.
    public void agregarPosicion(T valor, int indice)
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

        if (indiceValido(indice)) {
            Nodo<T> nuevo = new Nodo<>(valor);
            Nodo<T> previo = obtenerNodo(indice - 1);
            nuevo.setNodoSiguiente(previo.getNodoSiguiente());
            previo.setNodoSiguiente(nuevo);
            this.tamanio++;
        }
    }
    
    // # 5. Obtiene un nodo por su índice.
    public Nodo<T> obtenerNodo(int indice)
    {
        if (!indiceValido(indice)) return null;

        Nodo<T> aux = this.nodoPrimero;
        int contador = 0;
        // # usamos un while para el recorrido.
        while (contador < indice) {
            aux = aux.getNodoSiguiente();
            contador++;
        }
        return aux;
    }
    
    // # 4. Obtiene el valor de un nodo por su índice.
    public T obtenerValorNodo(int indice)
    {
        Nodo<T> nodo = obtenerNodo(indice);
        return (nodo != null) ? nodo.getValor() : null;
    }

    // # 6. Obtiene el índice de un nodo por su valor.
    public int obtenerPosicionNodo(T valor)
    {
        if (estaVacia()) return -1;
        
        Nodo<T> aux = this.nodoPrimero;
        for (int i = 0; i < this.tamanio; i++) {
            if (aux.getValor().equals(valor)) {
                return i;
            }
            aux = aux.getNodoSiguiente();
        }
        return -1;
    }

    // # 7. Valida si un índice es correcto.
    public boolean indiceValido(int indice)
    {
        return indice >= 0 && indice <= this.tamanio;
    }

    // # 9. Elimina el primer nodo.
    public void eliminarPrimero()
    {
        if (estaVacia()) return;

        if (this.tamanio == 1) {
            borrarLista(); // # si solo hay uno, la lista queda vacía.
        } else {
            this.nodoPrimero = this.nodoPrimero.getNodoSiguiente();
            this.nodoUltimo.setNodoSiguiente(this.nodoPrimero); // # mantenemos el ciclo.
            this.tamanio--;
        }
    }

    // # 10. Elimina el último nodo.
    public void eliminarUltimo()
    {
        if (estaVacia()) return;
        
        if (this.tamanio == 1) {
            borrarLista();
        } else {
            // # aquí encontramos el penúltimo nodo para hacerlo el último.
            Nodo<T> penultimo = obtenerNodo(this.tamanio - 2);
            penultimo.setNodoSiguiente(this.nodoPrimero);
            this.nodoUltimo = penultimo;
            this.tamanio--;
        }
    }

    // # 11. Elimina un nodo por su valor.
    public void eliminar(T valor)
    {
        if (estaVacia()) return;

        // # primero revisamos si es el primer nodo.
        if (this.nodoPrimero.getValor().equals(valor)) {
            eliminarPrimero();
            return;
        }

        // # después buscamos en el resto de la lista.
        Nodo<T> aux = this.nodoPrimero;
        do {
            if (aux.getNodoSiguiente().getValor().equals(valor)) {
                if (aux.getNodoSiguiente() == this.nodoUltimo) {
                    // # si es el último, usamos la lógica ya creada.
                    eliminarUltimo();
                    return;
                }
                aux.setNodoSiguiente(aux.getNodoSiguiente().getNodoSiguiente());
                this.tamanio--;
                return;
            }
            aux = aux.getNodoSiguiente();
        } while (aux != this.nodoPrimero);
    }
    
    // # 12. Modifica el valor de un nodo.
    public void modificarNodo(int indice, T nuevoValor)
    {
        Nodo<T> nodo = obtenerNodo(indice);
        if (nodo != null) {
            nodo.setValor(nuevoValor);
        }
    }
    
    // # 13. Ordena la lista (burbuja).
    public void ordenarLista()
    {
        if (this.tamanio < 2) return;
        
        // # convertimos a una lista simple temporalmente para ordenar.
        this.nodoUltimo.setNodoSiguiente(null);
        
        boolean cambiado;
        do {
            cambiado = false;
            Nodo<T> actual = this.nodoPrimero;
            while (actual.getNodoSiguiente() != null) {
                if (actual.getValor().compareTo(actual.getNodoSiguiente().getValor()) > 0) {
                    T temp = actual.getValor();
                    actual.setValor(actual.getNodoSiguiente().getValor());
                    actual.getNodoSiguiente().setValor(temp);
                    cambiado = true;
                }
                actual = actual.getNodoSiguiente();
            }
        } while (cambiado);

        // # aquí reconectamos el ciclo.
        this.nodoUltimo = obtenerNodo(this.tamanio - 1);
        this.nodoUltimo.setNodoSiguiente(this.nodoPrimero);
    }

    // # 14. Imprime la lista.
    public void imprimirLista()
    {
        if (estaVacia()) {
            System.out.println("Lista circular vacía.");
            return;
        }

        Nodo<T> aux = this.nodoPrimero;
        // # el bucle do-while es ideal para recorrer listas circulares una vez.
        do {
            System.out.print(aux.getValor() + " -> ");
            aux = aux.getNodoSiguiente();
        } while (aux != this.nodoPrimero);
        System.out.println("(vuelve a " + this.nodoPrimero.getValor() + ")");
    }
    
    // # 16. Borra la lista completa.
    public void borrarLista()
    {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.tamanio = 0;
    }

    // # 17. Invierte el contenido de la lista.
    public void invertirContenido()
    {
        if (this.tamanio < 2) return;
        
        Nodo<T> anterior = this.nodoUltimo;
        Nodo<T> actual = this.nodoPrimero;
        Nodo<T> siguiente;
        
        // # aquí recorremos la lista una vez, invirtiendo los punteros.
        for (int i = 0; i < this.tamanio; i++) {
            siguiente = actual.getNodoSiguiente();
            actual.setNodoSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }
        
        // # finalmente, reajustamos la cabeza y la cola de la lista.
        this.nodoUltimo = this.nodoPrimero;
        this.nodoPrimero = anterior;
    }

    // # 15. Provee el iterador.
    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>() {
            private Nodo<T> actual = nodoPrimero;
            private int visitados = 0;
            public boolean hasNext() { return !estaVacia() && visitados < tamanio; }
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T valor = actual.getValor();
                actual = actual.getNodoSiguiente();
                visitados++;
                return valor;
            }
        };
    }
    
    public int getTamanio()
    {
        return this.tamanio;
    }
}