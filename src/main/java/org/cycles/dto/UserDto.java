package org.cycles.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable{
    private Long userId;
    
    private String userName;

    private String userSurname;

    private String userNickname;

    private String userEmail;

    private String userPassword;

    private String userPhoneNumber;

    private String userRole;

    private int userActive; 
}
