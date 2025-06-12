package com.gestionestudiantes.estudiantes.dao;

import com.gestionestudiantes.estudiantes.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioDaoImpTest {

    @InjectMocks
    private UsuarioDaoImp usuarioDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsuarios() {
        Usuario u1 = new Usuario();
        u1.setNombre("Ana");

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(u1));

        List<Usuario> resultado = usuarioDao.getUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNombre());
    }

    @Test
    public void testEliminar() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(entityManager.find(Usuario.class, 1L)).thenReturn(usuario);

        usuarioDao.eliminar(1L);

        verify(entityManager).remove(usuario);
    }

    @Test
    public void testRegistrar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");

        usuarioDao.registrar(usuario);

        verify(entityManager).merge(usuario);
    }


    @Test
    public void testObtenerUsuarioPorCredenciales_Incorrectas() {
        Usuario loginUsuario = new Usuario();
        loginUsuario.setEmail("correo@test.com");
        loginUsuario.setPassword("malapass");

        Usuario dbUsuario = new Usuario();
        dbUsuario.setEmail("correo@test.com");

        Argon2 argon2 = Argon2Factory.create();
        dbUsuario.setPassword(argon2.hash(1, 1024, 1, "1234")); // contraseña correcta, pero usuario pone mal

        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("email"), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(dbUsuario));

        Usuario resultado = usuarioDao.obtenerUsuarioPorCredenciales(loginUsuario);

        assertNull(resultado);
    }
}
