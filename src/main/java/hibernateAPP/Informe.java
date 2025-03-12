package hibernateAPP;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "informe")
public class Informe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "fecha_Creacion")
    private LocalDate fechaCreacion;

    @Column(name = "fecha_UltimoAcceso")
    private LocalDate fechaUltimoacceso;

    @Lob
    @Column(name = "documento")
    private String documento;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "estado")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private hibernateAPP.Proyecto idProyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private hibernateAPP.Usuario idUsuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaUltimoacceso() {
        return fechaUltimoacceso;
    }

    public void setFechaUltimoacceso(LocalDate fechaUltimoacceso) {
        this.fechaUltimoacceso = fechaUltimoacceso;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public hibernateAPP.Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(hibernateAPP.Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    public hibernateAPP.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(hibernateAPP.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

}