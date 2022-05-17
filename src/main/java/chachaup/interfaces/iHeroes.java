package chachaup.interfaces;

import chachaup.domain.Heroes;

import java.util.List;

public interface iHeroes {
    //create
    void addHero(Heroes hero);
    //read
    Heroes findById(int id);
    List<Heroes> findBySquad(int squadId);
    List<Heroes> getAll();
    //delete
    void deleteById(int id);
    void deleteAll();

}
