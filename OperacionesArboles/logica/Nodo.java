package OperacionesArboles.logica;

public class Nodo<T> {
    
    private Nodo<T> padre;
    private Nodo<T> izq;
    private Nodo<T> der;
    private T dato;

    public Nodo(T dato) {
        this.padre = null;
        this.izq = null;
        this.der = null;
        this.dato = dato;
    }

    public Nodo<T> getPadre() {
        return padre;
    }

    public void setPadre(Nodo<T> padre) {
        this.padre = padre;
    }

    public Nodo<T> getIzq() {
        return izq;
    }

    public void setIzq(Nodo<T> izq) {
        this.izq = izq;
    }

    public Nodo<T> getDer() {
        return der;
    }

    public void setDer(Nodo<T> der) {
        this.der = der;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }
}