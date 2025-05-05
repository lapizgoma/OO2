package test;

import model.Usuario;
import negocio.UsuarioABM;

public class TestCU010 {
    public static void main(String[] args) {
	    // objetivo: eliminar lógicamente un usuario y verificar que esté reflejado en la BD.

        // INICIALIZACIÓN
        UsuarioABM usuarioABM = new UsuarioABM();
        Usuario u = new Usuario(
            "test@cu011.com", 
            "011", 
            "Caso de Uso",
            "Difícil",
            false);

        Long uID = usuarioABM.agregar (u);
        
        // VALIDACIÓN: ¿cuál es el estado del usuario?
        Usuario u_db = usuarioABM.traer (uID);
        System.out.println ("Estado usuario antes de eliminar ==");
        System.out.print ("Está eliminado: ");
	    System.out.println (u_db.isDeleted ());
        
        // VALIDACIÓN: ¿se actualicó el estado (solo borrado) del usuario?
        u_db.setApellido("Cambié el apellido");
        usuarioABM.eliminar (u_db);
        System.out.println ("Estado usuario después de eliminar ==");
        System.out.print ("Está eliminado: ");
        u_db = usuarioABM.traer (uID);
        
        System.out.println (u_db.isDeleted ());
        System.out.println (
            "Solo se cambió el estado eliminado: " + !(u_db.getApellido ().equals("Cambié el apellido")));
        
	}
}
