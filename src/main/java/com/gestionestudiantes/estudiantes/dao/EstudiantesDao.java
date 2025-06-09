package com.gestionestudiantes.estudiantes.dao;

import com.gestionestudiantes.estudiantes.models.Estudiantes;

import java.util.List;

public interface EstudiantesDao {
    List<Estudiantes> getEstudiantes();

    void eliminar(Long id);

    void registrar(Estudiantes estudiantes);

    Estudiantes obtenerPorId(Long id);

    void actualizar(Estudiantes estudiante);


}

