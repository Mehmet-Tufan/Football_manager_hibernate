package com.mehmett.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblmatchstats")
public class MatchStats extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int homeTeam_Passes;
    private int awayTeam_Passes;

    private int homeTeam_Shots;
    private int homeTeam_Saves;
    private int awayTeam_Shots;
    private int awayTeam_Saves;

    private int homeTeam_fouls;
    private int awayTeam_fouls;

    private int homeTeamBallPercantage;
    private int awayTeamBallPercantage;

    @OneToOne
    private Match match;


}