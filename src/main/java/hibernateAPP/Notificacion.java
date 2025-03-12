package hibernateAPP;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "estado")
    private Boolean estado;

    @Lob
    @Column(name = "contenido")
    private String contenido;

    @Lob
    @Column(name = "tipo")
    private String tipo;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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