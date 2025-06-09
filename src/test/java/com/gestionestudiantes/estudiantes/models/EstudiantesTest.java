package com.gestionestudiantes.estudiantes.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gestionestudiantes.estudiantes.models.Estudiantes;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EstudiantesTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
    }

    @Test
    public void testEstudiante() {
        Estudiantes estudiante = new Estudiantes();
        estudiante.setNombre("Laura");
        estudiante.setApellido("Ramírez");
        estudiante.setEmail("laura@example.com");
        estudiante.setTelefono("1234567890");
        estudiante.setIdioma("español");

        Set<ConstraintViolation<Estudiantes>> violations = validator.validate(estudiante);
        assertTrue(violations.isEmpty(), "No debería de haber errores de validación");
    }

    @Test
    public void testTelefono(){
        Estudiantes estudiante = new Estudiantes();
        estudiante.setNombre("Pedro");
        estudiante.setApellido("Pascal");
        estudiante.setEmail("pedrop@gmail.com");
        estudiante.setTelefono("852");
        estudiante.setIdioma("español");

        Set<ConstraintViolation<Estudiantes>> violations = validator.validate(estudiante);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v->v.getPropertyPath().toString().equals("telefono")));

    }

    @Test
    public void testIdioma() {
        Estudiantes estudiante = new Estudiantes();
        estudiante.setNombre("Sofía");
        estudiante.setApellido("Ruano");
        estudiante.setEmail("sofi@gmail.com");
        estudiante.setTelefono("7418529635");
        estudiante.setIdioma("Mandarín");

        Set<ConstraintViolation<Estudiantes>> violations = validator.validate(estudiante);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v->v.getPropertyPath().toString().equals("idioma")));
    }
}