package com.mehmett.utility.Data;

import com.mehmett.entity.*;
import com.mehmett.repository.*;
import com.mehmett.utility.engine.BetOddsEngine;
import com.mehmett.utility.enums.EDivision;
import com.mehmett.utility.enums.EPosition;
import com.mehmett.utility.enums.ERegion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DemoData {

    private static Long minimumSalaryIndex = 15_000L;
    private static Long maximumSalaryIndex = 25_000L;
    private static Long minimumValueIndex = 95_000L;
    private static Long maximumValueIndex = 105_000L;
    private static Long youngStarPlayerBonusValue = 10_000_000L;



    public static void GenerateDemonData(){
        createSeasons();
        createLeagues();
        createStadiums();
        createTeams();
        createPlayers();
        setTeamSquads();
        createManagers();
        FixtureGenerator.createFixture();
        createGambler();
        createBetOdds();

    }

    private static void createSeasons(){
        Season season = Season.builder().BEGINNING_OF_SEASON(LocalDate.of(2024,8,9))
                .title("2024-2025 TRENDYOL SUPER LEAGUE SEASON").currentDate(LocalDate.of(2024,8,8))
                .build();
        List<Season> seasons = new ArrayList<>();
        seasons.add(season);
        SeasonRepository.getInstance().saveAll(seasons);
    }




    private static void createLeagues(){
        List<League> leagues = new ArrayList<>();
        List<Season> seasons = SeasonRepository.getInstance().findByFieldNameAndValue("title", "2024-2025 TRENDYOL SUPER LEAGUE SEASON");
        if(!seasons.isEmpty()){
            League turkishSuperLeagu = League.builder().season(seasons.getFirst()).division(EDivision.SUPER_LIG_TR)
                    .region(ERegion.TURKIYE).leaugeName("Turkish Super League").build();
            leagues.add(turkishSuperLeagu);

        }
        LeagueRepository.getInstance().saveAll(leagues);
    }

    private static void createStadiums(){
        Stadium stadium1 = Stadium.builder().name("Ulker Stadyumu Fenerbahce Sukru Saracoglu Spor Kompleksi").location("Istanbul").capacity(50609).build();
        Stadium stadium2= Stadium.builder().name("Toki Arena").location("Istanbul").capacity(52500).build();
        Stadium stadium3 = Stadium.builder().name("Vodafone Park").location("Istanbul").capacity(41758).build();
        Stadium stadium4 = Stadium.builder().name("Senol Günes Spor Kompleksi").location("Trabzon").capacity(40661).build();
        Stadium stadium5 = Stadium.builder().name("Izmit IsmetPasa Stadyumu").location("Kocaeli").capacity(34829).build();
        Stadium stadium6 = Stadium.builder().name("Caykur Didi Stadyumu").location("Rize").capacity(15300).build();
        Stadium stadium7 = Stadium.builder().name("Samsun Stadyumu").location("Samsun").capacity(33000).build();
        Stadium stadium8 = Stadium.builder().name("Antalya Stadyumu").location("Antalya").capacity(33000).build();
        Stadium stadium9 = Stadium.builder().name("Goztepe Gursel Aksel Stadyumu").location("Izmir").capacity(20500).build();
        Stadium stadium10 = Stadium.builder().name("Adana Stadyumu").location("Adana").capacity(33000).build();
        Stadium stadium11 = Stadium.builder().name("Bahcesehir Okullari Stadyumu").location("Antalya").capacity(10500).build();
        Stadium stadium12 = Stadium.builder().name("Sivas 4 Eylül Stadyumu").location("Sivas").capacity(27182).build();
        Stadium stadium13 = Stadium.builder().name("Kasımpasa Stadyumu").location("Istanbul").capacity(14000).build();
        Stadium stadium14 = Stadium.builder().name("Konya Büyüksehir Stadyumu").location("Konya").capacity(42000).build();
        Stadium stadium15 = Stadium.builder().name("Kalyon Stadyumu").location("Gaziantep").capacity(33500).build();
        Stadium stadium16 = Stadium.builder().name("Hatay Stadyumu").location("Hatay").capacity(25000).build();
        Stadium stadium17 = Stadium.builder().name("Timsah Arena").location("Bursa").capacity(32325).build();
        Stadium stadium18 = Stadium.builder().name("Sakarya Ataturk Stadyumu").location("Sakarya").capacity(27569).build();
        Stadium stadium19 = Stadium.builder().name("Bodrum Belediyesi Bodrumspor Stadyumu").location("Mugla").capacity(5000).build();
        Stadium stadium20 = Stadium.builder().name("BAY").location("Adana").capacity(5000).build();

        List<Stadium> stadiumList = new ArrayList<>();
        stadiumList.addAll(List.of(stadium1,stadium2,stadium3,stadium4,stadium5,stadium6,stadium7,stadium8,stadium9,stadium10,stadium11,stadium12,stadium13,stadium14,stadium15,stadium16,stadium17,stadium18,stadium19,stadium20));
        StadiumRepository.getInstance().saveAll(stadiumList);
    }

    private static void createTeams(){
        List<Team> teams = new ArrayList<>();
        League league = LeagueRepository.getInstance().findById(1L).get();
        List<Stats> stats = new ArrayList<>();
        
        

        for(Long i =1L;i<=19L;i++){
            Stadium stadium = StadiumRepository.getInstance().findById(i).get();
           Team team = Team.builder().teamName("").budget(0L).stadium(stadium)
                    .league(league).players(new ArrayList<>()).build();
            teams.add(team);
            stats.add(Stats.builder().team(team).build());
        }
        teams.get(0).setTeamName("Fenerbahce");
        teams.get(0).setBudget(252_400_000L);
        teams.get(1).setTeamName("Galatasaray");
        teams.get(1).setBudget(210_500_000L);
        teams.get(2).setTeamName("Besiktas");
        teams.get(2).setBudget(142_530_000L);
        teams.get(3).setTeamName("Trabzonspor");
        teams.get(3).setBudget(98_630_000L);
        teams.get(4).setTeamName("Kocaelispor");
        teams.get(4).setBudget(45_000_000L);
        teams.get(5).setTeamName("Caykur Rizespor");
        teams.get(5).setBudget(32_750_000L);
        teams.get(6).setTeamName("Samsunspor");
        teams.get(6).setBudget(31_700_000L);
        teams.get(7).setTeamName("Antalyaspor");
        teams.get(7).setBudget(23_680_000L);
        teams.get(8).setTeamName("Goztepe");
        teams.get(8).setBudget(15_330_000L);
        teams.get(9).setTeamName("Adana Demirspor");
        teams.get(9).setBudget(23_400_000L);
        teams.get(10).setTeamName("Alanyaspor");
        teams.get(10).setBudget(22_460_000L);
        teams.get(11).setTeamName("Sivasspor");
        teams.get(11).setBudget(20_780_000L);
        teams.get(12).setTeamName("Kasimpasa");
        teams.get(12).setBudget(20_450_000L);
        teams.get(13).setTeamName("Konyaspor");
        teams.get(13).setBudget(20_360_000L);
        teams.get(14).setTeamName("Gaziantepspor");
        teams.get(14).setBudget(18_180_000L);
        teams.get(15).setTeamName("Hatayspor");
        teams.get(15).setBudget(17_980_000L);
        teams.get(16).setTeamName("Bursaspor");
        teams.get(16).setBudget(15_300_000L);
        teams.get(17).setTeamName("Sakaryaspor");
        teams.get(17).setBudget(12_750_000L);
        teams.get(18).setTeamName("Sipay Bodrum FK");
        teams.get(18).setBudget(20_710_000L);
        
        Stadium stadium = StadiumRepository.getInstance().findById(20L).get();
        Team bayTeam = Team.builder().teamName("BAY").league(league).stadium(stadium).build();
        teams.add(bayTeam);

        TeamRepository.getInstance().saveAll(teams);
        StatsRepository.getInstance().saveAll(stats);
    }

    private static final String[] FIRST_NAMES = {
            "Ahmet", "Mehmet", "Ali", "Mustafa", "Hasan", "Huseyin", "Ibrahim",
            "Kemal", "Yasin", "Cem", "Murat", "Osman", "Ferhat", "Burak", "Emre",
            "Omer", "Halil", "Serkan", "Tuncay", "Levent", "Ersin", "Onur", "Baris",
            "Volkan", "Kadir", "Erdem", "Furkan", "Can", "Ugur", "Salih", "Sinan",
            "Gökhan", "Hakan", "Yavuz", "Erhan", "Rıza", "Tayfun", "Metin", "Dogan",
            "Koray", "Yusuf", "Oguz", "Tolga", "Alper", "Mevlut", "Sahin", "Zafer"
    };

    private static final String[] LAST_NAMES = {
            "Yilmaz", "Kaya", "Demir", "Celik", "Sahin", "Ozturk", "Aydin", "Kilic",
            "Arslan", "Dogan", "Kara", "Koc", "Ozdemir", "Bal", "Simsek", "Ekinci",
            "Polat", "Cetin", "Keskin", "Yucel", "Avci", "Kurt", "Ates", "Bulut",
            "Gunes", "Bozkurt", "Ay", "Turkmen", "Cakir", "Karaca", "Kocak", "Erol",
            "Tan", "Deniz", "Gurbuz", "Soylu", "Unal", "Elmas", "Guler", "Aksoy",
            "Tuna", "Yildiz", "Gul", "Aslan", "Sener", "Ozkan", "Erdogan", "Sezer"
    };

    public static void createPlayers() {

        Random random = new Random();
        Long teamId = 1L;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 18; j++) {
                Player player = Player.builder().build();
                switch (j) {
                    case 0:
                        player.setPosition(EPosition.GOALKEEPER);
                        break;
                    case 1, 2, 3, 4:
                        player.setPosition(EPosition.DEFENCE);
                        break;
                    case 5, 6, 7, 8:
                        player.setPosition(EPosition.MIDFIELDER);
                        break;
                    case 9, 10:
                        player.setPosition(EPosition.FORWARD);
                    case 11: player.setPosition(EPosition.GOALKEEPER);
                    break;
                    case 12,13: player.setPosition(EPosition.DEFENCE);
                    break;
                    case 14,15 : player.setPosition(EPosition.MIDFIELDER);
                    break;
                    case 16,17 : player.setPosition(EPosition.FORWARD);
                    break;
                }

                player.setName(FIRST_NAMES[random.nextInt(0, FIRST_NAMES.length)] + " " +
                        LAST_NAMES[random.nextInt(0, LAST_NAMES.length)]);
                player.setSkillLevel(random.nextInt(35, 101));
                player.setAge((random.nextInt(17, 37)));

                if(player.getSkillLevel()<90){
                    player.setValue(player.getSkillLevel()* random.nextLong(minimumValueIndex,maximumValueIndex));
                }
                else{
                    player.setValue(player.getSkillLevel()* random.nextLong(minimumValueIndex,maximumValueIndex)* random.nextLong(2,4));
                    if(player.getAge()<24){
                        player.setValue(player.getValue()+youngStarPlayerBonusValue);
                    }
                }



                player.setSalary(player.getSkillLevel()* random.nextLong(minimumSalaryIndex,maximumSalaryIndex));
                Team team = TeamRepository.getInstance().findById(teamId).get();
                player.setTeam(team);
                PlayerRepository.getInstance().save(player);

            }
            teamId++;
        }
    }
    private static void setTeamSquads() {
        List<Player> players = PlayerRepository.getInstance().findAll();
        Team team;
        for (Player player : players) {
            team = player.getTeam();
            if (team != null){
                team.getPlayers().add(player);
            }
        }
    }

    private static void createManagers(){
        final String password ="1234";
        List<Team> teamList = TeamRepository.getInstance().findAll();
        Manager manager1 =Manager.builder().name("Jose Mourinho").age(61).username("josemourinho").password(password).team(teamList.get(0)).experience(10).build();
        Manager manager2 =Manager.builder().name("Okan Buruk").age(50).username("okanburuk").password(password).team(teamList.get(1)).experience(3).build();
        Manager manager3 =Manager.builder().name("Giovanni Van Brockhorst").age(49).username("giovanni").password(password).team(teamList.get(2)).experience(8).build();
        Manager manager4 =Manager.builder().name("Senol Gunes").age(61).username("senolgunes").password(password).team(teamList.get(3)).experience(4).build();
        Manager manager5 =Manager.builder().name("Ertugrul Saglam").age(54).username("ertugrulsaglam").password(password).team(teamList.get(4)).experience(7).build();
        Manager manager6 =Manager.builder().name("Ilhan Palut").age(47).username("ilhanpalut").password(password).team(teamList.get(5)).experience(7).build();
        Manager manager7 =Manager.builder().name("Thoman Reis").age(46).username("thomanreis").password(password).team(teamList.get(6)).experience(7).build();
        Manager manager8 =Manager.builder().name("Alex De Souza").age(46).username("alexdesouza").password(password).team(teamList.get(7)).experience(8).build();
        Manager manager9 =Manager.builder().name("Stanimir Stoilov").age(57).username("stanimirstoilov").password(password).team(teamList.get(8)).experience(6).build();
        Manager manager10 =Manager.builder().name("Michael Valkanis").age(49).username("michaelvalkanis").password(password).team(teamList.get(9)).experience(6).build();
        Manager manager11 =Manager.builder().name("Fatih Tekke").age(46).username("fatihtekke").password(password).team(teamList.get(10)).experience(7).build();
        Manager manager12 =Manager.builder().name("Bulent Uygun").age(53).username("bulentuygun").password(password).team(teamList.get(11)).experience(6).build();
        Manager manager13 =Manager.builder().name("Sami Ugurlu").age(47).username("samiugurlu").password(password).team(teamList.get(12)).experience(7).build();
        Manager manager14 =Manager.builder().name("Aleksandar Stanojevic").age(50).username("aleksandr").password(password).team(teamList.get(13)).experience(7).build();
        Manager manager15 =Manager.builder().name("Selcuk Inan").age(39).username("selcukinan").password(password).team(teamList.get(14)).experience(5).build();
        Manager manager16 =Manager.builder().name("Ozhan Pulat").age(39).username("ozhanpulat").password(password).team(teamList.get(15)).experience(6).build();
        Manager manager17 =Manager.builder().name("Pablo Batalla").age(40).username("pablobatalla").password(password).team(teamList.get(16)).experience(7).build();
        Manager manager18 =Manager.builder().name("Tuncay Sanli").age(42).username("tuncaysanli").password(password).team(teamList.get(17)).experience(7).build();
        Manager manager19 =Manager.builder().name("Fikret Öztürk").age(40).username("fiko").password(password).team(teamList.get(18)).experience(6).build();
        List<Manager> managerList = new ArrayList<>(List.of(manager1,manager2,manager3,manager4,manager5,manager6,manager7,manager8,manager9,manager10,
        manager11,manager12,manager13,manager14,manager15,manager16,manager17,manager18,manager19));
        ManagerRepository.getInstance().saveAll(managerList);
    }

    private static void createGambler(){
        Gambler gambler1 = Gambler.builder().name("Ezel Bayraktar").accountBalance(25_000.00).username("ezel")
                .password("1234").age(43).build();
        GamblerRepository.getInstance().save(gambler1);
    }

    private static void createBetOdds(){
        List<Match> matches = MatchRepository.getInstance().findAll();
        for(Match match : matches){
            if(match.getHomeTeamId()!=20L && match.getAwayTeamId()!=20L){ //Bay takım exception fırlatmasın diye yaptım
                BetOdds betOdds = BetOddsEngine.getMatchBetOdds(match);
                BetOddsRepository.getInstance().save(betOdds);
            }

        }
    }




}