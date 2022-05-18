package chachaup.interfaces;

import chachaup.domain.Heroes;

import java.util.ArrayList;
import java.util.List;

public interface iHeroes {
    //create
    void addHero(Heroes hero);
    //read
    Heroes findById(int id);
    ArrayList<Heroes> findBySquad(int squadId);
    ArrayList<Heroes> getAll();
    //delete
    void deleteById(int id);
    void deleteAll();

}
