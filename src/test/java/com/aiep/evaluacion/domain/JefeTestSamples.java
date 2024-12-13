package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JefeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Jefe getJefeSample1() {
        return new Jefe().id(1L).nombreJefe("nombreJefe1").telefonoJefe("telefonoJefe1");
    }

    public static Jefe getJefeSample2() {
        return new Jefe().id(2L).nombreJefe("nombreJefe2").telefonoJefe("telefonoJefe2");
    }

    public static Jefe getJefeRandomSampleGenerator() {
        return new Jefe()
            .id(longCount.incrementAndGet())
            .nombreJefe(UUID.randomUUID().toString())
            .telefonoJefe(UUID.randomUUID().toString());
    }
}
