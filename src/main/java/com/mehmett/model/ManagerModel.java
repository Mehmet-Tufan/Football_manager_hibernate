package com.mehmett.model;

import com.mehmett.entity.Manager;

public class ManagerModel {
    private String name;
    private String age;
    private String username;
    private Integer experience; //between 1-10
    private String teamName;

    public ManagerModel(Manager manager) {
        this.name = manager.getName();
        this.age = manager.getAge().toString();
        this.username = manager.getUsername();
        this.experience = manager.getExperience();
        this.teamName = manager.getTeam().getTeamName();
    }

    public void displayManager(){
        System.out.println("------------------------------------------");
        System.out.println("Name        : " + name);
        System.out.println("age         : " + age);
        System.out.println("username    : " + username);
        System.out.println("experience  : " + experience.toString()+" Level");
        System.out.println("teamName    : " + teamName);
        System.out.println("------------------------------------------");
    }
}