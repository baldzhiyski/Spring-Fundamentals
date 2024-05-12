package org.softuni.mobilele.domain.dtos.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.softuni.mobilele.domain.dtos.user_role.UserRoleIdDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;

    private String password;
    private String firstName;

    private String lastName;

    private boolean isActive;

    private UserRoleIdDto role;
    private String imageUrl;

    private String created;

    private String modified;
}
