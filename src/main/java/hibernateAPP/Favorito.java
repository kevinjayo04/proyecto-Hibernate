package hibernateAPP;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "favorito")
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private hibernateAPP.Proyecto idProyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private hibernateAPP.Usuario idUsuario;

    @Column(name = "fecha_guardado")
    private LocalDate fechaGuardado;

    @Lob
    @Column(name = "estado")
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getFechaGuardado() {
        return fechaGuardado;
    }

    public void setFechaGuardado(LocalDate fechaGuardado) {
        this.fechaGuardado = fechaGuardado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}