package OperacionesArboles;

import OperacionesArboles.visual.VentanaPrincipal;
import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("No se pudo aplicar el LookAndFeel del sistema.");
        }

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}