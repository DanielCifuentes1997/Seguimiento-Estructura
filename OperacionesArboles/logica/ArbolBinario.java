package OperacionesArboles.logica;

import java.util.LinkedList;
import java.util.Queue;

public class ArbolBinario<T extends Comparable<T>> {

    private Nodo<T> nodoRaiz;

    public ArbolBinario() {
        this.nodoRaiz = null;
    }

    public Nodo<T> getNodoRaiz() {
        return nodoRaiz;
    }

    public boolean esVacio() {
        return this.nodoRaiz == null;
    }

    public void limpiarArbol() {
        this.nodoRaiz = null;
    }

    public void insertar(T dato) {
        this.nodoRaiz = insertarRecursivo(this.nodoRaiz, dato);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> actual, T dato) {
        if (actual == null) {
            return new Nodo<>(dato);
        }

        int comparacion = dato.compareTo(actual.getDato());

        if (comparacion < 0) {
            actual.setIzq(insertarRecursivo(actual.getIzq(), dato));
            actual.getIzq().setPadre(actual);
        } else if (comparacion > 0) {
            actual.setDer(insertarRecursivo(actual.getDer(), dato));
            actual.getDer().setPadre(actual);
        }

        return actual;
    }

    public void eliminarDato(T dato) {
        this.nodoRaiz = eliminarRecursivo(this.nodoRaiz, dato);
    }

    private Nodo<T> eliminarRecursivo(Nodo<T> actual, T dato) {
        if (actual == null) {
            return null;
        }

        int comparacion = dato.compareTo(actual.getDato());

        if (comparacion < 0) {
            actual.setIzq(eliminarRecursivo(actual.getIzq(), dato));
        } else if (comparacion > 0) {
            actual.setDer(eliminarRecursivo(actual.getDer(), dato));
        } else {
            if (actual.getIzq() == null && actual.getDer() == null) {
                return null;
            }

            if (actual.getIzq() == null) {
                actual.getDer().setPadre(actual.getPadre());
                return actual.getDer();
            } else if (actual.getDer() == null) {
                actual.getIzq().setPadre(actual.getPadre());
                return actual.getIzq();
            }

            Nodo<T> sucesor = encontrarMinimo(actual.getDer());
            actual.setDato(sucesor.getDato());
            actual.setDer(eliminarRecursivo(actual.getDer(), sucesor.getDato()));
        }
        return actual;
    }

    public boolean buscarDato(T dato) {
        return buscarRecursivo(this.nodoRaiz, dato);
    }

    private boolean buscarRecursivo(Nodo<T> actual, T dato) {
        if (actual == null) {
            return false;
        }
        if (dato.equals(actual.getDato())) {
            return true;
        }
        return (dato.compareTo(actual.getDato()) < 0)
                ? buscarRecursivo(actual.getIzq(), dato)
                : buscarRecursivo(actual.getDer(), dato);
    }

    public T getValorRaiz() {
        return (this.nodoRaiz == null) ? null : this.nodoRaiz.getDato();
    }

    public int calcularAltura() {
        return alturaRecursiva(this.nodoRaiz);
    }

    private int alturaRecursiva(Nodo<T> actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + Math.max(alturaRecursiva(actual.getIzq()), alturaRecursiva(actual.getDer()));
    }

    public int encontrarNivel(T dato) {
        return nivelRecursivo(this.nodoRaiz, dato, 1);
    }

    private int nivelRecursivo(Nodo<T> actual, T dato, int nivel) {
        if (actual == null) {
            return 0;
        }
        if (dato.equals(actual.getDato())) {
            return nivel;
        }
        
        int nivelIzquierdo = nivelRecursivo(actual.getIzq(), dato, nivel + 1);
        if (nivelIzquierdo != 0) {
            return nivelIzquierdo;
        }
        
        return nivelRecursivo(actual.getDer(), dato, nivel + 1);
    }

    public int contarNodosHoja() {
        return contarHojasRecursivo(this.nodoRaiz);
    }

    private int contarHojasRecursivo(Nodo<T> actual) {
        if (actual == null) {
            return 0;
        }
        if (actual.getIzq() == null && actual.getDer() == null) {
            return 1;
        }
        return contarHojasRecursivo(actual.getIzq()) + contarHojasRecursivo(actual.getDer());
    }

    public T buscarMinimo() {
        if (esVacio()) {
            return null;
        }
        return encontrarMinimo(this.nodoRaiz).getDato();
    }

    private Nodo<T> encontrarMinimo(Nodo<T> actual) {
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    public T buscarMaximo() {
        if (esVacio()) {
            return null;
        }
        Nodo<T> actual = this.nodoRaiz;
        while (actual.getDer() != null) {
            actual = actual.getDer();
        }
        return actual.getDato();
    }

    public void recorridoAmplitud() {
        if (this.nodoRaiz == null) {
            return;
        }
        Queue<Nodo<T>> cola = new LinkedList<>();
        cola.add(this.nodoRaiz);
        
        while (!cola.isEmpty()) {
            Nodo<T> actual = cola.poll();
            System.out.print(actual.getDato() + " ");
            
            if (actual.getIzq() != null) {
                cola.add(actual.getIzq());
            }
            if (actual.getDer() != null) {
                cola.add(actual.getDer());
            }
        }
        System.out.println();
    }

    public void recorridoInorden() {
        inordenRecursivo(this.nodoRaiz);
        System.out.println();
    }

    private void inordenRecursivo(Nodo<T> actual) {
        if (actual != null) {
            inordenRecursivo(actual.getIzq());
            System.out.print(actual.getDato() + " ");
            inordenRecursivo(actual.getDer());
        }
    }

    public void recorridoPreorden() {
        preordenRecursivo(this.nodoRaiz);
        System.out.println();
    }

    private void preordenRecursivo(Nodo<T> actual) {
        if (actual != null) {
            System.out.print(actual.getDato() + " ");
            preordenRecursivo(actual.getIzq());
            preordenRecursivo(actual.getDer());
        }
    }

    public void recorridoPostorden() {
        postordenRecursivo(this.nodoRaiz);
        System.out.println();
    }

    private void postordenRecursivo(Nodo<T> actual) {
        if (actual != null) {
            postordenRecursivo(actual.getIzq());
            postordenRecursivo(actual.getDer());
            System.out.print(actual.getDato() + " ");
        }
    }
}