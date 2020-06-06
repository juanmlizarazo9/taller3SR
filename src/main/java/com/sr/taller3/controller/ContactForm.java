package com.sr.taller3.controller;


import com.sr.taller3.model.Rating;
import com.sr.taller3.service.Neo4jRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
public class ContactForm {

    @Autowired
    private Neo4jRecommendationService neo4j;

    @RequestMapping("/show_rating_list")
    public ModelAndView darTrackRatings(@RequestParam Map<String, String> params) {

        List<Rating> ratings = neo4j.getRatings(params.get("user"));
        Map<String, Object> model = new HashMap<>();
        model.put("ratings",ratings);

        return new ModelAndView("taller3UsuarioRating", model);
    }


    @RequestMapping("/addUserRating")
    public ModelAndView t1AgregarUsuarioRating(@RequestParam Map<String, String> params) throws IOException {
        String user = params.get("user");
        String movie = params.get("movie");
        double rating = Double.parseDouble(params.get("Rating"));

        Map<String, Object> model = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:5000/add_rating?&userId=" + user.replace("u","")+"&movieId="+movie.replace("m","")+"&rating="+rating;
        try {
            restTemplate.getForObject(url, String.class);
        }
        catch(Exception e) {
            model.put("errorMessage","Error al adicionar ratings al modelo item-item CF cosine");
            return new ModelAndView("taller1UsuarioRating", model);
        }

        if(!neo4j.existsUser(user)) {
            if (!neo4j.addUser(user)) {
                model.put("errorMessage", "Error al adicionar usuario");
                return new ModelAndView("taller1UsuarioRating", model);
            }
        }

        if(!neo4j.addRating(user,movie,rating)) {
            model.put("errorMessage", "Error al adicionar rating");
            return new ModelAndView("taller1UsuarioRating", model);
        }

        return new ModelAndView("taller3UsuarioRating", model);
    }
}
