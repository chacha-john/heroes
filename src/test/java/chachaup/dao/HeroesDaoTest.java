package chachaup.dao;


import chachaup.domain.Heroes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeroesDaoTest {
    private HeroesDao heroesDao;
    private Connection con;

    @BeforeEach
    void setUp() {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:DB/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        heroesDao = new HeroesDao(sql2o);
        con = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        con.close();
    }

    @Test
    void addHero() {
        Heroes hero = setUpHero();
        heroesDao.addHero(hero);
        assertEquals(1,hero.getId());
    }

    @Test
    void findById() {
        Heroes hero = setUpHero();
        Heroes hero1 = new Heroes("stella","solitude","fast foods",25);
        heroesDao.addHero(hero1);
        assertEquals("stella",heroesDao.findById(hero1.getId()).getName());
    }

    @Test
    void findBySquad() {
        Heroes hero = setUpHero();
        Heroes hero1 = setUpHero();
        hero.setSquadId(21);
        heroesDao.addHero(hero);
        assertEquals(1, heroesDao.findBySquad(21).size());
    }

    @Test
    void getAll() {
        Heroes hero = setUpHero();
        Heroes hero1 = setUpHero();
        Heroes hero2 = setUpHero();
        heroesDao.addHero(hero);heroesDao.addHero(hero2);heroesDao.addHero(hero1);
        assertEquals(3,heroesDao.getAll().size());
    }

    @Test
    void deleteById() {
        Heroes hero = setUpHero();
        Heroes hero1 = setUpHero();
        heroesDao.addHero(hero);heroesDao.addHero(hero1);
        assertEquals(2,heroesDao.getAll().size());
        heroesDao.deleteById(hero.getId());
        assertEquals(1,heroesDao.getAll().size());
    }

    @Test
    void deleteAll() {
        Heroes hero = setUpHero();
        Heroes hero1 = setUpHero();
        Heroes hero2 = setUpHero();
        heroesDao.addHero(hero);heroesDao.addHero(hero2);heroesDao.addHero(hero1);
        assertEquals(3,heroesDao.getAll().size());
        heroesDao.deleteAll();
        assertEquals(0,heroesDao.getAll().size());
    }

    //helper methods
    public Heroes setUpHero(){
        Heroes hero = new Heroes("chacha","solitude","fast foods",25);
        return hero;
    }

}