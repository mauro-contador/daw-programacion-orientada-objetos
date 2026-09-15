package Proyecto.vista.gui;

import javax.swing.*;
import java.awt.*;

public class VistaFormularioSocio extends JFrame {
    public JTextField txtDni, txtNombre, txtApellidos, txtDireccion, txtCodigoPostal, txtTelefono;
    public JTextField txtFechaNacimiento, txtFechaAlta;
    public JComboBox<String> comboTipoSocio;
    public JButton btnGuardar, btnCancelar;
    public boolean esEdicion = false; // Bandera para saber si estamos editando

    public VistaFormularioSocio() {
        setTitle("Formulario de Socio");
        setSize(400, 500);
        setLayout(new GridLayout(10, 2, 10, 10));

        add(new JLabel("DNI:")); txtDni = new JTextField(); add(txtDni);
        add(new JLabel("Nombre:")); txtNombre = new JTextField(); add(txtNombre);
        add(new JLabel("Apellidos:")); txtApellidos = new JTextField(); add(txtApellidos);
        add(new JLabel("Dirección:")); txtDireccion = new JTextField(); add(txtDireccion);
        add(new JLabel("C.P.:")); txtCodigoPostal = new JTextField(); add(txtCodigoPostal);
        add(new JLabel("Teléfono:")); txtTelefono = new JTextField(); add(txtTelefono);
        add(new JLabel("F. Nacimiento (YYYY-MM-DD):")); txtFechaNacimiento = new JTextField(); add(txtFechaNacimiento);
        add(new JLabel("F. Alta (YYYY-MM-DD):")); txtFechaAlta = new JTextField(); add(txtFechaAlta);

        add(new JLabel("Tipo Socio:"));
        comboTipoSocio = new JComboBox<>(new String[]{
                "Infantil", "Juvenil", "Normal sin Grada", "Normal con Grada", "Jubilado", "Jubilado Especial"
        });
        add(comboTipoSocio);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        add(btnGuardar);
        add(btnCancelar);

        setLocationRelativeTo(null);
    }
}