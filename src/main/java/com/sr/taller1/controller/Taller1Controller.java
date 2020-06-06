package com.sr.taller1.controller;


import com.sr.Models.Modelo;
import com.sr.Models.Movie;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Taller1Controller {


    public Taller1Controller() throws IOException, TasteException {
    }

    private Modelo modelo1 = Modelo.instance();


    @RequestMapping("/t3_peliculas_recomendacion")
    public ModelAndView t3RecomendacionPeliculas(@RequestParam Map<String, String> params) throws TasteException {

        String user = params.get("user");

        Map<String, Object> model = new HashMap<>();

        model.put("user",user);

        try {
            if(Modelo.instance().darUsuarios() == null)
            {
                model.put("errorMessage","Usuario no existe");
                return new ModelAndView("taller3", model);
            }
            ArrayList<Movie> recomendation = Modelo.instance().darPeliculas();

            model.put("recommendations",recomendation);
            return new ModelAndView("taller3", model);
        }

        catch(org.apache.mahout.cf.taste.common.NoSuchUserException ex){
            model.put("errorMessage","Usuario no existe");
            return new ModelAndView("taller3", model);
        } catch (IOException e) {
            model.put("errorMessage","IO Error");
            return new ModelAndView("taller3", model);
        }


    }



}