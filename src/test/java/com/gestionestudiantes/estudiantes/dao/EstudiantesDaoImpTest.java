package com.gestionestudiantes.estudiantes.dao;

import com.gestionestudiantes.estudiantes.models.Estudiantes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EstudiantesDaoImpTest {

    @InjectMocks
    private EstudiantesDaoImp estudiantesDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Estudiantes> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEstudiantes() {
        Estudiantes e1 = new Estudiantes();
        e1.setNombre("Anthony");
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(e1));

        List<Estudiantes> resultado = estudiantesDao.getEstudiantes();

        assertEquals(1, resultado.size());
        assertEquals("Anthony", resultado.get(0).getNombre());
        verify(entityManager).createQuery("FROM Estudiantes");
    }

    @Test
    public void testEliminar(){
        Estudiantes estudiante = new Estudiantes();
        estudiante.setId(1L);

        when(entityManager.find(Estudiantes.class, 1L)).thenReturn(estudiante);

        estudiantesDao.eliminar(1L);

        verify(entityManager).remove(estudiante);
    }

    @Test
    public void testRegistrar(){
        Estudiantes estudiante = new Estudiantes();
        estudiante.setNombre("Vinicio");

        estudiantesDao.registrar(estudiante);

        verify(entityManager).merge(estudiante);
    }

    @Test
    public void testObtenerPorId() {
        Estudiantes estudiante = new Estudiantes();
        estudiante.setId(5L);

        when(entityManager.find(Estudiantes.class, 5L)).thenReturn(estudiante);

        Estudiantes resultado = estudiantesDao.obtenerPorId(5L);

        assertEquals(5L, resultado.getId());
    }
}