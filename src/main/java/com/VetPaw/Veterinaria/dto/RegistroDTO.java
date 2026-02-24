package com.VetPaw.Veterinaria.dto;

import lombok.Data;

@Data
public class RegistroDTO {
    private String nombre;
    private String apellido;
    private String documento;
    private String celular;
    private String password;
}
