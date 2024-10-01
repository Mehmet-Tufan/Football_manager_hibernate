package com.mehmett.entity;

import com.mehmett.utility.enums.EState;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Builder.Default
    private EState state = EState.ACTIVE;
    private LocalDate createAt = LocalDate.now();
    private LocalDate updateAt = LocalDate.now();


}