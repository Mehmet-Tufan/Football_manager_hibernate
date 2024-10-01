package com.mehmett.entity;

import com.mehmett.utility.enums.EDivision;
import com.mehmett.utility.enums.ERegion;
import com.mehmett.utility.enums.EDivision;
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
@Table(name = "tblleague")
public class League extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leaugeName;

    @Enumerated(EnumType.STRING)
    private ERegion region;
    @Enumerated(EnumType.STRING)
    private EDivision division;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Season season;
    
    @OneToOne(mappedBy = "league")
    private Fixture fixture;


}