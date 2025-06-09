package com.gestionestudiantes.estudiantes.dao;

import com.gestionestudiantes.estudiantes.models.Estudiantes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EstudiantesDaoImp implements EstudiantesDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Estudiantes> getEstudiantes() {
        String query = "FROM Estudiantes";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Estudiantes estudiantes = entityManager.find(Estudiantes.class, id);
        entityManager.remove(estudiantes);
    }

    @Override
    public void registrar(Estudiantes estudiantes) {
        entityManager.merge(estudiantes);
    }

    @Override
    public Estudiantes obtenerPorId(Long id) {
        return entityManager.find(Estudiantes.class, id);
    }

    @Override
    public void actualizar(Estudiantes estudiante) {
        Estudiantes existente = obtenerPorId(estudiante.getId());
        if (existente != null) {
            entityManager.merge(estudiante);
        }
    }
}