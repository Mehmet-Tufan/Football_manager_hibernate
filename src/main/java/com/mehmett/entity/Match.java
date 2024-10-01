package com.mehmett.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblmatch")
public class Match extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    static Integer matchIdCounter = 0;

    private Long homeTeamId;
    private Long awayTeamId;

    private int homeTeamScore;
    private int awayTeamScore;

    private LocalDate matchDate;
    
    private boolean isPlayed;
    
    @ManyToOne
    private Fixture fixture;
}