package hibernateAPP.Metodos;

import hibernateAPP.Informe;
import hibernateAPP.Proyecto;
import hibernateAPP.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MetodosInforme {
    private EntityManagerFactory entityManagerFactory;


    public MetodosInforme() {
        //configuro el EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void listarInformes() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Consulta para obtener todos los informes de la base de datos
            List<Informe> informes = entityManager.createQuery("SELECT i FROM Informe i", Informe.class).getResultList();

            // Verificar si hay informes disponibles
            if (informes.isEmpty()) {
                System.out.println("No hay informes en la base de datos.");
            } else {
                System.out.println("\n--- Listado de Informes ---");
                for (Informe informe : informes) {
                    System.out.println("ID Informe: " + informe.getId());
                    System.out.println("Nombre del Informe: " + informe.getNombre());
                    System.out.println("Fecha de Creación: " + informe.getFechaCreacion());
                    System.out.println("Fecha del Último Acceso: " + informe.getFechaUltimoacceso());
                    System.out.println("Documentacion: " + informe.getDocumento());
                    System.out.println("Descripción: " + informe.getDescripcion());
                    System.out.println("Estado: " + informe.getEstado());
                    System.out.println("-----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar informes: " + e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public void agregarInforme(Usuario usuarioActual) {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // Iniciar una transacción
            entityManager.getTransaction().begin();

            // Crear un nuevo informe
            Informe informe = new Informe();

            // Solicitar datos del informe
            System.out.print("Ingrese el nombre del informe: ");
            String nombre = scanner.nextLine();
            informe.setNombre(nombre);

            System.out.print("Ingrese la fecha de creación (yyyy-MM-dd): ");
            String fechaCreacionStr = scanner.nextLine();
            LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);  // Convertir a LocalDate
            informe.setFechaCreacion(fechaCreacion);

            System.out.print("Ingrese la fecha del último acceso (yyyy-MM-dd): ");
            String fechaUltimoAccesoStr = scanner.nextLine();
            LocalDate fechaUltimoAcceso = LocalDate.parse(fechaUltimoAccesoStr);  // Convertir a LocalDate
            informe.setFechaUltimoacceso(fechaUltimoAcceso);

            System.out.println("Ingrese nombre del documento: ");
            String documento = scanner.nextLine();
            informe.setDocumento(documento);

            System.out.print("Ingrese la descripción del informe: ");
            String descripcion = scanner.nextLine();
            informe.setDescripcion(descripcion);

            System.out.print("Ingrese el estado del informe (por ejemplo, 'En proceso', 'Finalizado'): ");
            String estado = scanner.nextLine();
            informe.setEstado(estado);

            // Solicitar el ID del proyecto
            System.out.print("Ingrese el ID del proyecto asociado al informe: ");
            int idProyecto = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Buscar el proyecto en la base de datos
            Proyecto proyecto = entityManager.find(Proyecto.class, idProyecto);

            if (proyecto == null) {
                System.out.println("Proyecto no encontrado.");
                entityManager.getTransaction().rollback();
                return;
            }

            // Asignar el proyecto al informe
            informe.setIdProyecto(proyecto);

            // Asignar el usuario actual (autenticado) al informe
            if (usuarioActual == null) {
                System.out.println("No hay un usuario autenticado.");
                entityManager.getTransaction().rollback();
                return;
            }

            informe.setIdUsuario(usuarioActual);  // Asociar el usuario actual al informe

            // Guardar el informe en la base de datos
            entityManager.persist(informe);

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            System.out.println("¡Informe creado exitosamente!");

        } catch (Exception e) {
            // Revertir la transacción en caso de error
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al crear el informe: " + e.getMessage());
        } finally {
            entityManager.close();
        }


    }

    public void modificarInforme() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Solicitar el ID del informe a actualizar
        System.out.print("Ingrese el ID del informe a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Buscar el informe en la base de datos
        Informe informe = entityManager.find(Informe.class, id);

        if (informe == null) {
            System.out.println("Informe no encontrado.");
            entityManager.close();
            return;
        }

        // Mostrar el informe actual
        System.out.println("\n--- Informe Actual ---");
        System.out.println("ID Informe: " + informe.getId());
        System.out.println("Nombre: " + informe.getNombre());
        System.out.println("Fecha de Creación: " + informe.getFechaCreacion());
        System.out.println("Fecha del Último Acceso: " + informe.getFechaUltimoacceso());
        System.out.println("Descripción: " + informe.getDescripcion());
        System.out.println("Estado: " + informe.getEstado());
        System.out.println("Documentación: " + informe.getDocumento()); // Mostrar el contenido actual de la documentación

        // Solicitar qué campo desea modificar
        System.out.println("\n¿Qué campo desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Fecha de Creación");
        System.out.println("3. Fecha del Último Acceso");
        System.out.println("4. Descripción");
        System.out.println("5. Estado");
        System.out.println("6. Documentación (Texto)");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (opcion) {
            case 1:
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                informe.setNombre(nuevoNombre); // Actualizar el nombre
                break;
            case 2:
                System.out.print("Ingrese la nueva fecha de creación (yyyy-MM-dd): ");
                String nuevaFechaCreacion = scanner.nextLine();
                informe.setFechaCreacion(LocalDate.parse(nuevaFechaCreacion)); // Actualizar fecha de creación
                break;
            case 3:
                System.out.print("Ingrese la nueva fecha del último acceso (yyyy-MM-dd): ");
                String nuevaFechaUltimoAcceso = scanner.nextLine();
                informe.setFechaUltimoacceso(LocalDate.parse(nuevaFechaUltimoAcceso)); // Actualizar fecha del último acceso
                break;
            case 4:
                System.out.print("Ingrese la nueva descripción: ");
                String nuevaDescripcion = scanner.nextLine();
                informe.setDescripcion(nuevaDescripcion); // Actualizar descripción
                break;
            case 5:
                System.out.print("Ingrese el nuevo estado: ");
                String nuevoEstado = scanner.nextLine();
                informe.setEstado(nuevoEstado); // Actualizar estado
                break;
            case 6:
                System.out.print("Ingrese la nueva documentación (texto largo): ");
                String nuevaDocumentacion = scanner.nextLine();
                informe.setDocumento(nuevaDocumentacion); // Actualizar documentación
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        // Iniciar una transacción para guardar los cambios
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(informe); // Actualizar el informe
            entityManager.getTransaction().commit();
            System.out.println("¡Informe actualizado exitosamente!");
        } catch (Exception e) {
            // Si ocurre un error, revertimos la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al actualizar el informe: " + e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public void eliminarInforme() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Solicitar el ID del informe a eliminar
        System.out.print("Ingrese el ID del informe a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Buscar el informe en la base de datos
        Informe informe = entityManager.find(Informe.class, id);

        // Verificamos si el informe existe
        if (informe == null) {
            System.out.println("Informe no encontrado.");
            entityManager.close();
            return;
        }

        // Mostrar los detalles del informe antes de eliminarlo
        System.out.println("\n--- Informe a Eliminar ---");
        System.out.println("ID Informe: " + informe.getId());
        System.out.println("Nombre: " + informe.getNombre());
        System.out.println("Fecha de Creación: " + informe.getFechaCreacion());
        System.out.println("Fecha del Último Acceso: " + informe.getFechaUltimoacceso());
        System.out.println("Descripción: " + informe.getDescripcion());
        System.out.println("Estado: " + informe.getEstado());
        System.out.println("Documentación: " + informe.getDocumento());  // Mostrar documento si es necesario

        // Confirmar si el usuario quiere eliminar el informe
        System.out.print("\n¿Está seguro de que desea eliminar este informe? (si/no): ");
        String confirmacion = scanner.nextLine();

        if (!confirmacion.equalsIgnoreCase("si")) {
            System.out.println("El informe no ha sido eliminado.");
            entityManager.close();
            return;
        }

        // Iniciar la transacción
        entityManager.getTransaction().begin();

        try {
            // Eliminar el informe
            entityManager.remove(informe);
            entityManager.getTransaction().commit(); // Confirmamos la transacción
            System.out.println("¡Informe eliminado exitosamente!");
        } catch (Exception e) {
            // En caso de error, revertimos la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar el informe: " + e.getMessage());
        } finally {
            entityManager.close(); // Cerramos el EntityManager después de la operación
        }
    }

}
