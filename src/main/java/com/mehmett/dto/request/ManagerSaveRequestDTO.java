package com.mehmett.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ManagerSaveRequestDTO {
    private String fullname;
    private Integer age;
    private String username;
    private String password;
}