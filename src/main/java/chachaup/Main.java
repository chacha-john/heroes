package chachaup;

import chachaup.dao.HeroesDao;
import chachaup.dao.SquadsDao;
import chachaup.domain.Heroes;
import chachaup.domain.Squads;
import chachaup.utils.RouterUtil;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Session;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main extends RouterUtil {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/chachaup", "riko", "nzfu5321");
        SquadsDao squadsDao = new SquadsDao(sql2o);
        HeroesDao heroesDao = new HeroesDao(sql2o);
        staticFileLocation("/public");
        Connection con = sql2o.open();

        //set up session
        post("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Session session = req.session();
            String username = req.queryParams("username");
            session.attribute("user", username);
            model.put("user", session.attribute("user"));
            res.redirect("/");
            return null;
            }, new HandlebarsTemplateEngine());

        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "user.hbs");
            }, new HandlebarsTemplateEngine());

        //set up routes
        // home page
        get("/", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = new ArrayList<>();
            Heroes hero = heroesDao.getAll().get(0);
            Heroes hero1 = heroesDao.getAll().get(1);
            Heroes hero2 = heroesDao.getAll().get(2);
            heroes.add(hero);
            heroes.add(hero1);
            heroes.add(hero2);
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //heroes
        //get form to add new hero
        get("/heroes/add", (req, res) -> {
//            checkSession(req,res);
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "add-hero.hbs");
        }, new HandlebarsTemplateEngine());

        //post form to add new hero
        post("/hero/add", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = heroesDao.getAll();
            String name = req.queryParams("name");
            String power = req.queryParams("superpower");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            Heroes hero = new Heroes(name, power, weakness, age);
            heroesDao.addHero(hero);
            heroes.add(hero);
            model.put("heroes", heroes);
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //list of heroes
        get("/heroes", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            ArrayList<Heroes> heroes = heroesDao.getAll();
            model.put("heroes", heroes);
            return new ModelAndView(model, "heroes.hbs");
            }, new HandlebarsTemplateEngine());

        //squads
        //get form to add new squad
        get("/squads/add", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "add-squad.hbs");
            }, new HandlebarsTemplateEngine());

        //post form to add new squad
        post("squad/add", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            ArrayList<Squads> squads = squadsDao.getAll();
            String squadName = req.queryParams("name");
            String cause = req.queryParams("cause");
            int max = Integer.parseInt(req.queryParams("maxsize"));
            Squads squad = new Squads(max, squadName, cause);
            squadsDao.addSquad(squad);
            squads.add(squad);
            model.put("squads", squads);
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

        //list of squads
        get("/squads", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            ArrayList<Squads> squads = squadsDao.getAll();
            model.put("squads", squads);
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

        //delete squad
        get("/squads/:id/delete", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            squadsDao.deleteById(id);
            ArrayList<Squads> squads = squadsDao.getAll();
            model.put("squads", squads);
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

        //add hero to squad
        get("/squads/:id/add-hero", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            ArrayList<Heroes> heroes = heroesDao.getAll();
            model.put("heroes", heroes);
            ArrayList<Squads> squads = squadsDao.getAll();
            model.put("squads", squads);
            return new ModelAndView(model, "add-hero-to-squad.hbs");
        }, new HandlebarsTemplateEngine());

        post("/squads/:id/add-hero-to-squad", (req, res) -> {
//            checkSession(req, res);
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            Squads squad = squadsDao.findById(id);
            int currentCount = squad.getCount();
            if (currentCount >= squad.getMaxSize()) {
                model.put("error", "Squad is full");
                ArrayList<Squads> squads = squadsDao.getAll();
                model.put("squads", squads);
                return new ModelAndView(model, "squads.hbs");
            }
            int newCount = currentCount + 1;
            Squads squad1 = new Squads(squad.getMaxSize(), squad.getName(), squad.getCause());
            squad1.setCount(newCount);
            squadsDao.update(squad.getId(), squad1);
            ArrayList<Squads> squads = squadsDao.getAll();
            model.put("squads", squads);
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
