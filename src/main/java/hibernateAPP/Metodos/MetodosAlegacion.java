package hibernateAPP.Metodos;

import hibernateAPP.Alegacion;
import hibernateAPP.Proyecto;
import hibernateAPP.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MetodosAlegacion {
    private EntityManagerFactory entityManagerFactory;

    public MetodosAlegacion() {
        //configuro el EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void listarAlegacion(){
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            //realizo la consulta
            List<Alegacion> alegaciones = entityManager.createQuery("SELECT a FROM Alegacion a", Alegacion.class).getResultList();

            //comprubeo si se encuentra alegacione
            if (alegaciones.isEmpty()){
                System.out.println("No hay Alegaciones");
            } else {
                System.out.println("\n--- Listado de Alegaciones ---");
                // Mostrar los detalles de cada alegación
                for (Alegacion alegacion : alegaciones) {
                    System.out.println("ID: " + alegacion.getId());
                    System.out.println("Estado: " + alegacion.getEstado());
                    System.out.println("Contenido: " + alegacion.getContenido());
                    System.out.println("Fecha de Registro: " + alegacion.getFechaRegistro());
                    System.out.println("Fecha de Resolución: " + alegacion.getFechaResolucion());
                    System.out.println("Reviado: " + alegacion.getReviado());
                    System.out.println("Firma: " + alegacion.getFirma());
                    System.out.println("Proyecto: " + alegacion.getIdProyecto().getNombre());
                    System.out.println("Usuario: " + alegacion.getIdUsuario().getNombre());
                    System.out.println("-----------------------------------------");
            }
        }
        } catch (Exception e) {
            System.out.println("Error al listar Alegaciones: " + e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public void agregarAlegacion() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // Iniciar una transacción
            entityManager.getTransaction().begin();

            // Crear un nuevo objeto Alegacion
            Alegacion alegacion = new Alegacion();

            // Solicitar los datos de la alegación
            System.out.print("Ingrese el estado de la alegación: ");
            String estado = scanner.nextLine();

            System.out.print("Ingrese el contenido de la alegación: ");
            String contenido = scanner.nextLine();

            System.out.print("Ingrese la fecha de registro (formato: yyyy-MM-dd): ");
            String fechaRegistroStr = scanner.nextLine();
            LocalDate fechaRegistro = LocalDate.parse(fechaRegistroStr);  // Convertir a LocalDate

            System.out.print("Ingrese la fecha de resolución (formato: yyyy-MM-dd, o deje vacío si no ha sido resuelta): ");
            String fechaResolucionStr = scanner.nextLine();
            LocalDate fechaResolucion = (fechaResolucionStr.isEmpty()) ? null : LocalDate.parse(fechaResolucionStr); // Permitir fecha null

            System.out.print("¿Está reviado? (true/false): ");
            Boolean reviado = scanner.nextBoolean();
            scanner.nextLine();  // Limpiar el buffer

            System.out.print("Ingrese la firma: ");
            String firma = scanner.nextLine();

            // Solicitar el ID del usuario y del proyecto relacionados con la alegación
            System.out.print("Ingrese el ID del usuario relacionado con la alegación: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            // Buscar el usuario en la base de datos
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                entityManager.getTransaction().rollback();  // Revertir si no se encuentra el usuario
                return;
            }

            System.out.print("Ingrese el ID del proyecto relacionado con la alegación: ");
            int idProyecto = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            // Buscar el proyecto en la base de datos
            Proyecto proyecto = entityManager.find(Proyecto.class, idProyecto);
            if (proyecto == null) {
                System.out.println("Proyecto no encontrado.");
                entityManager.getTransaction().rollback();  // Revertir si no se encuentra el proyecto
                return;
            }

            // Asignar los valores al objeto Alegacion
            alegacion.setEstado(estado);
            alegacion.setContenido(contenido);
            alegacion.setFechaRegistro(fechaRegistro);
            alegacion.setFechaResolucion(fechaResolucion);
            alegacion.setReviado(reviado);
            alegacion.setFirma(firma);
            alegacion.setIdUsuario(usuario);  // Asociar el usuario
            alegacion.setIdProyecto(proyecto);  // Asociar el proyecto

            // Persistir la nueva alegación en la base de datos
            entityManager.persist(alegacion);

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            System.out.println("¡Alegación agregada exitosamente!");

        } catch (Exception e) {
            // Si ocurre un error, revertir la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al agregar la alegación: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }
    }

    public void actualizarAlegacion() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // Pedir el ID de la alegación a actualizar
            System.out.print("Ingrese el ID de la alegación a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Buscar la alegación en la base de datos
            Alegacion alegacion = entityManager.find(Alegacion.class, id);

            if (alegacion == null) {
                System.out.println("Alegación no encontrada.");
                entityManager.close();
                return;
            }

            // Mostrar las opciones de qué campo desea actualizar
            System.out.println("¿Qué campo desea actualizar?");
            System.out.println("1. Estado");
            System.out.println("2. Contenido");
            System.out.println("3. Fecha de Resolución");
            System.out.println("4. Reviado");
            System.out.println("5. Firma");
            System.out.println("6. Volver al Menú");

            System.out.print("Seleccione el número del campo que desea actualizar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Iniciar una transacción
            entityManager.getTransaction().begin();

            // Según la opción, actualizar solo el campo correspondiente
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nuevo estado de la alegación: ");
                    String nuevoEstado = scanner.nextLine();
                    alegacion.setEstado(nuevoEstado);
                    break;
                case 2:
                    System.out.print("Ingrese el nuevo contenido de la alegación: ");
                    String nuevoContenido = scanner.nextLine();
                    alegacion.setContenido(nuevoContenido);
                    break;
                case 3:
                    System.out.print("Ingrese la nueva fecha de resolución (formato: yyyy-MM-dd): ");
                    String nuevaFechaResolucionStr = scanner.nextLine();
                    LocalDate nuevaFechaResolucion = LocalDate.parse(nuevaFechaResolucionStr);
                    alegacion.setFechaResolucion(nuevaFechaResolucion);
                    break;
                case 4:
                    System.out.print("¿Está reviado? (true/false): ");
                    Boolean nuevoReviado = scanner.nextBoolean();
                    alegacion.setReviado(nuevoReviado);
                    break;
                case 5:
                    System.out.print("Ingrese la nueva firma: ");
                    String nuevaFirma = scanner.nextLine();
                    alegacion.setFirma(nuevaFirma);
                    break;
                case 6:
                    System.out.println("Volviendo al menú...");
                    entityManager.getTransaction().rollback();  // Revertir los cambios si se elige volver
                    entityManager.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
                    entityManager.getTransaction().rollback();  // Revertir los cambios si la opción no es válida
                    entityManager.close();
                    return;
            }

            // Confirmar la transacción si se realizó algún cambio
            entityManager.getTransaction().commit();

            System.out.println("¡Alegación actualizada exitosamente!");

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error al actualizar alegacion: " + e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public void eliminarAlegacion() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Pedir el ID de la alegación a eliminar
        System.out.print("Ingrese el ID de la alegación a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Buscar la alegación en la base de datos
        Alegacion alegacion = entityManager.find(Alegacion.class, id);

        if (alegacion == null) {
            System.out.println("Alegacion no encontrada.");
            entityManager.close();
            return;
        }

        try {
            // Iniciar una transacción
            entityManager.getTransaction().begin();

            // Eliminar la alegación
            entityManager.remove(alegacion);

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            System.out.println("¡Alegación eliminada exitosamente!");

        } catch (Exception e) {
            // Si ocurre un error, revertir la transacción
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar la alegación: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }
    }

}