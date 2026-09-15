package Proyecto.modelo;

import java.util.List;

public interface ISocioDAO {
    List<Socio> listarTodos();
    boolean insertar(Socio s);
    boolean actualizar(Socio s);
    boolean eliminar(String dniSocio);
}