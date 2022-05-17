package chachaup.interfaces;

import chachaup.domain.Squads;

import java.util.List;

public interface iSquads {
    //create
    void addSquad(Squads squad);
    void addHero(int heroId);
    //read
    Squads findById(int id);
    List<Squads> findByCause(String cause);
    List<Squads> getAll();
    //delete
    void deleteById(int id);
    void deleteAll();

}
