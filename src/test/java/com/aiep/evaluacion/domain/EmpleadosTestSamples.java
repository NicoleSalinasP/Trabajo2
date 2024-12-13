package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpleadosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Empleados getEmpleadosSample1() {
        return new Empleados()
            .id(1L)
            .nombresEmpleado("nombresEmpleado1")
            .apellidoEmpleado("apellidoEmpleado1")
            .telefono("telefono1")
            .correo("correo1");
    }

    public static Empleados getEmpleadosSample2() {
        return new Empleados()
            .id(2L)
            .nombresEmpleado("nombresEmpleado2")
            .apellidoEmpleado("apellidoEmpleado2")
            .telefono("telefono2")
            .correo("correo2");
    }

    public static Empleados getEmpleadosRandomSampleGenerator() {
        return new Empleados()
            .id(longCount.incrementAndGet())
            .nombresEmpleado(UUID.randomUUID().toString())
            .apellidoEmpleado(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString());
    }
}
