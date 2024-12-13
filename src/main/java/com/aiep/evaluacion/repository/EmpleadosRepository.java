package com.aiep.evaluacion.repository;

import com.aiep.evaluacion.domain.Empleados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Empleados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Long> {}
