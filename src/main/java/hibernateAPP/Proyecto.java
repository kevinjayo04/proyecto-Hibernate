package hibernateAPP;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "proyecto")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProyecto", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario")
    private hibernateAPP.Usuario idUsuario;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "fecha_ultima_modificacion")
    private LocalDate fechaUltimaModificacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    @Column(name = "es_empresa")
    private Boolean esEmpresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public hibernateAPP.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(hibernateAPP.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(LocalDate fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getEsEmpresa() {
        return esEmpresa;
    }

    public void setEsEmpresa(Boolean esEmpresa) {
        this.esEmpresa = esEmpresa;
    }

/*
 TODO [Reverse Engineering] create field to map the 'ubicacion' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "ubicacion", columnDefinition = "point")
    private Object ubicacion;
*/
}