package com.aiep.evaluacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Jefe.
 */
@Entity
@Table(name = "jefe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Jefe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_jefe")
    private String nombreJefe;

    @Column(name = "telefono_jefe")
    private String telefonoJefe;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jefe")
    @JsonIgnoreProperties(value = { "empleados", "jefe" }, allowSetters = true)
    private Set<Deparmento> deparmentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jefe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreJefe() {
        return this.nombreJefe;
    }

    public Jefe nombreJefe(String nombreJefe) {
        this.setNombreJefe(nombreJefe);
        return this;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public String getTelefonoJefe() {
        return this.telefonoJefe;
    }

    public Jefe telefonoJefe(String telefonoJefe) {
        this.setTelefonoJefe(telefonoJefe);
        return this;
    }

    public void setTelefonoJefe(String telefonoJefe) {
        this.telefonoJefe = telefonoJefe;
    }

    public Set<Deparmento> getDeparmentos() {
        return this.deparmentos;
    }

    public void setDeparmentos(Set<Deparmento> deparmentos) {
        if (this.deparmentos != null) {
            this.deparmentos.forEach(i -> i.setJefe(null));
        }
        if (deparmentos != null) {
            deparmentos.forEach(i -> i.setJefe(this));
        }
        this.deparmentos = deparmentos;
    }

    public Jefe deparmentos(Set<Deparmento> deparmentos) {
        this.setDeparmentos(deparmentos);
        return this;
    }

    public Jefe addDeparmento(Deparmento deparmento) {
        this.deparmentos.add(deparmento);
        deparmento.setJefe(this);
        return this;
    }

    public Jefe removeDeparmento(Deparmento deparmento) {
        this.deparmentos.remove(deparmento);
        deparmento.setJefe(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jefe)) {
            return false;
        }
        return getId() != null && getId().equals(((Jefe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jefe{" +
            "id=" + getId() +
            ", nombreJefe='" + getNombreJefe() + "'" +
            ", telefonoJefe='" + getTelefonoJefe() + "'" +
            "}";
    }
}
