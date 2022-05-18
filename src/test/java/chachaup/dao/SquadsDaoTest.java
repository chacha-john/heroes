package chachaup.dao;

import chachaup.domain.Heroes;
import chachaup.domain.Squads;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class SquadsDaoTest {
    private SquadsDao squadsDao;
    private HeroesDao heroesDao;
    private Connection con;

    @BeforeEach
    void setUp() {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:DB/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        squadsDao = new SquadsDao(sql2o);
        heroesDao = new HeroesDao(sql2o);
        con = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        con.close();
    }

    @Test
    void addSquad() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        assertEquals(1, squad.getId());

    }
    @Test
    void addCount() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        int initial = squad.getCount();
        squadsDao.addCount(squad.getId());
        int current = squad.getCount();
        assertEquals(initial,current);
    }

    @Test
    void addHero() {
        Squads squad = setUpSquad();
        Heroes hero = setUpHero();
        squadsDao.addHero(hero.getId());
        squad.setCount(setUpSquad().getCount()+1);
        squadsDao.addSquad(squad);

    }

    @Test
    void findById() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        assertEquals("chachas",squadsDao.findById(squad.getId()).getName());
    }

    @Test
    void findByCause() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        assertEquals(1,squadsDao.findByCause("code").size());
    }

    @Test
    void getAll() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        assertEquals(1,squadsDao.getAll().size());
    }

    @Test
    void deleteById() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        assertEquals(1,squadsDao.getAll().size());
        squadsDao.deleteById(squad.getId());
        assertEquals(0,squadsDao.getAll().size());
    }

    @Test
    void deleteAll() {
        Squads squad = setUpSquad();
        squadsDao.addSquad(squad);
        assertEquals(1,squadsDao.getAll().size());
        squadsDao.deleteAll();
        assertEquals(0,squadsDao.getAll().size());
    }

    //helper methods
    public Squads setUpSquad(){
        Squads squad = new Squads(15, "chachas","code");
        return squad;
    }

    public Heroes setUpHero(){
        Heroes hero = new Heroes("chacha","solitude","fast foods",25);
        return hero;
    }
}