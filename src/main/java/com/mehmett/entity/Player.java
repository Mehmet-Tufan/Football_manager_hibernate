package com.mehmett.entity;

import com.mehmett.utility.enums.EPosition;
import com.mehmett.utility.enums.EPosition;
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
@Table(name = "tblplayer")
public class Player extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EPosition position;
    private Integer skillLevel; //1-100 arasi olacak.
    private Long value;
    private Long salary; //Sonradan eklendi.
    private Integer age;

    @ManyToOne
    private Team team;

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                "name= "+ getName() +
                ", position=" + position +
                ", skillLevel=" + skillLevel +
                ", value=" + value +
                ", salary=" + salary +
                ", age=" + age +
                ", team=" + team.getTeamName() +
                '}';
    }
}