package com.mehmett.entity;

import com.mehmett.utility.enums.EBetOddsState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblbet_odds")
public class BetOdds extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal homeTeamWins;
    @Column(precision = 10, scale = 2)
    private BigDecimal awayTeamWins;
    @Column(precision = 10, scale = 2)
    private BigDecimal draw;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalGoalsEqual3OrMore;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalGoalsEqual2OrLess;

    @Enumerated(EnumType.STRING)
    private EBetOddsState eBetOddsState;
    



    @ManyToOne
    private Match match;

}