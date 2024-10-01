package com.mehmett.utility.Data;

import com.mehmett.entity.*;
import com.mehmett.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureGenerator {


    public static void createFixture(){
        FixtureRepository fixtureRepository = FixtureRepository.getInstance();
        LeagueRepository leagueRepository = LeagueRepository.getInstance();
        League league = leagueRepository.findById(1L).get();
        Fixture fixture = Fixture.builder().description("Fixture of: "+ league.getLeaugeName()).league(league).build();
        fixtureRepository.save(fixture);
        generateMatchesAndFixture(league);
    }

    //maçlar için 3 ayrı metod
    private static void generateMatchesAndFixture(League league){
        LeagueRepository leagueRepository = LeagueRepository.getInstance();
        int teamNums = leagueRepository.getTeamIds(league.getId()).size();

        int matchesPerWeek = teamNums/2;

        List<Integer[]> fixtureListWithID = generateFixtureList(teamNums);

        List<Match> matches = setIDToMatches(fixtureListWithID, league);//Mac nesnesi yarattigimiz yer.

        setDatestoMatches(league, matches);


    }

    private static List<Integer[]> generateFixtureList(int teamCount) {


        List<Integer[]> fixture = new ArrayList<>();

        // Her takımın birbirleriyle iki kez karşılaşacağı fikstürü oluşturuyoruz
        for (int i = 0; i < teamCount - 1; i++) {
            for (int j = 0; j < teamCount / 2; j++) {
                int home = (i + j) % (teamCount - 1);
                int away = (teamCount - 1 - j + i) % (teamCount - 1);
                if (j == 0) {
                    away = teamCount - 1;
                }
                fixture.add(new Integer[]{home, away});
            }
        }

        // İkinci yarı için ters maçları ekliyoruz
        List<Integer[]> reversedFixture = new ArrayList<>();
        for (Integer[] match : fixture) {
            reversedFixture.add(new Integer[]{match[1], match[0]});
        }
        fixture.addAll(reversedFixture);

        int halfOfMatches = fixture.size()/2;
        int totalMatchesInWeek = teamCount/2;

        //Aynı takım üst üste sürekli ev sahibi olmasın diye
        List<Integer[]> temp = List.copyOf(fixture);

        for(int i =0;i<teamCount-1;i++){
            for(int j=0;j<teamCount/2;j++ ){
                if(i%2==0){
                    fixture.set(j+(i*totalMatchesInWeek),temp.get(halfOfMatches+j+(i*totalMatchesInWeek)));
                    fixture.set(halfOfMatches+j+(i*totalMatchesInWeek), temp.get(j+(i*totalMatchesInWeek)));
                }
            }
        }
        return fixture;
    }
    private static List<Match> setIDToMatches(List<Integer[]> fixtureList, League league) {
        //maçlara id atar.
        List<Long> teamIDlist = LeagueRepository.getInstance().getTeamIds(league.getId());
        FixtureRepository fixtureRepository = FixtureRepository.getInstance();
        Fixture fixture = fixtureRepository.findById(1L).get();
        List<Match> matchesList = new ArrayList<>();

        for (Integer[] matches : fixtureList) {
            Match match = Match.builder().build();
            if (matches[0] < teamIDlist.size() && matches[1] < teamIDlist.size()) {
                match.setHomeTeamId(teamIDlist.get(matches[0]));
                match.setAwayTeamId(teamIDlist.get(matches[1]));
                match.setFixture(fixture);
                matchesList.add(match);
            }
        }
        return matchesList;
    }

    private static Map<Integer, List<Match>> setDatestoMatches(League league, List<Match> matches) {
        MatchRepository matchRepository = MatchRepository.getInstance();
        Season season = league.getSeason();
        LocalDate matchDate = season.getBEGINNING_OF_SEASON();
        int totalTeamsInLeague = LeagueRepository.getInstance().getTeamIds(league.getId()).size();


        int totalWeek = (totalTeamsInLeague - 1) * 2;
        int matchesPerWeek = totalTeamsInLeague / 2;
        int matchIndex = 0;


        for (int i = 0; i < totalWeek; i++) {
            for (int j = 0; j < matchesPerWeek; j++) {
                switch (j) {
                    case 0, 1:
                        matches.get(matchIndex++).setMatchDate(matchDate);
                        break;
                    case 2, 3, 4:
                        matches.get(matchIndex++).setMatchDate(matchDate.plusDays(1));
                        break;
                    case 5, 6, 7:
                        matches.get(matchIndex++).setMatchDate(matchDate.plusDays(2));
                        break;
                    case 8, 9:
                        matches.get(matchIndex++).setMatchDate(matchDate.plusDays(3));
                        break;
                }
            }
            matchDate = matchDate.plusDays(7);
        }
        MatchStatsRepository matchStatsRepository = MatchStatsRepository.getInstance();
        for (Match match : matches) {
            MatchStats matchStats = MatchStats.builder().match(match).build();
            matchStatsRepository.save(matchStats);
        }
        matchRepository.saveAll(matches);

        return new HashMap<>();
    }



}