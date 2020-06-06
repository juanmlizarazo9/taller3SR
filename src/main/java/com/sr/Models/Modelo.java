package com.sr.Models;

import org.apache.mahout.cf.taste.common.TasteException;

import java.io.IOException;
import java.util.ArrayList;


public class Modelo {

    private static Modelo instance = null;
    private ArrayList<Movie> movies;
    private ArrayList<Rating> ratings;
    private ArrayList<User> users;

    public Modelo(){
        User usuario1 = new User("uno");

        Movie movie1 = new Movie("m1", "nombre1", "tags1", "actores1", "directores1", "generos1" );
        Movie movie2 = new Movie("m2", "nombre2", "tags2", "actores2", "directores2", "generos2" );
        Movie movie3 = new Movie("m3", "nombre3", "tags3", "actores3", "directores3", "generos3" );


        Rating r1 = new Rating("u1", "m1", 3);
        Rating r2 = new Rating("u2", "m2", 4.5);
        Rating r3 = new Rating("u3", "m3", 2);

        User u1 = new User("u1");
        User u2 = new User("u2");
        User u3 = new User("u3");

        movies = new ArrayList<Movie>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);


        users = new ArrayList<User>();
        users.add(u1);
        users.add(u2);
        users.add(u3);

        ratings = new ArrayList<Rating>();
        ratings.add(r1);
        ratings.add(r2);
        ratings.add(r3);
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


    public void addPelicula(Movie movie){
        movies.add(movie);
    }


    public ArrayList<User> darUsuarios(){
        return users;
    }

    public ArrayList<Rating> darRatings(){
        return ratings;
    }

    public void addRating(Rating rating){
        ratings.add(rating);
    }

    public void addUser(User user){
        users.add(user);
    }



}
