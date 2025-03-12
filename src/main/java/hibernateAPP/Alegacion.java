package hibernateAPP;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "alegaciones")
public class Alegacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "estado")
    private String estado;

    @Lob
    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "fecha_resolucion")
    private LocalDate fechaResolucion;

    @Column(name = "reviado")
    private Boolean reviado;

    @Column(name = "firma", length = 40)
    private String firma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private hibernateAPP.Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private hibernateAPP.Proyecto idProyecto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Boolean getReviado() {
        return reviado;
    }

    public void setReviado(Boolean reviado) {
        this.reviado = reviado;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public hibernateAPP.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(hibernateAPP.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public hibernateAPP.Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(hibernateAPP.Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

}