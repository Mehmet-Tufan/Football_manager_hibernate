package com.mehmett.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblstats")
public class Stats extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int gamesPlayed;
    private int totalWins;
    private int totalDraws;
    private int totalLoses;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int points;

    @OneToOne
    private Team team;
}