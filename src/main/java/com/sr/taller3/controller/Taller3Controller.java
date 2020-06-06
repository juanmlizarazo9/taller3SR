package com.sr.taller3.controller;


import com.sr.taller3.model.Movie;
import com.sr.taller3.service.Neo4jRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Taller3Controller {

    @Autowired
    private Neo4jRecommendationService neo4j;

    @RequestMapping("/t3_peliculas_recomendacion")
    public ModelAndView t3RecomendacionPeliculas(@RequestParam Map<String, String> params){

        String user = params.get("user");
        Map<String, Object> model = new HashMap<>();

        List<Movie> moviesrec1 = neo4j.recommend(user);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:5000/predict?&userId=" + user.replace("u","");
        String info = restTemplate.getForObject(url, String.class);

        String[] items = info.replace("\"", "").replace("[[", "")
                .replace("]]", "").split("\\],\\[");

        List<Movie> moviesrec2 = new ArrayList<>();
        for(String item: items){
            String[] vars = item.split(",");
            Movie movie = new Movie();
            movie.setId(vars[0]);
            movie.setTitle(vars[1]);
            movie.setGenres(vars[2]);
            movie.setTags(vars[3]);
            movie.setDirectors(vars[4]);
            movie.setActors(vars[5]);
            moviesrec2.add(movie);
        }

        ArrayList<Movie> hybrid = new ArrayList<>();
        for(int i = 0; i < moviesrec2.size() && i < moviesrec1.size(); i++){
            hybrid.add(moviesrec2.get(i));
            hybrid.add(moviesrec1.get(i));
        }

        model.put("recommendations",hybrid);
        return new ModelAndView("taller3", model);
    }
}