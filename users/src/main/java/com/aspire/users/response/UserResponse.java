package com.aspire.users.response;

import com.aspire.users.models.Role;
import com.aspire.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String displayName;
    private Role role;

    public static UserResponse from(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setDisplayName(user.getDisplayName());
        userResponse.setRole(user.getRole());

        return userResponse;
    }
}
