package hibernateAPP;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity //Marca una clase como entidad para mapear la tabla a la base de datos
@Table(name = "usuario") // especificacion del nombre
public class Usuario {
    @Id//clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //definicion del valor de clave primaria
    @Column(name = "id", nullable = false) //configura el mapea de un atributo no acepta null
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "clave", nullable = false, length = 20)
    private String clave;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correoElectronico", length = 30)
    private String correoElectronico;

    @Column(name = "codigo_postal", length = 5)
    private String codigoPostal;

    @Column(name = "provincia", length = 20)
    private String provincia;

    @Column(name = "direccion", length = 20)
    private String direccion;

    @Column(name = "ciudad", length = 20)
    private String ciudad;

    @Column(name = "dni", length = 10)
    private String dni;

    @Lob //mapeo a tipos de grandes datos
    @Column(name = "cargo")
    private String cargo;

    @Column(name = "fechaAcceso")
    private LocalDate fechaAcceso;

    @Column(name = "fechaUltimoAcc")
    private LocalDate fechaUltimoAcc;

    @Column(name = "consentimiento")
    private Boolean consentimiento;

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(LocalDate fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public LocalDate getFechaUltimoAcc() {
        return fechaUltimoAcc;
    }

    public void setFechaUltimoAcc(LocalDate fechaUltimoAcc) {
        this.fechaUltimoAcc = fechaUltimoAcc;
    }

    public Boolean getConsentimiento() {
        return consentimiento;
    }

    public void setConsentimiento(Boolean consentimiento) {
        this.consentimiento = consentimiento;
    }

}