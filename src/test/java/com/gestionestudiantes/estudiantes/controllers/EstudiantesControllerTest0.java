package com.gestionestudiantes.estudiantes.controllers;

import com.gestionestudiantes.estudiantes.dao.EstudiantesDao;
import com.gestionestudiantes.estudiantes.models.Estudiantes;
import com.gestionestudiantes.estudiantes.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class EstudiantesControllerTest {

    @Mock
    private EstudiantesDao estudiantesDao;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private EstudiantesController estudiantesController;

    @Test
    void testObtenerPorId_CuandoExiste() {
        // Arrange
        Estudiantes estudiante = new Estudiantes();
        estudiante.setId(1L);
        estudiante.setNombre("Pablo");
        estudiante.setApellido("Flores");
        estudiante.setEmail("pablof@gmail.com");
        estudiante.setTelefono("1234567890");
        estudiante.setIdioma("francés");

        when(estudiantesDao.obtenerPorId(1L)).thenReturn(estudiante);

        // Act
        ResponseEntity<Estudiantes> response = estudiantesController.obtenerPorId(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Pablo", response.getBody().getNombre());
        verify(estudiantesDao).obtenerPorId(1L);
    }

    @Test
    void testObtenerPorId_CuandoNoExiste() {
        // Arrange
        when(estudiantesDao.obtenerPorId(99L)).thenReturn(null);

        // Act
        ResponseEntity<Estudiantes> response = estudiantesController.obtenerPorId(99L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(estudiantesDao).obtenerPorId(99L);
    }
}
