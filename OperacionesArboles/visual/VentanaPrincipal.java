package OperacionesArboles.visual;

import OperacionesArboles.logica.ArbolBinario;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final ArbolBinario<Integer> miArbol;
    private final LienzoArbol<Integer> lienzo;
    
    private final JTextField campoValor;
    private final JButton botonInsertar;
    private final JButton botonEliminar;
    private final JButton botonBuscar;
    private final JButton botonLimpiar;
    private final JTextArea areaConsola;

    public VentanaPrincipal() {
        this.miArbol = new ArbolBinario<>();

        setTitle("Visor de Árbol Binario");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.lienzo = new LienzoArbol<>(this.miArbol);
        this.lienzo.setPreferredSize(new Dimension(600, 700));

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridBagLayout());
        panelControles.setBackground(new Color(60, 60, 60));
        panelControles.setPreferredSize(new Dimension(300, 700));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel etiquetaTitulo = new JLabel("Operaciones");
        etiquetaTitulo.setForeground(Color.WHITE);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        etiquetaTitulo.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        panelControles.add(etiquetaTitulo, gbc);

        JLabel etiquetaValor = new JLabel("Valor (entero):");
        etiquetaValor.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelControles.add(etiquetaValor, gbc);

        campoValor = new JTextField();
        gbc.gridx = 1;
        panelControles.add(campoValor, gbc);

        botonInsertar = new JButton("Insertar");
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panelControles.add(botonInsertar, gbc);

        botonEliminar = new JButton("Eliminar");
        gbc.gridy = 3;
        panelControles.add(botonEliminar, gbc);

        botonBuscar = new JButton("Buscar");
        gbc.gridy = 4;
        panelControles.add(botonBuscar, gbc);
        
        botonLimpiar = new JButton("Borrar Árbol");
        botonLimpiar.setBackground(new Color(200, 80, 80));
        botonLimpiar.setForeground(Color.WHITE);
        gbc.gridy = 5;
        panelControles.add(botonLimpiar, gbc);

        areaConsola = new JTextArea(10, 20);
        areaConsola.setEditable(false);
        areaConsola.setBackground(Color.BLACK);
        areaConsola.setForeground(Color.GREEN);
        areaConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaConsola.setText("--- Consola de Recorridos ---\n");
        JScrollPane scrollConsola = new JScrollPane(areaConsola);
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelControles.add(scrollConsola, gbc);

        add(lienzo, BorderLayout.CENTER);
        add(panelControles, BorderLayout.EAST);

        configurarAcciones();
    }

    private void configurarAcciones() {
        botonInsertar.addActionListener(e -> ejecutarAccion(Accion.INSERTAR));
        botonEliminar.addActionListener(e -> ejecutarAccion(Accion.ELIMINAR));
        botonBuscar.addActionListener(e -> ejecutarAccion(Accion.BUSCAR));
        botonLimpiar.addActionListener(e -> ejecutarAccion(Accion.LIMPIAR));
    }
    
    private enum Accion { INSERTAR, ELIMINAR, BUSCAR, LIMPIAR }

    private void ejecutarAccion(Accion accion) {
        if (accion == Accion.LIMPIAR) {
            miArbol.limpiarArbol();
            lienzo.actualizarArbol(miArbol);
            areaConsola.setText("--- Árbol borrado ---\n");
            return;
        }

        String textoValor = campoValor.getText();
        if (textoValor.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int valor = Integer.parseInt(textoValor);
            
            switch (accion) {
                case INSERTAR:
                    miArbol.insertar(valor);
                    areaConsola.setText("Valor " + valor + " insertado.\n");
                    actualizarRecorridos();
                    break;
                case ELIMINAR:
                    if (!miArbol.buscarDato(valor)) {
                        JOptionPane.showMessageDialog(this, "El valor " + valor + " no existe en el árbol.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        miArbol.eliminarDato(valor);
                        areaConsola.setText("Valor " + valor + " eliminado.\n");
                        actualizarRecorridos();
                    }
                    break;
                case BUSCAR:
                    boolean encontrado = miArbol.buscarDato(valor);
                    if (encontrado) {
                        lienzo.resaltarNodo(valor);
                        JOptionPane.showMessageDialog(this, "Valor " + valor + " encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Valor " + valor + " no encontrado.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
            }
            
            campoValor.setText("");
            lienzo.actualizarArbol(miArbol);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese solo números enteros.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarRecorridos() {
        areaConsola.append("\n--- Información Actual ---\n");
        areaConsola.append("Altura: " + miArbol.calcularAltura() + "\n");
        areaConsola.append("Hojas: " + miArbol.contarNodosHoja() + "\n");
        areaConsola.append("Mínimo: " + miArbol.buscarMinimo() + "\n");
        areaConsola.append("Máximo: " + miArbol.buscarMaximo() + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}