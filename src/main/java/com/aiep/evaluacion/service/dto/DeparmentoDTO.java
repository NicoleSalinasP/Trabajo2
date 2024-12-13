package com.aiep.evaluacion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.aiep.evaluacion.domain.Deparmento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeparmentoDTO implements Serializable {

    private Long id;

    private String nombreDepartamento;

    private String ubicacionDepartamento;

    private String presupuestoDepartamento;

    private JefeDTO jefe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getUbicacionDepartamento() {
        return ubicacionDepartamento;
    }

    public void setUbicacionDepartamento(String ubicacionDepartamento) {
        this.ubicacionDepartamento = ubicacionDepartamento;
    }

    public String getPresupuestoDepartamento() {
        return presupuestoDepartamento;
    }

    public void setPresupuestoDepartamento(String presupuestoDepartamento) {
        this.presupuestoDepartamento = presupuestoDepartamento;
    }

    public JefeDTO getJefe() {
        return jefe;
    }

    public void setJefe(JefeDTO jefe) {
        this.jefe = jefe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeparmentoDTO)) {
            return false;
        }

        DeparmentoDTO deparmentoDTO = (DeparmentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deparmentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeparmentoDTO{" +
            "id=" + getId() +
            ", nombreDepartamento='" + getNombreDepartamento() + "'" +
            ", ubicacionDepartamento='" + getUbicacionDepartamento() + "'" +
            ", presupuestoDepartamento='" + getPresupuestoDepartamento() + "'" +
            ", jefe=" + getJefe() +
            "}";
    }
}
