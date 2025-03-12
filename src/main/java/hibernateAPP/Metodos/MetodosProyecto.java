package hibernateAPP.Metodos;

import hibernateAPP.Proyecto;
import hibernateAPP.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MetodosProyecto {

    private EntityManagerFactory entityManagerFactory;


    public MetodosProyecto() {
        //configuro el EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void listarProyectos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            //consulta para obtener todos los proyectos
            List<Proyecto> proyectos = entityManager.createQuery("SELECT p FROM Proyecto p", Proyecto.class).getResultList();

            //comprobar si se encuentrar proyectos
            if (proyectos.isEmpty()){
                System.out.println("No hay proyectos");
            } else {
                System.out.println("\n--- Listado de Proyectos ---");
                //itero sobre los proyectos y muestro los datos
                for (Proyecto proyecto : proyectos) {
                    System.out.println("ID: " + proyecto.getId());
                    System.out.println("Nombre: " + proyecto.getNombre());
                    System.out.println("Descripción: " + proyecto.getDescripcion());
                    System.out.println("Fecha Inicio: " + proyecto.getFechaCreacion());
                    System.out.println("Fecha Fin: " + proyecto.getFechaFin());
                    System.out.println("-----------------------------------------");
                }
            }
        } finally {
            entityManager.close();
        }
    }

    public void agregarProyecto() {

        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            //inicio una transaccion
            entityManager.getTransaction().begin();

            //creo un nuevo objeto Proyecto
            Proyecto proyecto = new Proyecto();

            //solicito los datos del nuevo proyecto
            System.out.println("Ingrese el nombre del proyecto: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese la descripcion del proyecto: ");
            String descripcion = scanner.nextLine();

            System.out.println("Ingrese el estado del proyecto (Aprobado provisional, En tramite, Aprobado Definitivo): ");
            String estado = scanner.nextLine();

            System.out.println("Ingrese la fecha de creacion del proyecto (formato: yyyy-MM-dd): ");
            String fecha = scanner.nextLine();
            LocalDate fechaCreacion = LocalDate.parse(fecha); //convierte a LocalDate

            System.out.println("Ingrese la fecha de ultima modificacion (formato: yyyy-MM-dd): ");
            String ultimaModificacion = scanner.nextLine();
            LocalDate fechaModificacion = LocalDate.parse(fecha);

            System.out.println("Ingrese la fecha de fin de proyecto (formato yyyy-MM-dd): ");
            String FinProyecto = scanner.nextLine();
            LocalDate fechafin = LocalDate.parse(fecha);

            System.out.println("¿Es una empresa? (true/false): ");
            Boolean esEmpresa = scanner.nextBoolean();
            scanner.nextLine();

            System.out.println("Ingrese el id del usuario asociado al proyecto: ");
            int idUsuario = scanner.nextInt();

            Usuario usuario = entityManager.find(Usuario.class, idUsuario);

            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                entityManager.getTransaction().rollback(); //revertimos la transaccion
                return;
            }
            //asignamos los valores al objeto Proyecto
            proyecto.setNombre(nombre);
            proyecto.setDescripcion(descripcion);
            proyecto.setEstado(estado);
            proyecto.setFechaCreacion(fechaCreacion);
            proyecto.setFechaUltimaModificacion(fechaModificacion);
            proyecto.setFechaFin(fechafin);
            proyecto.setEsEmpresa(esEmpresa);
            proyecto.setIdUsuario(usuario);

            //persisto el nuevo proyecto a base de datos
            entityManager.persist(proyecto);

            //confirmo la transaccion
            entityManager.getTransaction().commit();

            System.out.println("Proyecto agregado Correctamente");

        } catch (Exception e) {
            //si ocurre un erro, revierte la transaccion
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error al agregar el proyecto: " + e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public void modificarProyecto() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            //pedido el id del proyecto a modificar
            System.out.println("Ingrese el ID del proyecto para modificar: ");
            int idProyecto = scanner.nextInt();
            scanner.nextLine();

            //busco el proyecto en la base de datos
            Proyecto proyecto = entityManager.find(Proyecto.class, idProyecto);

            if (proyecto == null) {
                System.out.println("Proyecto no encontrado");
                entityManager.close();
                return;
            }

            // Mostrar qué campo quiere actualizar
            System.out.println("¿Qué campo desea actualizar?");
            System.out.println("1. Nombre");
            System.out.println("2. Descripción");
            System.out.println("3. Estado");
            System.out.println("4. Fecha de fin");
            System.out.println("5. Volver al Menú");

            System.out.println("Seleccione el numero del campo a modificar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            //inicio la transaccion
            entityManager.getTransaction().begin();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nuevo nombre del proyecto: ");
                    String nuevoNombre = scanner.nextLine();
                    proyecto.setNombre(nuevoNombre);
                    break;
                case 2:
                    System.out.print("Ingrese la nueva descripción del proyecto: ");
                    String nuevaDescripcion = scanner.nextLine();
                    proyecto.setDescripcion(nuevaDescripcion);
                    break;
                case 3:
                    System.out.print("Ingrese el nuevo estado del proyecto: ");
                    String nuevoEstado = scanner.nextLine();
                    proyecto.setEstado(nuevoEstado);
                    break;
                case 4:
                    System.out.print("Ingrese la nueva fecha de fin del proyecto (formato: yyyy-MM-dd): ");
                    String nuevaFechaFinStr = scanner.nextLine();
                    LocalDate nuevaFechaFin = LocalDate.parse(nuevaFechaFinStr);
                    proyecto.setFechaFin(nuevaFechaFin);
                    break;
                case 5:
                    System.out.println("Volviendo al menú...");
                    entityManager.getTransaction().rollback(); // Revertir cambios si se elige volver
                    entityManager.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
                    entityManager.getTransaction().rollback(); // Revertir cambios si la opción no es válida
                    entityManager.close();
                    return;
            }

            //confirmo la transaccion
            entityManager.getTransaction().commit();
            System.out.println("proyecto actualizado correctamente");

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error al actualizar el proyecto: " + e.getMessage());
        } finally {
            entityManager.close();

        }

    }

    public void eliminarProyecto() {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Pido el id del proyecto para eliminar
        System.out.println("Ingrese el ID del proyecto para ELIMINAR: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        //busco el proyecto del id
        Proyecto proyecto = entityManager.find(Proyecto.class, id);
        if (proyecto == null) {
            System.out.println("Proyecto no encontrado");
            entityManager.close();
            return;
        }
        try {
            //inicio al transaccion
            entityManager.getTransaction().begin();

            //elimino el proyecto
            entityManager.remove(proyecto);

            //confirmo la transaccion
            entityManager.getTransaction().commit();

            System.out.println("Proyecto eliminado correctamente.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error al eliminar el proyecto: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }



}
