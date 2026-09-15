package Proyecto.modelo.bbdd_mysql;

import Proyecto.modelo.ISocioDAO;
import Proyecto.modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAOMySQL implements ISocioDAO {

    @Override
    public List<Socio> listarTodos() {
        List<Socio> lista = new ArrayList<>();
        String sql = "SELECT * FROM socio";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Socio(
                        rs.getString("dni_socio"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("direccion"),
                        rs.getInt("codigo_postal"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getDate("fecha_alta"),
                        rs.getInt("id_tipo_socio")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public boolean insertar(Socio s) {
        String sql = "INSERT INTO socio (dni_socio, nombre, apellidos, direccion, codigo_postal, telefono, fecha_nacimiento, fecha_alta, id_tipo_socio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return ejecutarActualizacion(sql, s);
    }

    @Override
    public boolean actualizar(Socio s) {
        String sql = "UPDATE socio SET nombre=?, apellidos=?, direccion=?, codigo_postal=?, telefono=?, fecha_nacimiento=?, fecha_alta=?, id_tipo_socio=? WHERE dni_socio=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getNombre());
            ps.setString(2, s.getApellidos());
            ps.setString(3, s.getDireccion());
            ps.setInt(4, s.getCodigoPostal());
            ps.setString(5, s.getTelefono());
            ps.setDate(6, s.getFechaNacimiento());
            ps.setDate(7, s.getFechaAlta());
            ps.setInt(8, s.getIdTipoSocio());
            ps.setString(9, s.getDniSocio()); // El WHERE va al final

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String dniSocio) {
        String sql = "DELETE FROM socio WHERE dni_socio = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dniSocio);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para no repetir tanto código en el INSERT
    private boolean ejecutarActualizacion(String sql, Socio s) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getDniSocio());
            ps.setString(2, s.getNombre());
            ps.setString(3, s.getApellidos());
            ps.setString(4, s.getDireccion());
            ps.setInt(5, s.getCodigoPostal());
            ps.setString(6, s.getTelefono());
            ps.setDate(7, s.getFechaNacimiento());
            ps.setDate(8, s.getFechaAlta());
            ps.setInt(9, s.getIdTipoSocio());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}