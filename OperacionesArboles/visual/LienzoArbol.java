package OperacionesArboles.visual;

import OperacionesArboles.logica.ArbolBinario;
import OperacionesArboles.logica.Nodo;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class LienzoArbol<T extends Comparable<T>> extends JPanel {

    private ArbolBinario<T> arbol;
    private final Set<Nodo<T>> nodosResaltados;
    private final Color colorFondo = new Color(45, 45, 45);
    private final Color colorNodo = new Color(102, 255, 255);
    private final Color colorTexto = Color.BLACK;
    private final Color colorLinea = Color.WHITE;
    private final Font fuenteNodo = new Font("Arial", Font.BOLD, 14);

    public LienzoArbol(ArbolBinario<T> arbol) {
        this.arbol = arbol;
        this.nodosResaltados = new HashSet<>();
        setBackground(colorFondo);
    }

    public void actualizarArbol(ArbolBinario<T> arbol) {
        this.arbol = arbol;
        this.nodosResaltados.clear();
        repaint();
    }
    
    public void limpiarResaltado() {
        this.nodosResaltados.clear();
        repaint();
    }

    public void resaltarNodo(T dato) {
        buscarYResaltar(arbol.getNodoRaiz(), dato);
        repaint();
    }
    
    private void buscarYResaltar(Nodo<T> actual, T dato) {
        if (actual == null) {
            return;
        }
        
        int comparacion = dato.compareTo(actual.getDato());
        
        if (comparacion == 0) {
            nodosResaltados.add(actual);
        } else if (comparacion < 0) {
            buscarYResaltar(actual.getIzq(), dato);
        } else {
            buscarYResaltar(actual.getDer(), dato);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arbol.getNodoRaiz() != null) {
            dibujarNodoRecursivo(g2d, arbol.getNodoRaiz(), getWidth() / 2, 50, getWidth() / 4);
        }
    }

    private void dibujarNodoRecursivo(Graphics2D g, Nodo<T> nodo, int x, int y, int hEspacio) {
        if (nodo == null) {
            return;
        }

        int diametro = 50;
        int radio = diametro / 2;
        
        if (nodosResaltados.contains(nodo)) {
            g.setColor(Color.ORANGE);
            g.fillOval(x - radio, y - radio, diametro, diametro);
        } else {
            g.setColor(colorNodo);
            g.fillOval(x - radio, y - radio, diametro, diametro);
        }
        
        g.setColor(colorTexto);
        g.setFont(fuenteNodo);
        String texto = nodo.getDato().toString();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, x - fm.stringWidth(texto) / 2, y + fm.getAscent() / 2 - 2);

        int yHijo = y + 80;
        int xIzquierdo = x - hEspacio;
        int xDerecho = x + hEspacio;

        g.setColor(colorLinea);
        if (nodo.getIzq() != null) {
            g.drawLine(x, y + radio, xIzquierdo, yHijo - radio);
            dibujarNodoRecursivo(g, nodo.getIzq(), xIzquierdo, yHijo, hEspacio / 2);
        }

        if (nodo.getDer() != null) {
            g.drawLine(x, y + radio, xDerecho, yHijo - radio);
            dibujarNodoRecursivo(g, nodo.getDer(), xDerecho, yHijo, hEspacio / 2);
        }
    }
}