package com.mehmett.repository;

import com.mehmett.entity.Bet;

public class BetRepository extends RepositoryManager<Bet,Long>{

    private static BetRepository instance;

    private BetRepository() {
        super(Bet.class);
    }

    public static BetRepository getInstance() {
        if (instance == null) {
            instance = new BetRepository();
        }
        return instance;
    }

}