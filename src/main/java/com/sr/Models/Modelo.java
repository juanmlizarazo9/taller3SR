package com.sr.Models;

import java.util.ArrayList;

public class Modelo {

    private ArrayList<Movie> movies;

    public Modelo(){
        Movie movie1 = new Movie("m1", "nombre1", "tags1", "actores1", "directores1", "generos1" );
        Movie movie2 = new Movie("m2", "nombre2", "tags2", "actores2", "directores2", "generos2" );
        Movie movie3 = new Movie("m3", "nombre3", "tags3", "actores3", "directores3", "generos3" );

        movies = new ArrayList<Movie>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
    }

    public ArrayList<Movie> darPeliculas(){
        return movies;
    }

    public void addPelicula(Movie movie){
        movies.add(movie);
    }



}
