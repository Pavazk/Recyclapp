package com.example.isana.login;

public class RespuestaLogin {
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public RespuestaLogin(String codigo, String estado, String rol) {
        this.codigo = codigo;
        this.estado = estado;
        this.rol = rol;
    }
    private String codigo;
    private String estado;
    private String rol;

}
