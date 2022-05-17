package chachaup.dao;

import chachaup.domain.Heroes;
import chachaup.interfaces.iHeroes;

import java.util.List;

public class HeroesDao implements iHeroes {
    @Override
    public void addHero(Heroes hero) {

    }

    @Override
    public Heroes findById(int id) {
        return null;
    }

    @Override
    public List<Heroes> findBySquad(int squadId) {
        return null;
    }

    @Override
    public List<Heroes> getAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
