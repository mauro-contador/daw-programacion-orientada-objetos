package Proyecto.vista.gui;

import Proyecto.controlador.GestionSocios;
import Proyecto.modelo.Socio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EscuchadorSocios implements ActionListener {
    private VistaListadoSocios vListado;
    private VistaFormularioSocio vFormulario;
    private GestionSocios servicio;

    public EscuchadorSocios(VistaListadoSocios vListado, VistaFormularioSocio vFormulario, GestionSocios servicio) {
        this.vListado = vListado;
        this.vFormulario = vFormulario;
        this.servicio = servicio;

        // Listeners Botones
        this.vListado.btnRefrescar.addActionListener(this);
        this.vListado.btnIrAAñadir.addActionListener(this);
        this.vListado.btnBorrar.addActionListener(this);
        this.vFormulario.btnCancelar.addActionListener(this);
        this.vFormulario.btnGuardar.addActionListener(this);

        // Doble Clic para Editar
        this.vListado.tablaSocios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = vListado.tablaSocios.getSelectedRow();
                    if (fila != -1) {
                        prepararEdicion(fila);
                    }
                }
            }
        });

        refrescarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vListado.btnRefrescar) {
            refrescarTabla();
        } else if (e.getSource() == vListado.btnIrAAñadir) {
            limpiarFormulario();
            vFormulario.esEdicion = false;
            vFormulario.txtDni.setEnabled(true); // DNI editable en alta
            vFormulario.setVisible(true);
            vListado.setVisible(false);
        } else if (e.getSource() == vFormulario.btnCancelar) {
            vFormulario.setVisible(false);
            vListado.setVisible(true);
        } else if (e.getSource() == vFormulario.btnGuardar) {
            guardarOActualizarSocio();
        } else if (e.getSource() == vListado.btnBorrar) {
            borrarSocio();
        }
    }

    private void refrescarTabla() {
        String[] columnas = {"DNI", "Nombre", "Apellidos", "Dirección", "C.P.", "Teléfono", "F. Nac", "F. Alta", "Tipo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        List<Socio> listaCompleta = servicio.obtenerTodosLosSocios();
        String filtro = (String) vListado.comboTipoSocio.getSelectedItem();

        List<Socio> listaFiltrada = new ArrayList<>();

        // Filtro local (se podría hacer en BD, pero lo hacemos aquí por simplicidad)
        if (filtro.equals("Todos")) {
            listaFiltrada = listaCompleta;
        } else {
            int idFiltro = obtenerIdTipoSocio(filtro);
            for(Socio s : listaCompleta){
                if(s.getIdTipoSocio() == idFiltro){
                    listaFiltrada.add(s);
                }
            }
        }

        for (Socio s : listaFiltrada) {
            Object[] fila = {
                    s.getDniSocio(), s.getNombre(), s.getApellidos(), s.getDireccion(),
                    s.getCodigoPostal(), s.getTelefono(), s.getFechaNacimiento(),
                    s.getFechaAlta(), obtenerNombreTipoSocio(s.getIdTipoSocio())
            };
            modelo.addRow(fila);
        }
        vListado.tablaSocios.setModel(modelo);
    }

    private void prepararEdicion(int fila) {
        String dni = (String) vListado.tablaSocios.getValueAt(fila, 0);
        String nombre = (String) vListado.tablaSocios.getValueAt(fila, 1);
        String apellidos = (String) vListado.tablaSocios.getValueAt(fila, 2);
        String direccion = (String) vListado.tablaSocios.getValueAt(fila, 3);
        int cp = (int) vListado.tablaSocios.getValueAt(fila, 4);
        String telefono = (String) vListado.tablaSocios.getValueAt(fila, 5);
        Date fNac = (Date) vListado.tablaSocios.getValueAt(fila, 6);
        Date fAlta = (Date) vListado.tablaSocios.getValueAt(fila, 7);
        String tipo = (String) vListado.tablaSocios.getValueAt(fila, 8);

        vFormulario.txtDni.setText(dni);
        vFormulario.txtDni.setEnabled(false); // DNI no editable en edición
        vFormulario.txtNombre.setText(nombre);
        vFormulario.txtApellidos.setText(apellidos);
        vFormulario.txtDireccion.setText(direccion);
        vFormulario.txtCodigoPostal.setText(String.valueOf(cp));
        vFormulario.txtTelefono.setText(telefono);
        vFormulario.txtFechaNacimiento.setText(fNac.toString());
        vFormulario.txtFechaAlta.setText(fAlta.toString());
        vFormulario.comboTipoSocio.setSelectedItem(tipo);

        vFormulario.esEdicion = true;
        vFormulario.setVisible(true);
        vListado.setVisible(false);
    }

    private void guardarOActualizarSocio() {
        try {
            String dni = vFormulario.txtDni.getText();
            String nombre = vFormulario.txtNombre.getText();
            String apellidos = vFormulario.txtApellidos.getText();
            String direccion = vFormulario.txtDireccion.getText();
            int cp = Integer.parseInt(vFormulario.txtCodigoPostal.getText());
            String telefono = vFormulario.txtTelefono.getText();
            Date fNac = Date.valueOf(vFormulario.txtFechaNacimiento.getText());
            Date fAlta = Date.valueOf(vFormulario.txtFechaAlta.getText());
            int idTipo = obtenerIdTipoSocio((String) vFormulario.comboTipoSocio.getSelectedItem());

            Socio s = new Socio(dni, nombre, apellidos, direccion, cp, telefono, fNac, fAlta, idTipo);

            boolean exito;
            if (vFormulario.esEdicion) {
                exito = servicio.modificarSocio(s);
            } else {
                exito = servicio.registrarNuevoSocio(s);
            }

            if (exito) {
                JOptionPane.showMessageDialog(vFormulario, "Operación realizada con éxito.");
                vFormulario.setVisible(false);
                vListado.setVisible(true);
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(vFormulario, "Error al guardar en BD.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vFormulario, "Error en formato de datos (Revisa Fechas o C.P.).");
        }
    }

    private void borrarSocio() {
        int fila = vListado.tablaSocios.getSelectedRow();
        if (fila != -1) {
            String dni = (String) vListado.tablaSocios.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(vListado, "¿Seguro que quieres dar de baja al socio con DNI " + dni + "?", "Confirmar Baja", JOptionPane.YES_NO_OPTION);

            if(confirmacion == JOptionPane.YES_OPTION){
                if(servicio.borrarSocio(dni)){
                    JOptionPane.showMessageDialog(vListado, "Socio dado de baja.");
                    refrescarTabla();
                } else {
                    JOptionPane.showMessageDialog(vListado, "Error al borrar en BD.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(vListado, "Selecciona un socio para borrar.");
        }
    }

    private void limpiarFormulario(){
        vFormulario.txtDni.setText("");
        vFormulario.txtNombre.setText("");
        vFormulario.txtApellidos.setText("");
        vFormulario.txtDireccion.setText("");
        vFormulario.txtCodigoPostal.setText("");
        vFormulario.txtTelefono.setText("");
        vFormulario.txtFechaNacimiento.setText("");
        vFormulario.txtFechaAlta.setText(new Date(System.currentTimeMillis()).toString()); // Fecha actual
        vFormulario.comboTipoSocio.setSelectedIndex(0);
    }

    // Auxiliares para IDs
    private int obtenerIdTipoSocio(String nombre) {
        switch (nombre) {
            case "Infantil": return 1;
            case "Juvenil": return 2;
            case "Normal sin Grada": return 3;
            case "Normal con Grada": return 4;
            case "Jubilado": return 5;
            case "Jubilado Especial": return 6;
            default: return 1;
        }
    }

    private String obtenerNombreTipoSocio(int id) {
        switch (id) {
            case 1: return "Infantil";
            case 2: return "Juvenil";
            case 3: return "Normal sin Grada";
            case 4: return "Normal con Grada";
            case 5: return "Jubilado";
            case 6: return "Jubilado Especial";
            default: return "Desconocido";
        }
    }
}