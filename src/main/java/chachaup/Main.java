package chachaup;

import chachaup.domain.Heroes;
import chachaup.domain.Squads;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        staticFileLocation("/public");
        // home page
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = new ArrayList<>();
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //heroes
        //get form to add new hero
        get("/heroes/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "add-hero.hbs");
        }, new HandlebarsTemplateEngine());

        //post form to add new hero
        post("/hero/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = new ArrayList<>();
            String name = req.queryParams("name");
            String power = req.queryParams("power");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            Heroes hero = new Heroes(name, power, weakness, age);
            heroes.add(hero);
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //list of heroes
        get("/heroes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = new ArrayList<>();
//            heroes.add(new Heroes("Spiderman", "Spider Sense", "Web", 20));
//            heroes.add(new Heroes("Batman", "Money", "Dark Knight", 30));
//            heroes.add(new Heroes("Superman", "Super Strength", "Kryptonite", 40));
            model.put("heroes", heroes);
            return new ModelAndView(model, "heroes.hbs");
                }, new HandlebarsTemplateEngine());

        //squads
        //get form to add new squad
        get("/squads/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "add-squad.hbs");
            }, new HandlebarsTemplateEngine());

        //post form to add new squad
        post("squad/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Squads> squads = new ArrayList<>();
            String squadName = req.queryParams("name");
            String cause = req.queryParams("cause");
            int max = Integer.parseInt(req.queryParams("max"));
            Squads squad = new Squads(max, squadName, cause);
            squads.add(squad);
            model.put("squads", squads);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());



    }
}
