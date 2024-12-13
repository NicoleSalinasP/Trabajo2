package com.aiep.evaluacion.repository;

import com.aiep.evaluacion.domain.Deparmento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deparmento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeparmentoRepository extends JpaRepository<Deparmento, Long> {}
