package hibernateAPP.Metodos;

import hibernateAPP.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Metodos {
    private EntityManagerFactory entityManagerFactory;

    public Metodos() {
        //configuro el EntityManager de objeto pesado 
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public Usuario verificarLogin(String nombre, String clave) {
        //creo la instancia de la interfaz inicial que interactua con la base de datos
        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            // Consulta para verificar si existe un usuario con el nombre y clave proporcionados
            String url = "SELECT u FROM Usuario u WHERE LOWER(u.nombre) = LOWER(:nombre) AND u.clave = :clave";
            TypedQuery<Usuario> query = em.createQuery(url, Usuario.class); //creo consulta dinamica
            query.setParameter("nombre", nombre);
            query.setParameter("clave", clave);

            List<Usuario> usuarios = query.getResultList();

            if (!usuarios.isEmpty()) {
                return usuarios.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error al verificar el login: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void cerrarConexion() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    //listo todos los usuarios
    public void listarUsuarios() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // consulta para seleccionar solo los ID y nombre de los usuarios
            List<Object[]> usuarios = entityManager.createQuery("SELECT u.id, u.nombre FROM Usuario u", Object[].class).getResultList();

            System.out.println("\n--- Listado de Usuarios ---");

            // Iteramos sobre los resultados y mostramos el ID y el nombre
            for (Object[] usuario : usuarios) {
                Integer id = (Integer) usuario[0]; // ID
                String nombre = (String) usuario[1]; // Nombre
                System.out.println("ID: " + id + " | Nombre: " + nombre);
            }

        } finally {
            entityManager.close();
        }
    }

    //Agrego un nuevo Usuario
    public void agregarUsuario() {
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            //Iniciamos una transaccion
            entityManager.getTransaction().begin();

            //creo un nuevo usuario
            Usuario usuario = new Usuario();

            //solicito los datos del usuario
            System.out.println("\n--- Agregar Usuario ---");

            System.out.print("Ingrese el nombre del usuario: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese la clave del usuario: ");
            String clave = scanner.nextLine();

            System.out.print("Ingrese el teléfono del usuario: ");
            String telefono = scanner.nextLine();

            System.out.print("Ingrese el correo electrónico del usuario: ");
            String correoElectronico = scanner.nextLine();

            System.out.print("Ingrese el código postal: ");
            String codigoPostal = scanner.nextLine();

            System.out.print("Ingrese la provincia: ");
            String provincia = scanner.nextLine();

            System.out.print("Ingrese la dirección: ");
            String direccion = scanner.nextLine();

            System.out.print("Ingrese la ciudad: ");
            String ciudad = scanner.nextLine();

            System.out.print("Ingrese el DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Ingrese el cargo (Administrador, Usuario, Gestor): ");
            String cargo = scanner.nextLine();

            System.out.print("Ingrese la fecha de acceso (formato: yyyy-MM-dd): ");
            String fechaAccesoStr = scanner.nextLine();
            LocalDate fechaAcceso = LocalDate.parse(fechaAccesoStr);

            System.out.print("Ingrese la fecha del ultimo acceso (formato: yyyy-MM-dd): ");
            String fechaUltimoAccStr = scanner.nextLine();
            LocalDate fechaUltimoAcc = LocalDate.parse(fechaUltimoAccStr);

            System.out.print("¿Consentimiento dado? (true/false): ");
            Boolean consentimiento = scanner.nextBoolean();
            scanner.nextLine();

            //asignacion de los valores al objeto usuario
            usuario.setNombre(nombre);
            usuario.setClave(clave);
            usuario.setTelefono(telefono);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setCodigoPostal(codigoPostal);
            usuario.setProvincia(provincia);
            usuario.setDireccion(direccion);
            usuario.setCiudad(ciudad);
            usuario.setDni(dni);
            usuario.setCargo(cargo);
            usuario.setFechaAcceso(fechaAcceso);
            usuario.setFechaUltimoAcc(fechaUltimoAcc);
            usuario.setConsentimiento(consentimiento);

            //persistencia al nuevo usuario en la base de datos
            entityManager.persist(usuario);

            //confirmacion de la transaccion
            entityManager.getTransaction().commit();

            System.out.println("Usuario agregado Correctamente. ");

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error al agregar el usuario: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void actualizarUsuario() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        // Pedir el ID del usuario a actualizar
        System.out.print("Ingrese el ID del usuario a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        // Buscar al usuario en la base de datos
        Usuario usuario = entityManager.find(Usuario.class, id);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            entityManager.close();
            return;
        }

        // Preguntar al usuario qué campo desea actualizar
        System.out.println("¿Qué campo desea actualizar?");
        System.out.println("1. Nombre");
        System.out.println("2. Clave");
        System.out.println("3. Teléfono");
        System.out.println("4. Correo Electrónico");
        System.out.println("5. Código Postal");
        System.out.println("6. Provincia");
        System.out.println("7. Dirección");
        System.out.println("8. Ciudad");
        System.out.println("9. DNI");
        System.out.println("10. Fecha de Acceso");
        System.out.println("11. Fecha del Último Acceso");
        System.out.println("12. Consentimiento");

        System.out.print("Seleccione el numero del campo que desea actualizar (o presione Enter para salir): ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Iniciar una transacción
        entityManager.getTransaction().begin();

        // Según la opción, actualizar solo el campo correspondiente
        switch (opcion) {
            case 1:
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                usuario.setNombre(nuevoNombre);
                break;
            case 2:
                System.out.print("Ingrese la nueva clave: ");
                String nuevaClave = scanner.nextLine();
                usuario.setClave(nuevaClave);
                break;
            case 3:
                System.out.print("Ingrese el nuevo teléfono: ");
                String nuevoTelefono = scanner.nextLine();
                usuario.setTelefono(nuevoTelefono);
                break;
            case 4:
                System.out.print("Ingrese el nuevo correo electrónico: ");
                String nuevoCorreo = scanner.nextLine();
                usuario.setCorreoElectronico(nuevoCorreo);
                break;
            case 5:
                System.out.print("Ingrese el nuevo código postal: ");
                String nuevoCodigoPostal = scanner.nextLine();
                usuario.setCodigoPostal(nuevoCodigoPostal);
                break;
            case 6:
                System.out.print("Ingrese la nueva provincia: ");
                String nuevaProvincia = scanner.nextLine();
                usuario.setProvincia(nuevaProvincia);
                break;
            case 7:
                System.out.print("Ingrese la nueva dirección: ");
                String nuevaDireccion = scanner.nextLine();
                usuario.setDireccion(nuevaDireccion);
                break;
            case 8:
                System.out.print("Ingrese la nueva ciudad: ");
                String nuevaCiudad = scanner.nextLine();
                usuario.setCiudad(nuevaCiudad);
                break;
            case 9:
                System.out.print("Ingrese el nuevo DNI: ");
                String nuevoDni = scanner.nextLine();
                usuario.setDni(nuevoDni);
                break;
            case 10:
                System.out.print("Ingrese la nueva fecha de acceso (formato: yyyy-MM-dd): ");
                String fechaAccesoStr = scanner.nextLine();
                LocalDate nuevaFechaAcceso = LocalDate.parse(fechaAccesoStr);
                usuario.setFechaAcceso(nuevaFechaAcceso);
                break;
            case 11:
                System.out.print("Ingrese la nueva fecha del último acceso (formato: yyyy-MM-dd): ");
                String fechaUltimoAccStr = scanner.nextLine();
                LocalDate nuevaFechaUltimoAcc = LocalDate.parse(fechaUltimoAccStr);
                usuario.setFechaUltimoAcc(nuevaFechaUltimoAcc);
                break;
            case 12:
                System.out.print("¿Consentimiento dado? (true/false): ");
                Boolean nuevoConsentimiento = scanner.nextBoolean();
                usuario.setConsentimiento(nuevoConsentimiento);
                break;
            default:
                System.out.println("Opción no válida.");
                entityManager.getTransaction().rollback(); // Revertir la transacción si la opción no es válida
                entityManager.close();
                return;
        }

        // Confirmar la transacción si se realizó algún cambio
        entityManager.getTransaction().commit();
        System.out.println("¡Usuario actualizado exitosamente!");

        // Cerrar el EntityManager
        entityManager.close();
    }

    public void eliminarUsuario() {

        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Iniciar una transacción
            entityManager.getTransaction().begin();

            // Solicitar el ID del usuario a eliminar
            System.out.print("Ingrese el ID del usuario a eliminar: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Buscar el usuario por su ID
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);

            if (usuario != null) {
                // Eliminar el usuario si existe
                entityManager.remove(usuario);
                System.out.println("¡Usuario eliminado exitosamente!");
            } else {
                // Si no se encuentra el usuario, mostrar un mensaje de error
                System.out.println("Usuario con ID " + idUsuario + " no encontrado.");
            }

            // Confirmar la transacción
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            // Si ocurre un error, revertir la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }
    }
}