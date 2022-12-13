package com.example.mysplash.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyInfo implements Serializable {
    private String usuario;
    private String password;
    private String correo;
    private String[] conocer;
    private Boolean sexo;
    private String date;
    private String cel;
    private String region;
    private Boolean activado;
    private String nombre;

    public List<MyData> getContras() {
        return contras;
    }

    public void setContras(List<MyData> contras) {
        this.contras = contras;
    }

    private List<MyData> contras= new ArrayList<>();



    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public MyInfo() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String[] getConocer() {
        return conocer;
    }

    public void setConocer(String[] conocer) {
        this.conocer = conocer;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }


}
