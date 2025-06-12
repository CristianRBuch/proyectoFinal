package com.gestionestudiantes.estudiantes.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testGettersAndSetters() {
        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNombre("Carlos");
        usuario.setApellido("Ramírez");
        usuario.setEmail("carlos@gmail.com");
        usuario.setTelefono("12345678");
        usuario.setPassword("clave123");

        assertEquals(1L, usuario.getId());
        assertEquals("Carlos", usuario.getNombre());
        assertEquals("Ramírez", usuario.getApellido());
        assertEquals("carlos@gmail.com", usuario.getEmail());
        assertEquals("12345678", usuario.getTelefono());
        assertEquals("clave123", usuario.getPassword());
    }

    @Test
    public void testToString() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");
        usuario.setApellido("López");

        String result = usuario.toString();
        assertTrue(result.contains("Ana"));
        assertTrue(result.contains("López"));
    }

    @Test
    public void testEqualsAndHashCode() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(10L);
        usuario1.setNombre("Luis");

        Usuario usuario2 = new Usuario();
        usuario2.setId(10L);
        usuario2.setNombre("Luis");

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}
