package com.mehmett.utility.engine;

import com.mehmett.entity.Match;
import com.mehmett.entity.MatchStats;
import com.mehmett.entity.Player;
import com.mehmett.entity.Team;
import com.mehmett.repository.MatchStatsRepository;
import com.mehmett.repository.PlayerRepository;
import com.mehmett.repository.TeamRepository;
import com.mehmett.utility.enums.EPosition;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MatchEngine {
   private static final TeamRepository teamRepository = TeamRepository.getInstance();
   private static final PlayerRepository playerRepository = PlayerRepository.getInstance();
   private static final MatchStatsRepository matchStatsRepository = MatchStatsRepository.getInstance();
   private static Random random = new Random();
   private static final double EVENT_POSSIBILITY = 0.02; // Moved magic number to a constant

    public static void simulateMatch(Match match) {
        MatchStats matchStats = calculateTeamPassDistribution(match);
        
        Team homeTeam = teamRepository.findById(match.getHomeTeamId()).get();
        Team awayTeam = teamRepository.findById(match.getAwayTeamId()).get();
        
        
        int totalPass = matchStats.getAwayTeam_Passes() + matchStats.getHomeTeam_Passes();
        int homeTeamBallPercantage = matchStats.getHomeTeamBallPercantage();
        
        
        Team attackingTeam;
        Team defenceTeam;
        for (int i = 0; i < totalPass; i++) {
            double olasilik = Math.random();
            if (olasilik <= EVENT_POSSIBILITY) {//pas yapıp pozisyona girmedikce pozisyona girme olasiligi %sel artsın.
                int rasgele = random.nextInt(101);
                if (rasgele < homeTeamBallPercantage) {
                    attackingTeam = homeTeam;
                    defenceTeam = awayTeam;
                } else {
                    attackingTeam = awayTeam;
                    defenceTeam = homeTeam;
                }
                if (makeFinalPass(attackingTeam)) {
                    if (takeShot(attackingTeam)) {
                        if(attackingTeam==homeTeam){
                            matchStats.setHomeTeam_Shots(matchStats.getHomeTeam_Shots()+1);
                        }
                        else{
                            matchStats.setAwayTeam_Shots(matchStats.getAwayTeam_Shots()+1);
                        }
                        if (!makeSave(defenceTeam)) {
                            System.out.println("GOOOOOOOLLLL " + attackingTeam.getTeamName() + " scores amazing goal!!!");
                            if (attackingTeam == homeTeam) {
                                match.setHomeTeamScore(match.getHomeTeamScore() + 1);
                                System.out.println("Score = " + match.getHomeTeamScore() + " - " + match.getAwayTeamScore());
                            } else {
                                match.setAwayTeamScore(match.getAwayTeamScore() + 1);
                                System.out.println("Score = " + match.getHomeTeamScore() + " - " + match.getAwayTeamScore());
                            }
                            
                        }else{
                            if(attackingTeam==homeTeam){
                                matchStats.setAwayTeam_Saves(matchStats.getAwayTeam_Saves()+1);
                            }
                            else{
                                matchStats.setHomeTeam_Saves(matchStats.getHomeTeam_Saves()+1);
                                
                            }
                        }
                    }
                }
            }
        }
        matchStatsRepository.update(matchStats);
    }
    
    private static Team getTeamById(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        return team.orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + teamId));
    }

    private static int calculateTeamPower(Team team) {
        return playerRepository.findAll().stream()
                .filter(p -> p.getTeam() == team)
                .map(Player::getSkillLevel)
                .reduce(0, Integer::sum) / 11;
    }

    private static MatchStats calculateTeamPassDistribution(Match match) {
        MatchStats matchStats = matchStatsRepository.findByFieldNameAndValue("match", match).getFirst();
        int totalPass = random.nextInt(700, 1000);

        Team homeTeam = getTeamById(match.getHomeTeamId());
        Team awayTeam = getTeamById(match.getAwayTeamId());

        int homeTeamPower = calculateTeamPower(homeTeam);
        int awayTeamPower = calculateTeamPower(awayTeam);

        int homeTeamPassPercentage = calculatePassPercentage(homeTeamPower, awayTeamPower);
        int awayTeamPassPercentage = 100 - homeTeamPassPercentage;

        int homeTeamPasses = (int) Math.round((homeTeamPassPercentage / 100.0) * totalPass);
        int awayTeamPasses = (int) Math.round((awayTeamPassPercentage / 100.0) * totalPass);

        matchStats.setHomeTeam_Passes(homeTeamPasses);
        matchStats.setAwayTeam_Passes(awayTeamPasses);
        matchStats.setHomeTeamBallPercantage(homeTeamPassPercentage);
        matchStats.setAwayTeamBallPercantage(awayTeamPassPercentage);
        matchStatsRepository.update(matchStats);

        return matchStats;
    }

    private static int calculatePassPercentage(int homeTeamPower, int awayTeamPower) {
        int basePercentage = (int) (50 + (homeTeamPower - awayTeamPower) * 0.5);
        return Math.max(30, Math.min(70, basePercentage));
    }

    private static boolean makeFinalPass(Team team) {
        Player player = selectRandomPlayer(team, EPosition.MIDFIELDER, EPosition.FORWARD);
        boolean success = random.nextInt(90) < player.getSkillLevel();

        if (success) {
            System.out.println(player.getName() + " makes a successful pass.");
        } else {
            System.out.println(player.getName() + " loses the ball.");
        }
        return success;
    }

    private static boolean takeShot(Team team) {
        Player player = selectRandomPlayer(team, EPosition.MIDFIELDER, EPosition.FORWARD);
        boolean success = random.nextInt(100) < player.getSkillLevel();

        if (success) {
            System.out.println(player.getName() + " takes a shot on target!");
        } else {
            System.out.println(player.getName() + " misses the shot.");
        }
        return success;
    }

    private static boolean makeSave(Team team) {
        Player goalKeeper = selectRandomPlayer(team, EPosition.GOALKEEPER);
        boolean success = random.nextInt(100) < goalKeeper.getSkillLevel();

        if (success) {
            System.out.println("Great save by " + team.getTeamName() + "'s goalkeeper!");
        }

        return success;
    }

    private static Player selectRandomPlayer(Team team, EPosition... positions) {
        List<Player> players = playerRepository.findAll().stream()
                .filter(p -> p.getTeam().equals(team))
                .filter(p -> List.of(positions).contains(p.getPosition()))
                .toList();
        return players.get(random.nextInt(players.size()));
    }
    
    
}