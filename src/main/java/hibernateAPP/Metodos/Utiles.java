package hibernateAPP.Metodos;


import hibernateAPP.Usuario;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Utiles {
    private Metodos metodos;
    private MetodosProyecto metodosProyecto;
    private MetodosAlegacion metodosAlegacion;
    private MetodosFavorito metodosFavorito;
    private MetodosInforme metodosInforme;
    private MetodosNotificacion metodosNotificacion;

    public Utiles() {
        this.metodos = new Metodos(); //instancia de la clase
        this.metodosProyecto = new MetodosProyecto();
        this.metodosAlegacion = new MetodosAlegacion();
        this.metodosFavorito = new MetodosFavorito();
        this.metodosInforme = new MetodosInforme();
        this.metodosNotificacion = new MetodosNotificacion();
    }

    public boolean validarEntradas(String entrada) {
        String regx = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9_@.-]+$";
        return Pattern.matches(regx, entrada);

    }


    public void login(){
        Metodos metodos = new Metodos();
        Scanner scanner = new Scanner(System.in);

        System.out.println("----- Login -----");

        while (true) {
            System.out.print("Ingrese su nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese su clave: ");
            String clave = scanner.nextLine();

            Usuario usuarioAutenticado = metodos.verificarLogin(nombre, clave);

            //valida entradas
            if (!validarEntradas(nombre) || !validarEntradas(clave)) {
                System.out.println("Entrada de caracteres no valida");
                continue;
            }


            //valido las credenciales de nombre y clave de la base de datos
            if (usuarioAutenticado != null) {

                System.out.println("Cargo del usuario: " + usuarioAutenticado.getCargo());

                DatosPersistentes.setUsuarioActual(usuarioAutenticado);

                System.out.println("¡Login exitoso! Bienvenido, " + nombre + ".");
                break;
            } else {
                System.out.println("Nombre o clave incorrectos. Intente de nuevo.");
            }
        }

        metodos.cerrarConexion(); // Cierra el EntityManagerFactory
    }

    public void mostrarMenu(){
        Scanner scanner2 = new Scanner(System.in);


        System.out.println("\n----- Menú Principal -----");
        System.out.println("1. Gestionar Usuarios");
        System.out.println("2. Gestionar Proyectos");
        System.out.println("3. Gestionar Alegaciones");
        System.out.println("4. Gestionar Favoritos");
        System.out.println("5. Gestionar Informes");
        System.out.println("6. Gestionar Notificaciones");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");


        int opcion = 0;

            try {
                opcion = scanner2.nextInt();
                scanner2.nextLine();

                switch (opcion) {
                    case 1:
                        if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador")) {
                            gestionarUsuarios();
                        } else {
                            System.out.println("Acceso denegado. Solo los Admimnistradores pueden acceder");
                        }
                        break;
                    case 2:
                        gestionProyectos();
                        break;
                    case 3:
                        gestionAlegaciones();
                        break;
                    case 4:
                        gestionFavorito();
                        break;
                    case 5:
                        gestionInformes();
                        break;
                    case 6:
                        gestionNotificacion();
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        break;
                }
            } catch (Exception e){
                System.out.println("Error en el menu: " + e.getMessage());
                scanner2.nextLine();
            }

    }

    public void gestionarUsuarios() {
        Scanner scanner = new Scanner(System.in);
        boolean volverAlMenuPrincipal = false; // Bandera para volver al menú principal

        while (!volverAlMenuPrincipal) {
            System.out.println("\n----- Gestión de Usuarios -----");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Agregar Usuario");
            System.out.println("3. Actualizar Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    metodos.listarUsuarios();
                    break;
                case 2:
                    metodos.agregarUsuario();

                    break;
                case 3:
                    metodos.actualizarUsuario();

                    break;
                case 4:
                    metodos.eliminarUsuario();

                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    volverAlMenuPrincipal = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        mostrarMenu();
    }

    public void gestionProyectos() {
        Scanner scanner = new Scanner(System.in);
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n----- Gestión de Proyectos -----");
            System.out.println("1. Listar Proyectos");
            System.out.println("2. Agregar Proyecto");
            System.out.println("3. Actualizar Proyecto");
            System.out.println("4. Eliminar Proyecto");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    metodosProyecto.listarProyectos();
                    break;
                case 2:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador") ||
                            DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")) {
                        metodosProyecto.agregarProyecto();
                    } else {
                        System.out.println("Acceso denegado. Intente nuevamente. Solo los Gestores pueden Agregar Proyectos.");
                    }
                    break;
                case 3:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador") ||
                    DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")) {
                        metodosProyecto.modificarProyecto();

                    } else {
                        System.out.println("Acceso denegado. Intente nuevamente.Solo los Gestores pueden Modificar Proyectos");
                    }
                    break;
                case 4:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador") ||
                    DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")) {
                        metodosProyecto.eliminarProyecto();
                    } else {
                        System.out.println("Acceso denegado, Solo los gestores pueden Eliminar Proyectos");
                    }
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    volverAlMenuPrincipal = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        mostrarMenu();

    }

    public void gestionAlegaciones() {
        Scanner scanner = new Scanner(System.in);
        boolean volverAlMenuPrincipal = false;
        while (!volverAlMenuPrincipal) {
            System.out.println("\n----- Gestión de Alegaciones -----");
            System.out.println("1. Listar Alegaciones");
            System.out.println("2. Agregar Alegación");
            System.out.println("3. Actualizar Alegación");
            System.out.println("4. Eliminar Alegación");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    metodosAlegacion.listarAlegacion();
                    break;
                case 2:
                    metodosAlegacion.agregarAlegacion();
                    break;
                case 3:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador")
                    ||DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")) {
                        metodosAlegacion.actualizarAlegacion();
                    } else {
                        System.out.println("Acceso denegado. Solo los gestores pueden modificar alegaciones.");
                    }
                    break;
                case 4:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador" )||
                            DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")){
                        metodosAlegacion.eliminarAlegacion();
                    } else {
                        System.out.println("Acceso denegado. Solo los gestores pueden eliminar la alegacion.");
                    }

                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    volverAlMenuPrincipal = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");

            }
        }
        mostrarMenu();
    }

    public void gestionFavorito() {
        Scanner scanner = new Scanner(System.in);
        boolean volverAlMenuPrincipal = false;
        while (!volverAlMenuPrincipal) {
            System.out.println("\n----- Gestión de Favoritos -----");
            System.out.println("1. Listar Favoritos");
            System.out.println("2. Agregar Favorito");
            System.out.println("3. Eliminar Favorito");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    metodosFavorito.listarFavoritos();
                    break;
                case 2:
                    metodosFavorito.agregarFavorito(DatosPersistentes.getUsuarioActual());
                    break;
                case 3:
                    metodosFavorito.eliminarFavorito();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    volverAlMenuPrincipal = true; // Cambiar bandera a true para salir del ciclo y regresar al menú principal
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        mostrarMenu();
    }

    public void gestionInformes() {
        Scanner scanner = new Scanner(System.in);
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n----- Gestión de Informes -----");
            System.out.println("1. Listar Informes");
            System.out.println("2. Agregar Informe");
            System.out.println("3. Modificar Informe");
            System.out.println("4. Eliminar Informe");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    metodosInforme.listarInformes();
                    break;
                case 2:
                    metodosInforme.agregarInforme(DatosPersistentes.getUsuarioActual());
                    break;
                case 3:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador") ||
                    DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")) {
                        metodosInforme.modificarInforme();
                    } else {
                        System.out.println("Acceso denegado. Solo los gestores pueden modificar los informes");
                    }
                    break;
                case 4:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador") ||
                    DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Gestor")) {
                        metodosInforme.eliminarInforme();
                    } else {
                        System.out.println("Acceso denegado. Solo los gestores pueden eliminar Informes.");
                    }
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    volverAlMenuPrincipal = true; // Cambiar bandera a true para salir del ciclo y regresar al menú principal
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        mostrarMenu();
    }

    public void gestionNotificacion() {
        Scanner scanner = new Scanner(System.in);
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n----- Gestión de Notificaciones -----");
            System.out.println("1. Listar Notificaciones");
            System.out.println("2. Agregar Notificación");
            System.out.println("3. Modificar Notificación");
            System.out.println("4. Eliminar Notificación");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    metodosNotificacion.listarNotificacion();
                    break;
                case 2:if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador")) {
                    metodosNotificacion.agregarNotificacion();
                } else {
                    System.out.println("Acceso denegado. Solo los administradores pueden agregar notificaciones");
                }
                    break;
                case 3:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador")) {
                        metodosNotificacion.modificarNotificacion();
                    } else {
                        System.out.println("Acceso denegado. Solo los Administradores pueden modificar notifiaciones");
                    }
                    break;
                case 4:
                    if (DatosPersistentes.getUsuarioActual().getCargo().equalsIgnoreCase("Administrador")) {
                        metodosNotificacion.eliminarNotificacion();
                    } else {
                        System.out.println("Acceso denegado. Solo los Administradores pueden eliminar notificaciones");
                    }
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    volverAlMenuPrincipal = true; // Cambiar bandera a true para salir del ciclo y regresar al menú principal
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        mostrarMenu();
    }



}