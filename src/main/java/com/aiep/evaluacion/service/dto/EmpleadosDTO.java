package com.aiep.evaluacion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.aiep.evaluacion.domain.Empleados} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpleadosDTO implements Serializable {

    private Long id;

    private String nombresEmpleado;

    private String apellidoEmpleado;

    private String telefono;

    private String correo;

    private DeparmentoDTO deparmento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public DeparmentoDTO getDeparmento() {
        return deparmento;
    }

    public void setDeparmento(DeparmentoDTO deparmento) {
        this.deparmento = deparmento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpleadosDTO)) {
            return false;
        }

        EmpleadosDTO empleadosDTO = (EmpleadosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empleadosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpleadosDTO{" +
            "id=" + getId() +
            ", nombresEmpleado='" + getNombresEmpleado() + "'" +
            ", apellidoEmpleado='" + getApellidoEmpleado() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", deparmento=" + getDeparmento() +
            "}";
    }
}
