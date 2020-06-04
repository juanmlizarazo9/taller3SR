package com.sr.taller1.controller;

//import com.sr.taller1.data.DataRecommendationModels;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CreateRatingController {

    //private DataRecommendationModels models  = DataRecommendationModels.instance();

    public CreateRatingController() throws IOException {
    }

    @RequestMapping("/t1_add_user_rating")
    public ModelAndView t1UsuarioRating(@RequestParam Map<String, String> params) {
        Map<String, Object> model = new HashMap<>();

        model.put("tipoRecomendador","");
        model.put("user","");
        model.put("item","");
        model.put("rating","");

        return new ModelAndView("taller1UsuarioRating", model);
    }

}
