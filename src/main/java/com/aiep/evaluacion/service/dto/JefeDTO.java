package com.aiep.evaluacion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.aiep.evaluacion.domain.Jefe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JefeDTO implements Serializable {

    private Long id;

    private String nombreJefe;

    private String telefonoJefe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreJefe() {
        return nombreJefe;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public String getTelefonoJefe() {
        return telefonoJefe;
    }

    public void setTelefonoJefe(String telefonoJefe) {
        this.telefonoJefe = telefonoJefe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JefeDTO)) {
            return false;
        }

        JefeDTO jefeDTO = (JefeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jefeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JefeDTO{" +
            "id=" + getId() +
            ", nombreJefe='" + getNombreJefe() + "'" +
            ", telefonoJefe='" + getTelefonoJefe() + "'" +
            "}";
    }
}
