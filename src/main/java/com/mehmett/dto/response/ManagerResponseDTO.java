package com.mehmett.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ManagerResponseDTO {
    private String fullname;
    private Integer age;
    private String username;
}