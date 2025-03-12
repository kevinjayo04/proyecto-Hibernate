package hibernateAPP.Metodos;

import hibernateAPP.Usuario;

public class DatosPersistentes {
    static Usuario usuarioActual;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }
    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static String getCargoUsuario() {
        if (usuarioActual != null){
            return usuarioActual.getCargo();
        }
        return null;
    }
}
