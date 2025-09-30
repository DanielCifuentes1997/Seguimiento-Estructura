package CodigoListas.ListaDobleCircular;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaCircularDoble<T extends Comparable<T>> implements Iterable<T>
{
    private NodoDoble<T> nodoPrimero;
    private NodoDoble<T> nodoUltimo;
    private int tamanio;

    public ListaCircularDoble()
    {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.tamanio = 0;
    }

    // # 8. Verifica si la lista no tiene elementos.
    public boolean estaVacia()
    {
        return this.tamanio == 0;
    }

    // # 1. Agrega un elemento al inicio del ciclo.
    public void agregarInicio(T valor)
    {
        NodoDoble<T> nuevo = new NodoDoble<>(valor);
        if (estaVacia()) {
            // # el nodo se apunta a sí mismo en ambas direcciones.
            this.nodoPrimero = this.nodoUltimo = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
        } else {
            // # aquí hacemos el enlace cuádruple.
            nuevo.setSiguiente(this.nodoPrimero);
            nuevo.setAnterior(this.nodoUltimo);
            this.nodoPrimero.setAnterior(nuevo);
            this.nodoUltimo.setSiguiente(nuevo);
            this.nodoPrimero = nuevo;
        }
        this.tamanio++;
    }

    // # 2. Agrega un elemento al final del ciclo.
    public void agregarFinal(T valor)
    {
        if (estaVacia()) {
            agregarInicio(valor);
            return;
        }

        NodoDoble<T> nuevo = new NodoDoble<>(valor);
        nuevo.setAnterior(this.nodoUltimo);
        nuevo.setSiguiente(this.nodoPrimero);
        this.nodoUltimo.setSiguiente(nuevo);
        this.nodoPrimero.setAnterior(nuevo);
        this.nodoUltimo = nuevo;
        this.tamanio++;
    }

    // # 3. Agrega un elemento en una posición específica.
    public void agregarPosicion(T valor, int indice)
    {
        if (indice == 0) {
            agregarInicio(valor);
            return;
        }
        if (indice == this.tamanio) {
            agregarFinal(valor);
            return;
        }
        
        if (indiceValido(indice)) {
            NodoDoble<T> actual = obtenerNodo(indice);
            NodoDoble<T> previo = actual.getAnterior();
            NodoDoble<T> nuevo = new NodoDoble<>(valor);
            
            // # aquí reconfiguramos los cuatro enlaces afectados.
            previo.setSiguiente(nuevo);
            nuevo.setAnterior(previo);
            nuevo.setSiguiente(actual);
            actual.setAnterior(nuevo);

            this.tamanio++;
        }
    }

    // # 5. Obtiene un nodo por su índice.
    public NodoDoble<T> obtenerNodo(int indice)
    {
        if (!indiceValido(indice)) return null;

        NodoDoble<T> aux = this.nodoPrimero;
        // # este for es ideal para el recorrido en una lista circular.
        for (int i = 0; i < indice; i++) {
            aux = aux.getSiguiente();
        }
        return aux;
    }

    // # 4. Obtiene el valor de un nodo.
    public T obtenerValorNodo(int indice)
    {
        NodoDoble<T> nodo = obtenerNodo(indice);
        return (nodo != null) ? nodo.getValor() : null;
    }

    // # 6. Obtiene la posición de un nodo por su valor.
    public int obtenerPosicionNodo(T valor)
    {
        if (estaVacia()) return -1;
        
        NodoDoble<T> aux = this.nodoPrimero;
        for (int i = 0; i < this.tamanio; i++) {
            if (aux.getValor().equals(valor)) {
                return i;
            }
            aux = aux.getSiguiente();
        }
        return -1;
    }
    
    // # 7. Valida un índice.
    public boolean indiceValido(int indice)
    {
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
            this.nodoPrimero.setAnterior(this.nodoUltimo);
            this.nodoUltimo.setSiguiente(this.nodoPrimero);
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
            this.nodoUltimo.setSiguiente(this.nodoPrimero);
            this.nodoPrimero.setAnterior(this.nodoUltimo);
            this.tamanio--;
        }
    }

    // # 11. Elimina un elemento por su valor.
    public void eliminar(T valor)
    {
        if (estaVacia()) return;
        
        // # primero tratamos los casos de los extremos.
        if (this.nodoPrimero.getValor().equals(valor)) {
            eliminarPrimero();
            return;
        }
        if (this.nodoUltimo.getValor().equals(valor)) {
            eliminarUltimo();
            return;
        }
        
        // # aquí buscamos en el interior de la lista.
        NodoDoble<T> aux = this.nodoPrimero.getSiguiente();
        do {
            if (aux.getValor().equals(valor)) {
                aux.getAnterior().setSiguiente(aux.getSiguiente());
                aux.getSiguiente().setAnterior(aux.getAnterior());
                this.tamanio--;
                return;
            }
            aux = aux.getSiguiente();
        } while (aux != this.nodoPrimero);
    }

    // # 12. Modifica el valor de un nodo.
    public void modificarNodo(int indice, T nuevoValor)
    {
        NodoDoble<T> nodo = obtenerNodo(indice);
        if (nodo != null) {
            nodo.setValor(nuevoValor);
        }
    }
    
    // # 13. Ordena la lista.
    public void ordenarLista()
    {
        if (this.tamanio < 2) return;
        
        boolean cambiado;
        do {
            cambiado = false;
            NodoDoble<T> actual = this.nodoPrimero;
            // # aquí recorremos la lista una vez para cada pasada del algoritmo.
            for (int i = 0; i < this.tamanio - 1; i++) {
                if (actual.getValor().compareTo(actual.getSiguiente().getValor()) > 0) {
                    T temp = actual.getValor();
                    actual.setValor(actual.getSiguiente().getValor());
                    actual.getSiguiente().setValor(temp);
                    cambiado = true;
                }
                actual = actual.getSiguiente();
            }
        } while (cambiado);
    }

    // # 14. Imprime la lista.
    public void imprimirLista()
    {
        if (estaVacia()) {
            System.out.println("Lista circular doble vacía.");
            return;
        }
        NodoDoble<T> aux = this.nodoPrimero;
        System.out.print("... <-> ");
        do {
            System.out.print(aux.getValor() + " <-> ");
            aux = aux.getSiguiente();
        } while (aux != this.nodoPrimero);
        System.out.println("... (ciclo)");
    }
    
    // # 16. Borra la lista.
    public void borrarLista()
    {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.tamanio = 0;
    }

    // # 17. Invierte el contenido.
    public void invertirContenido()
    {
        if (this.tamanio < 2) return;
        
        invertirRecursivo(this.nodoPrimero);
        
        // # aquí solo intercambiamos los roles de primero y último.
        NodoDoble<T> temp = this.nodoPrimero;
        this.nodoPrimero = this.nodoUltimo;
        this.nodoUltimo = temp;
    }
    
    // # esta función recursiva invierte los punteros de cada nodo.
    private void invertirRecursivo(NodoDoble<T> actual)
    {
        // # intercambiamos los punteros del nodo actual.
        NodoDoble<T> temp = actual.getSiguiente();
        actual.setSiguiente(actual.getAnterior());
        actual.setAnterior(temp);
        
        // # avanzamos al siguiente nodo del ciclo original.
        if (actual != this.nodoUltimo) {
            invertirRecursivo(actual.getAnterior()); // # usamos 'getAnterior' porque ya lo invertimos.
        }
    }
    
    // # 15. Provee el iterador.
    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>() {
            private NodoDoble<T> actual = nodoPrimero;
            private int visitados = 0;
            public boolean hasNext() { return !estaVacia() && visitados < tamanio; }
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T valor = actual.getValor();
                actual = actual.getSiguiente();
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