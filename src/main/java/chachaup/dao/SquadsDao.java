package chachaup.dao;

import chachaup.domain.Squads;
import chachaup.interfaces.iSquads;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;

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
    public ArrayList<Squads> findByCause(String cause) {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM squads WHERE cause = :cause";
            return (ArrayList<Squads>) con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .addParameter("cause",cause)
                    .executeAndFetch(Squads.class);

        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }
    }

    @Override
    public ArrayList<Squads> getAll() {
        try(Connection con = sql2o.open()){
            String query = "SELECT * FROM squads";
            return (ArrayList<Squads>) con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Squads.class);
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
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

    @Override
    public void update(int id, Squads squad) {
        try(Connection con = sql2o.open()){
            String query = "UPDATE squads SET name = :name, cause = :cause, count = :count WHERE id = :id";
            con.createQuery(query)
                    .addParameter("name",squad.getName())
                    .addParameter("cause",squad.getCause())
                    .addParameter("count",squad.getCount())
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new RuntimeException("Error encountered", ex);
        }

    }
}
