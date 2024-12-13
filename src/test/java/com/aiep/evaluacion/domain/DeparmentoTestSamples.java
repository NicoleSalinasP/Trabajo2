package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeparmentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Deparmento getDeparmentoSample1() {
        return new Deparmento()
            .id(1L)
            .nombreDepartamento("nombreDepartamento1")
            .ubicacionDepartamento("ubicacionDepartamento1")
            .presupuestoDepartamento("presupuestoDepartamento1");
    }

    public static Deparmento getDeparmentoSample2() {
        return new Deparmento()
            .id(2L)
            .nombreDepartamento("nombreDepartamento2")
            .ubicacionDepartamento("ubicacionDepartamento2")
            .presupuestoDepartamento("presupuestoDepartamento2");
    }

    public static Deparmento getDeparmentoRandomSampleGenerator() {
        return new Deparmento()
            .id(longCount.incrementAndGet())
            .nombreDepartamento(UUID.randomUUID().toString())
            .ubicacionDepartamento(UUID.randomUUID().toString())
            .presupuestoDepartamento(UUID.randomUUID().toString());
    }
}
