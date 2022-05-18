package chachaup.dao;

import chachaup.domain.Squads;
import chachaup.interfaces.iSquads;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SquadsDao implements iSquads {
    private final Sql2o sql2o;

    public SquadsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void addSquad(Squads squad) {
        try(Connection con = sql2o.open()){
            String query = "INSERT INTO squads(maxSize,count,name,cause) VALUES(:maxSize,:count,:name,:cause)";
            int id = (int) con.createQuery(query,true)
                    .bind(squad)
                    .executeUpdate()
                    .getKey();
            squad.setId(id);
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }

    }

    @Override
    public void addHero(int heroId) {

    }

    @Override
    public Squads findById(int id) {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM squads WHERE id = :id";
            return con.createQuery(query)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Squads.class);
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public List<Squads> findByCause(String cause) {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM squads WHERE cause = :cause";
            return con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .addParameter("cause",cause)
                    .executeAndFetch(Squads.class);

        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public List<Squads> getAll() {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM squads";
            return con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Squads.class);
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public void addCount(int squadId) {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM squads WHERE id = :squadId";
            Squads squad = con.createQuery(query)
                    .addParameter("squadId",squadId)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Squads.class);
            int newCount = (squad.getCount())+1;
            int id = squad.getId();
            try{
                String setQuery = "UPDATE squads SET count = :newCount WHERE id = :id";
                con.createQuery(setQuery)
                        .addParameter("newCount",newCount)
                        .addParameter("id",id)
                        .executeUpdate();
            } catch (Sql2oException ex){
                throw new RuntimeException("Error encountered", ex);
            }
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered",ex);
        }

    }

    @Override
    public void deleteById(int id) {
        try(Connection con = sql2o.open()){
            String query = "DELETE FROM squads WHERE id = :id";
            con.createQuery(query)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered",ex);
        }

    }

    @Override
    public void deleteAll() {
        try(Connection con = sql2o.open()){
            String query = "DELETE FROm squads";
            con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }

    }
}
