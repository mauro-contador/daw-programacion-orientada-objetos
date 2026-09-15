package Proyecto.vista.gui;

import javax.swing.*;
import java.awt.*;

public class VistaListadoSocios extends JFrame {
    public JTable tablaSocios;
    public JComboBox<String> comboTipoSocio;
    public JButton btnRefrescar, btnIrAAñadir, btnBorrar;

    public VistaListadoSocios() {
        setTitle("Gestión de Socios - R.C.D. Carabanchel");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Superior: Filtros y Botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Filtrar por Tipo:"));

        comboTipoSocio = new JComboBox<>(new String[]{
                "Todos", "Infantil", "Juvenil", "Normal sin Grada", "Normal con Grada", "Jubilado", "Jubilado Especial"
        });
        btnRefrescar = new JButton("Refrescar");
        btnIrAAñadir = new JButton("Añadir Socio");
        btnBorrar = new JButton("Dar de Baja");

        panelSuperior.add(comboTipoSocio);
        panelSuperior.add(btnRefrescar);
        panelSuperior.add(btnIrAAñadir);
        panelSuperior.add(btnBorrar);

        // Tabla de Socios
        tablaSocios = new JTable();
        tablaSocios.setDefaultEditor(Object.class, null); // Evita edición directa en la tabla

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaSocios), BorderLayout.CENTER);
        setLocationRelativeTo(null); // Centrar en pantalla
    }
}