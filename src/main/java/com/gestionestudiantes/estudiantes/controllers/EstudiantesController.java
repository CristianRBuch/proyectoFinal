package com.gestionestudiantes.estudiantes.controllers;

import com.gestionestudiantes.estudiantes.dao.EstudiantesDao;
import com.gestionestudiantes.estudiantes.models.Estudiantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/estudiantes")
public class EstudiantesController {

    @Autowired
    private EstudiantesDao estudiantesDao;

    @GetMapping
    public List<Estudiantes> getEstudiantes() {
        return estudiantesDao.getEstudiantes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiantes> obtenerPorId(@PathVariable Long id) {
        Estudiantes estudiante = estudiantesDao.obtenerPorId(id);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping
    public ResponseEntity<?> crearEstudiante(@Valid @RequestBody Estudiantes estudiante, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        if (emailYaExiste(estudiante.getEmail())) {
            return ResponseEntity.badRequest().body("El correo ya está en uso");
        }

        estudiantesDao.registrar(estudiante);
        return ResponseEntity.ok(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstudiante(@PathVariable Long id, @Valid @RequestBody Estudiantes estudiante) {
        estudiante.setId(id);
        estudiantesDao.actualizar(estudiante);
        return ResponseEntity.ok(estudiante);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Estudiantes estudiante = estudiantesDao.obtenerPorId(id);
        if (estudiante == null) return ResponseEntity.notFound().build();

        campos.forEach((clave, valor) -> {
            Field campo = ReflectionUtils.findField(Estudiantes.class, clave);
            if (campo != null) {
                campo.setAccessible(true);
                ReflectionUtils.setField(campo, estudiante, valor);
            }
        });

        estudiantesDao.actualizar(estudiante);
        return ResponseEntity.ok(estudiante);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estudiantesDao.eliminar(id);
    }

    private boolean emailYaExiste(String email) {
        return estudiantesDao.getEstudiantes().stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
    }
}