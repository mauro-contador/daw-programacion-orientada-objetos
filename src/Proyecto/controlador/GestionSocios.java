package Proyecto.controlador;

import java.util.List;
import Proyecto.modelo.Socio;
import Proyecto.modelo.ISocioDAO;
import Proyecto.modelo.bbdd_mysql.SocioDAOMySQL;

public class GestionSocios {
    private ISocioDAO dao;

    public GestionSocios() {
        this.dao = new SocioDAOMySQL(); // Siempre usamos BBDD en este caso
    }

    public List<Socio> obtenerTodosLosSocios() {
        return dao.listarTodos();
    }

    public boolean registrarNuevoSocio(Socio s) {
        return dao.insertar(s);
    }

    public boolean modificarSocio(Socio s) {
        return dao.actualizar(s);
    }

    public boolean borrarSocio(String dni) {
        return dao.eliminar(dni);
    }
}