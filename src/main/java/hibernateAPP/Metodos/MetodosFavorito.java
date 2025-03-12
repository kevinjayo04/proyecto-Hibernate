package hibernateAPP.Metodos;

import hibernateAPP.Favorito;
import hibernateAPP.Proyecto;
import hibernateAPP.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static hibernateAPP.Metodos.DatosPersistentes.usuarioActual;

public class MetodosFavorito {

    private EntityManagerFactory entityManagerFactory;


    public MetodosFavorito() {
        //configuro el EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void listarFavoritos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Consulta para obtener todos los favoritos
            List<Favorito> favoritos = entityManager.createQuery("SELECT f FROM Favorito f", Favorito.class).getResultList();

            if (favoritos.isEmpty()) {
                System.out.println("No hay favoritos en la base de datos.");
            } else {
                System.out.println("\n--- Listado de Favoritos ---");
                for (Favorito favorito : favoritos) {
                    System.out.println("ID Proyecto: " + favorito.getIdProyecto().getNombre());
                    System.out.println("ID Usuario: " + favorito.getIdUsuario().getNombre());
                    System.out.println("Fecha Guardado: " + favorito.getFechaGuardado());
                    System.out.println("Estado: " + favorito.getEstado());
                    System.out.println("-----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar favoritos: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void agregarFavorito(Usuario usuarioActual) {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Crear un nuevo favorito
            Favorito favorito = new Favorito();

            // Solicitar el ID del proyecto que se desea agregar a favoritos
            System.out.print("Ingrese el ID del proyecto que desea agregar a favoritos: ");
            int idProyecto = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Buscar el proyecto en la base de datos
            Proyecto proyecto = entityManager.find(Proyecto.class, idProyecto);

            if (proyecto == null) {
                System.out.println("Proyecto no encontrado.");
                entityManager.getTransaction().rollback();
                return;
            }

            // Verificar que el usuario actual no sea nulo
            if (usuarioActual == null) {
                System.out.println("No hay un usuario autenticado.");
                entityManager.getTransaction().rollback();
                return;
            }

            // Asignar los valores al objeto Favorito
            favorito.setIdProyecto(proyecto);
            favorito.setIdUsuario(usuarioActual);  // Asociar al usuario actual
            favorito.setFechaGuardado(LocalDate.now());
            favorito.setEstado("Iniciado");

            // Persistir el nuevo favorito
            entityManager.persist(favorito);

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            System.out.println("¡Favorito agregado exitosamente!");

        } catch (Exception e) {
            // Revertir la transacción en caso de error
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al agregar el favorito: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void eliminarFavorito() {
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.print("Ingrese el ID del favorito a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Buscar el favorito en la base de datos
        Favorito favorito = entityManager.find(Favorito.class, id);

        if (favorito == null) {
            System.out.println("Favorito no encontrado.");
            entityManager.close();
            return;
        }

        try {
            // Iniciar transacción
            entityManager.getTransaction().begin();

            // Eliminar el favorito
            entityManager.remove(favorito);

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            System.out.println("¡Favorito eliminado exitosamente!");

        } catch (Exception e) {
            // Revertir la transacción si hay un error
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error al eliminar el favorito: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
