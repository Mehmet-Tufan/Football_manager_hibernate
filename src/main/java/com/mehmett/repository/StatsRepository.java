package com.mehmett.repository;

import com.mehmett.entity.Stats;

public class StatsRepository extends RepositoryManager<Stats,Long> {
    private static StatsRepository instance;

    private StatsRepository() {
        super(Stats.class);
    }
    public static StatsRepository getInstance() {
        if(instance==null){
            instance = new StatsRepository();
        }
        return instance;
    }
}