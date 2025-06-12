package com.gestionestudiantes.estudiantes.controllers;

import com.gestionestudiantes.estudiantes.dao.UsuarioDao;
import com.gestionestudiantes.estudiantes.models.Usuario;
import com.gestionestudiantes.estudiantes.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UsuarioDao usuarioDao;

    @Mock
    private JWTUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@test.com");
        usuario.setPassword("1234");

        Usuario usuarioEnBD = new Usuario();
        usuarioEnBD.setId(1L);
        usuarioEnBD.setEmail("usuario@test.com");

        when(usuarioDao.obtenerUsuarioPorCredenciales(usuario)).thenReturn(usuarioEnBD);
        when(jwtUtil.create("1", "usuario@test.com")).thenReturn("fake-jwt-token");

        String resultado = authController.login(usuario);

        assertEquals("fake-jwt-token", resultado);
        verify(usuarioDao).obtenerUsuarioPorCredenciales(usuario);
        verify(jwtUtil).create("1", "usuario@test.com");
    }

    @Test
    public void testLogin_Fail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@test.com");
        usuario.setPassword("incorrecta");

        when(usuarioDao.obtenerUsuarioPorCredenciales(usuario)).thenReturn(null);

        String resultado = authController.login(usuario);

        assertEquals("FAIL", resultado);
        verify(usuarioDao).obtenerUsuarioPorCredenciales(usuario);
        verify(jwtUtil, never()).create(any(), any());
    }
}
