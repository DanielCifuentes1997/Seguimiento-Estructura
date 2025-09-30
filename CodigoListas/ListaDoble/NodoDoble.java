package CodigoListas.ListaDoble;

// # Representa un nodo para una lista doblemente enlazada.
// # Almacena un valor y mantiene dos referencias: una al nodo siguiente
// # y otra al nodo anterior, permitiendo el recorrido bidireccional.
public class NodoDoble<T>
{
    private T valor;
    private NodoDoble<T> siguiente;
    private NodoDoble<T> anterior;

    public NodoDoble(T valor)
    {
        this.valor = valor;
        this.siguiente = null;
        this.anterior = null;
    }

    public T getValor()
    {
        return this.valor;
    }

    public void setValor(T valor)
    {
        this.valor = valor;
    }

    public NodoDoble<T> getSiguiente()
    {
        return this.siguiente;
    }

    public void setSiguiente(NodoDoble<T> siguiente)
    {
        this.siguiente = siguiente;
    }

    public NodoDoble<T> getAnterior()
    {
        return this.anterior;
    }

    public void setAnterior(NodoDoble<T> anterior)
    {
        this.anterior = anterior;
    }
}