package com.mehmett.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblteam")
public class Team  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private Long budget;

    @OneToOne
    @JoinColumn(nullable = false)
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(nullable = false)
    private League league;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();


}