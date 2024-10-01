package com.mehmett.model;

import com.mehmett.controller.ManagerController;
import com.mehmett.entity.Manager;
import com.mehmett.entity.Player;
import com.mehmett.entity.Team;
import com.mehmett.utility.enums.EPosition;

import java.text.NumberFormat;
import java.util.Locale;

public class PlayerModel {

    private String name;
    private Integer age;
    private EPosition position;
    private Integer skillLevel;
    private Long value;
    private Long salary;
    private Team team;
    private Manager manager;


    public PlayerModel(Player player) {
        this.name = player.getName();
        this.age = player.getAge();
        this.position = player.getPosition();
        this.skillLevel = player.getSkillLevel();
        this.salary = player.getSalary();
        this.value = player.getValue();
        this.team = player.getTeam();
        this.manager = ManagerController.getInstance().findByFieldAndValue("team",team).getFirst();
    }


    public void displayPlayerForOffer(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        System.out.println("---------------------------------------");
        System.out.println("--- PLAYER DETAILS ---");
        System.out.println("---------------------------------------");
        System.out.println("Name         : "+name);
        System.out.println("Age          : "+age);
        System.out.println("Position     : "+position);
        System.out.println("Skill Level  : "+skillLevel);
        System.out.println("Transfer Fee : "+currencyFormat.format(value));
        System.out.println("Salary       : "+currencyFormat.format(salary));
        System.out.println("Team         : "+team.getTeamName());
        System.out.println("Manager      : "+manager.getName());
        System.out.println("---------------------------------------");
    }

    public void displayPlayerForMyTeam(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        System.out.println("---------------------------------------");
        System.out.println("--- PLAYER DETAILS ---");
        System.out.println("---------------------------------------");
        System.out.println("Name         : "+name);
        System.out.println("Age          : "+age);
        System.out.println("Position     : "+position);
        System.out.println("Skill Level  : "+skillLevel);
        System.out.println("Salary       : "+currencyFormat.format(salary));
        System.out.println("---------------------------------------");
    }





}