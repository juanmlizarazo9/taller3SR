package com.sr.Models;

public class Movie {

    private String id;
    private String nombre;
    private String tags;
    private String actores;
    private String directores;
    private String generos;

    public Movie(String id, String nombre, String tags, String actores, String directores, String generos){
        this.id=id;
        this.nombre=nombre;
        this.tags=tags;
        this.actores=actores;
        this.directores=directores;
        this.generos=generos;
    }

    public String getId()
    {
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getTags(){
        return tags;
    }

    public String getActores(){
        return actores;
    }

    public String getDirectores(){
        return directores;
    }

    public String getGeneros(){
        return generos;
    }
}
