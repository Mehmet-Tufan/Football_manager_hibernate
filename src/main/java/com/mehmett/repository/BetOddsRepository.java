package com.mehmett.repository;

import com.mehmett.entity.BetOdds;
import com.mehmett.utility.HibernateConnection;
import com.mehmett.utility.enums.EBetOddsState;
import com.mehmett.utility.enums.EState;

import java.time.LocalDate;

public class BetOddsRepository extends RepositoryManager<BetOdds,Long>{
    private static BetOddsRepository instance;

    private BetOddsRepository() {
        super(BetOdds.class);
    }
    public static BetOddsRepository getInstance() {
        if (instance == null) {
            return new BetOddsRepository();
        }
        return instance;
    }

    @Override //EBetOddsState default ON_WAIT atamak için override edildi.
    public BetOdds save(BetOdds betOdds) {
        betOdds.setEBetOddsState(EBetOddsState.ON_WAIT);
        betOdds.setState(EState.ACTIVE);
        betOdds.setCreateAt(LocalDate.now());
        betOdds.setUpdateAt(LocalDate.now());
        HibernateConnection.em.persist(betOdds);
        return betOdds;
    }
}