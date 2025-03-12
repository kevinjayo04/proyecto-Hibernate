package hibernateAPP.Metodos;

import hibernateAPP.Notificacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MetodosNotificacion {
    private EntityManagerFactory entityManagerFactory;

    public MetodosNotificacion() {
        //configuro el EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void listarNotificacion() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // Consulta para obtener todas las notificaciones de la base de datos
            List<Notificacion> notificaciones = entityManager.createQuery("SELECT n FROM Notificacion n", Notificacion.class).getResultList();

            // Verificar si hay notificaciones disponibles
            if (notificaciones.isEmpty()) {
                System.out.println("No hay notificaciones en la base de datos.");
            } else {
                System.out.println("\n--- Listado de Notificaciones ---");
                for (Notificacion notificacion : notificaciones) {
                    System.out.println("ID Notificación: " + notificacion.getId());
                    System.out.println("Fecha: " + notificacion.getFecha());
                    System.out.println("Estado: " + (notificacion.getEstado() ? "Activa" : "Inactiva"));
                    System.out.println("Contenido: " + notificacion.getContenido());
                    System.out.println("Tipo: " + notificacion.getTipo());
                    System.out.println("-----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar las notificaciones: " + e.getMessage());
        } finally {
            entityManager.close(); // Cerrar el EntityManager después de la operación
        }
    }

    public void agregarNotificacion() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // Iniciar transacción
            entityManager.getTransaction().begin();

            // Crear una nueva notificación
            Notificacion notificacion = new Notificacion();

            // Solicitar la fecha
            System.out.print("Ingrese la fecha de la notificación (yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            LocalDate fecha = LocalDate.parse(fechaStr);  // Convertir a LocalDate
            notificacion.setFecha(fecha);

            // Solicitar el estado (1: Activa, 0: Inactiva)
            System.out.print("Ingrese el estado de la notificación (1: Activa, 0: Inactiva): ");
            int estadoInt = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer
            notificacion.setEstado(estadoInt == 1);  // Convertir a booleano (true: activa, false: inactiva)

            // Solicitar el contenido de la notificación
            System.out.print("Ingrese el contenido de la notificación: ");
            String contenido = scanner.nextLine();
            notificacion.setContenido(contenido);

            // Solicitar el tipo de la notificación
            System.out.print("Ingrese el tipo de la notificación: ");
            String tipo = scanner.nextLine();
            notificacion.setTipo(tipo);

            // Persistir la nueva notificación
            entityManager.persist(notificacion);

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            System.out.println("¡Notificación agregada exitosamente!");

        } catch (Exception e) {
            // Revertir la transacción en caso de error
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al agregar la notificación: " + e.getMessage());
        } finally {
            entityManager.close(); // Cerrar el EntityManager después de la operación
        }

    }

    public void modificarNotificacion() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Solicitar el ID de la notificación a modificar
        System.out.print("Ingrese el ID de la notificación a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Buscar la notificación en la base de datos
        Notificacion notificacion = entityManager.find(Notificacion.class, id);

        if (notificacion == null) {
            System.out.println("Notificación no encontrada.");
            entityManager.close();
            return;
        }

        // Mostrar los detalles actuales de la notificación
        System.out.println("\n--- Notificación Actual ---");
        System.out.println("ID Notificación: " + notificacion.getId());
        System.out.println("Fecha: " + notificacion.getFecha());
        System.out.println("Estado: " + (notificacion.getEstado() ? "Activa" : "Inactiva"));
        System.out.println("Contenido: " + notificacion.getContenido());
        System.out.println("Tipo: " + notificacion.getTipo());

        // Solicitar qué campo desea modificar
        System.out.println("\n¿Qué campo desea modificar?");
        System.out.println("1. Fecha");
        System.out.println("2. Estado");
        System.out.println("3. Contenido");
        System.out.println("4. Tipo");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (opcion) {
            case 1:
                // Modificar fecha
                System.out.print("Ingrese la nueva fecha (yyyy-MM-dd): ");
                String fechaStr = scanner.nextLine();
                LocalDate nuevaFecha = LocalDate.parse(fechaStr);  // Convertir a LocalDate
                notificacion.setFecha(nuevaFecha);
                break;
            case 2:
                // Modificar estado
                System.out.print("Ingrese el nuevo estado (1: Activa, 0: Inactiva): ");
                int estadoInt = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                notificacion.setEstado(estadoInt == 1);  // Convertir a booleano
                break;
            case 3:
                // Modificar contenido
                System.out.print("Ingrese el nuevo contenido de la notificación: ");
                String nuevoContenido = scanner.nextLine();
                notificacion.setContenido(nuevoContenido);
                break;
            case 4:
                // Modificar tipo
                System.out.print("Ingrese el nuevo tipo de notificación: ");
                String nuevoTipo = scanner.nextLine();
                notificacion.setTipo(nuevoTipo);
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        // Iniciar la transacción para guardar los cambios
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(notificacion); // Actualizar la notificación
            entityManager.getTransaction().commit();
            System.out.println("¡Notificación modificada exitosamente!");
        } catch (Exception e) {
            // En caso de error, revertimos la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al modificar la notificación: " + e.getMessage());
        } finally {
            entityManager.close(); // Cerramos el EntityManager después de la operación
        }

    }

    public void eliminarNotificacion() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Solicitar el ID de la notificación a eliminar
        System.out.print("Ingrese el ID de la notificación a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Buscar la notificación en la base de datos
        Notificacion notificacion = entityManager.find(Notificacion.class, id);

        if (notificacion == null) {
            System.out.println("Notificación no encontrada.");
            entityManager.close();
            return;
        }

        // Mostrar los detalles de la notificación antes de eliminarla
        System.out.println("\n--- Notificación a Eliminar ---");
        System.out.println("ID Notificación: " + notificacion.getId());
        System.out.println("Fecha: " + notificacion.getFecha());
        System.out.println("Estado: " + (notificacion.getEstado() ? "Activa" : "Inactiva"));
        System.out.println("Contenido: " + notificacion.getContenido());
        System.out.println("Tipo: " + notificacion.getTipo());

        // Confirmar si el usuario quiere eliminar la notificación
        System.out.print("\n¿Está seguro de que desea eliminar esta notificación? (si/no): ");
        String confirmacion = scanner.nextLine();

        if (!confirmacion.equalsIgnoreCase("si")) {
            System.out.println("La notificación no ha sido eliminada.");
            entityManager.close();
            return;
        }

        // Iniciar la transacción
        entityManager.getTransaction().begin();

        try {
            // Eliminar la notificación
            entityManager.remove(notificacion);
            entityManager.getTransaction().commit(); // Confirmamos la transacción
            System.out.println("¡Notificación eliminada exitosamente!");
        } catch (Exception e) {
            // En caso de error, revertimos la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar la notificación: " + e.getMessage());
        } finally {
            entityManager.close(); // Cerramos el EntityManager después de la operación
        }

    }

}
