package com.sr.Models;

import org.apache.mahout.cf.taste.common.TasteException;

import java.io.IOException;
import java.util.ArrayList;

public class Modelo {

    private ArrayList<Movie> movies;
    private ArrayList<User> usuarios;
    private static Modelo instance = null;



    public Modelo(){
        User usuario1 = new User("uno");

        Movie movie1 = new Movie("m1", "nombre1", "tags1", "actores1", "directores1", "generos1" );
        Movie movie2 = new Movie("m2", "nombre2", "tags2", "actores2", "directores2", "generos2" );
        Movie movie3 = new Movie("m3", "nombre3", "tags3", "actores3", "directores3", "generos3" );

        usuarios = new ArrayList<User>();
        usuarios.add(usuario1);

        movies = new ArrayList<Movie>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

    }

    public static Modelo instance() throws IOException, TasteException {
        if(instance == null) {
            instance = new Modelo();
        }
        return instance;
    }


    public ArrayList<Movie> darPeliculas(){
        return movies;
    }

    public ArrayList<User> darUsuarios(){
        return usuarios;
    }


    public void addPelicula(Movie movie){
        movies.add(movie);
    }

    public void addUsuario(User usuario){
        usuarios.add(usuario);
    }



}
