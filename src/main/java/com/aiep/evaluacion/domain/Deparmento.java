package com.aiep.evaluacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Deparmento.
 */
@Entity
@Table(name = "deparmento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deparmento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_departamento")
    private String nombreDepartamento;

    @Column(name = "ubicacion_departamento")
    private String ubicacionDepartamento;

    @Column(name = "presupuesto_departamento")
    private String presupuestoDepartamento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deparmento")
    @JsonIgnoreProperties(value = { "deparmento" }, allowSetters = true)
    private Set<Empleados> empleados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deparmento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDepartamento() {
        return this.nombreDepartamento;
    }

    public Deparmento nombreDepartamento(String nombreDepartamento) {
        this.setNombreDepartamento(nombreDepartamento);
        return this;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getUbicacionDepartamento() {
        return this.ubicacionDepartamento;
    }

    public Deparmento ubicacionDepartamento(String ubicacionDepartamento) {
        this.setUbicacionDepartamento(ubicacionDepartamento);
        return this;
    }

    public void setUbicacionDepartamento(String ubicacionDepartamento) {
        this.ubicacionDepartamento = ubicacionDepartamento;
    }

    public String getPresupuestoDepartamento() {
        return this.presupuestoDepartamento;
    }

    public Deparmento presupuestoDepartamento(String presupuestoDepartamento) {
        this.setPresupuestoDepartamento(presupuestoDepartamento);
        return this;
    }

    public void setPresupuestoDepartamento(String presupuestoDepartamento) {
        this.presupuestoDepartamento = presupuestoDepartamento;
    }

    public Set<Empleados> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set<Empleados> empleados) {
        if (this.empleados != null) {
            this.empleados.forEach(i -> i.setDeparmento(null));
        }
        if (empleados != null) {
            empleados.forEach(i -> i.setDeparmento(this));
        }
        this.empleados = empleados;
    }

    public Deparmento empleados(Set<Empleados> empleados) {
        this.setEmpleados(empleados);
        return this;
    }

    public Deparmento addEmpleados(Empleados empleados) {
        this.empleados.add(empleados);
        empleados.setDeparmento(this);
        return this;
    }

    public Deparmento removeEmpleados(Empleados empleados) {
        this.empleados.remove(empleados);
        empleados.setDeparmento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deparmento)) {
            return false;
        }
        return getId() != null && getId().equals(((Deparmento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deparmento{" +
            "id=" + getId() +
            ", nombreDepartamento='" + getNombreDepartamento() + "'" +
            ", ubicacionDepartamento='" + getUbicacionDepartamento() + "'" +
            ", presupuestoDepartamento='" + getPresupuestoDepartamento() + "'" +
            "}";
    }
}
