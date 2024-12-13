package com.aiep.evaluacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Empleados.
 */
@Entity
@Table(name = "empleados")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombres_empleado")
    private String nombresEmpleado;

    @Column(name = "apellido_empleado")
    private String apellidoEmpleado;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "empleados", "jefe" }, allowSetters = true)
    private Deparmento deparmento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empleados id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombresEmpleado() {
        return this.nombresEmpleado;
    }

    public Empleados nombresEmpleado(String nombresEmpleado) {
        this.setNombresEmpleado(nombresEmpleado);
        return this;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidoEmpleado() {
        return this.apellidoEmpleado;
    }

    public Empleados apellidoEmpleado(String apellidoEmpleado) {
        this.setApellidoEmpleado(apellidoEmpleado);
        return this;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Empleados telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Empleados correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Deparmento getDeparmento() {
        return this.deparmento;
    }

    public void setDeparmento(Deparmento deparmento) {
        this.deparmento = deparmento;
    }

    public Empleados deparmento(Deparmento deparmento) {
        this.setDeparmento(deparmento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleados)) {
            return false;
        }
        return getId() != null && getId().equals(((Empleados) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empleados{" +
            "id=" + getId() +
            ", nombresEmpleado='" + getNombresEmpleado() + "'" +
            ", apellidoEmpleado='" + getApellidoEmpleado() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
