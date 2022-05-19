package chachaup.interfaces;

import chachaup.domain.Squads;

import java.util.ArrayList;
import java.util.List;

public interface iSquads {
    //create
    void addSquad(Squads squad);

    //read
    Squads findById(int id);
    ArrayList<Squads> findByCause(String cause);
    ArrayList<Squads> getAll();

    //delete
    void deleteById(int id);
    void deleteAll();

    void update(int id, Squads squad);  //update
}
