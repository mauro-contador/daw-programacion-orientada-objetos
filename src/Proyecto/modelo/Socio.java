package Proyecto.modelo;

import java.sql.Date;

public class Socio {
    private String dniSocio;
    private String nombre;
    private String apellidos;
    private String direccion;
    private int codigoPostal;
    private String telefono;
    private Date fechaNacimiento;
    private Date fechaAlta;
    private int idTipoSocio;

    // Constructor completo
    public Socio(String dniSocio, String nombre, String apellidos, String direccion,
                 int codigoPostal, String telefono, Date fechaNacimiento,
                 Date fechaAlta, int idTipoSocio) {
        this.dniSocio = dniSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.idTipoSocio = idTipoSocio;
    }

    // Getters
    public String getDniSocio() { return dniSocio; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDireccion() { return direccion; }
    public int getCodigoPostal() { return codigoPostal; }
    public String getTelefono() { return telefono; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public Date getFechaAlta() { return fechaAlta; }
    public int getIdTipoSocio() { return idTipoSocio; }
}