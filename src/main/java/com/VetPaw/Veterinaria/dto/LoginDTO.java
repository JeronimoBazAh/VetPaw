package com.VetPaw.Veterinaria.dto;

public class LoginDTO {

    public static class LoginRequest {
        private String documento;
        private String password;

        public LoginRequest() {}

        public String getDocumento() { return documento; }
        public void setDocumento(String documento) { this.documento = documento; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String usuario;

        public LoginResponse(String token, String usuario) {
            this.token = token;
            this.usuario = usuario;
        }

        public String getToken() { return token; }
        public String getUsuario() { return usuario; }
    }
}