package chachaup.dao;

import chachaup.domain.Heroes;
import chachaup.interfaces.iHeroes;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class HeroesDao implements iHeroes {
    private final Sql2o sql2o;

    public HeroesDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void addHero(Heroes hero) {
        try(Connection con = sql2o.open()){
            String query = "INSERT INTO heroes (name,superpower,weakness,age) VALUES(:name,:superpower,:weakness,:age)";
            int id = (int) con.createQuery(query,true)
                    .bind(hero)
                    .executeUpdate()
                    .getKey();
            hero.setId(id);

        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }

    }

    @Override
    public Heroes findById(int id) {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM heroes WHERE id = :id";
            Heroes hero = con.createQuery(query)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Heroes.class);
            return hero;
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public ArrayList<Heroes> findBySquad(int squadId) {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM heroes WHERE squadid = :squadId";
            return (ArrayList<Heroes>) con.createQuery(query)
                    .addParameter("squadId",squadId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Heroes.class);
        } catch(Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public ArrayList<Heroes> getAll() {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM heroes";
            return (ArrayList<Heroes>) con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Heroes.class);
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public void deleteById(int id) {
        try(Connection con = sql2o.open()){
            String query = "DELETE FROM heroes WHERE id = :id";
            con.createQuery(query)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }

    }

    @Override
    public void deleteAll() {
        try(Connection con = sql2o.open()){
            String query = "DELETE FROM heroes";
            con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }
}
