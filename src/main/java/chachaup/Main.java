package chachaup;

import chachaup.dao.HeroesDao;
import chachaup.dao.SquadsDao;
import chachaup.domain.Heroes;
import chachaup.domain.Squads;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Sql2o sql2o = new Sql2o("jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:DB/create.sql'", "", "");
        SquadsDao squadsDao = new SquadsDao(sql2o);
        HeroesDao heroesDao = new HeroesDao(sql2o);
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
            ArrayList<Heroes> heroes = heroesDao.getAll();
            String name = req.queryParams("name");
            String power = req.queryParams("superpower");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            Heroes hero = new Heroes(name, power, weakness, age);
            heroes.add(hero);
            model.put("heroes", heroes);
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //list of heroes
        get("/heroes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = heroesDao.getAll();
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
            ArrayList<Squads> squads = squadsDao.getAll();
            String squadName = req.queryParams("name");
            String cause = req.queryParams("cause");
            int max = Integer.parseInt(req.queryParams("max"));
            Squads squad = new Squads(max, squadName, cause);
            squads.add(squad);
            model.put("squads", squads);
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

        //list of squads
        get("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Squads> squads = squadsDao.getAll();
            model.put("squads", squads);
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
