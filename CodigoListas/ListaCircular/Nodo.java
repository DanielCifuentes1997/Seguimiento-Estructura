package CodigoListas.ListaCircular;

// # Representa la unidad fundamental de la lista.
// # Cada nodo contiene un valor y una referencia al siguiente nodo en la secuencia.
public class Nodo<T>
{
    private T valor;
    private Nodo<T> nodoSiguiente;

    public Nodo(T valor)
    {
        this.valor = valor;
        this.nodoSiguiente = null;
    }

    public T getValor()
    {
        return this.valor;
    }

    public void setValor(T valor)
    {
        this.valor = valor;
    }

    public Nodo<T> getNodoSiguiente()
    {
        return this.nodoSiguiente;
    }

    public void setNodoSiguiente(Nodo<T> nodoSiguiente)
    {
        this.nodoSiguiente = nodoSiguiente;
    }
}