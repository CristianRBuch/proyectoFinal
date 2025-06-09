package com.gestionestudiantes.estudiantes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "estudiantesing")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Estudiantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 255)
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "Debe contener exactamente 10 dígitos numéricos")
    private String telefono;

    @NotBlank(message = "El idioma es obligatorio")
    @Pattern(regexp = "inglés|español|francés", message = "Idioma inválido")
    private String idioma;
}

