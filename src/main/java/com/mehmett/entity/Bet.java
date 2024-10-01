package com.mehmett.entity;

import com.mehmett.utility.enums.EBetState;
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
@Table(name = "tblbet")
public class Bet extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalBetOdd;
    @Column(precision = 10, scale = 2)
    private BigDecimal betAmount;

    @Enumerated(EnumType.STRING)
    private EBetState betState = EBetState.ON_WAIT;


//    @OneToMany //TODO:Her maç sadece bir defa eklenebilsin diye List yerine Set tanımladım, sorun çıkarabilir kontrol et.
//    private Set<BetOdds> betOdds;


//    //TODO:Enum türünü ElementCollection ile grup olarak tutmaya çalıştım buna bir daha bak.
//    @ElementCollection(targetClass = EOddSelection.class)
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "bet_odd_selections", joinColumns = @JoinColumn(name = "bet_id"))
//    @Column(name = "odd_selection")
//    private List<EOddSelection> oddSelections = new ArrayList<>();

}