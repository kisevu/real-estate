package com.ameda.api.user;

import java.util.List;

/**
 * Author: kev.Ameda
 */
public interface UserService {
    User createUser(UserDTO dto);
    List<User> getUsers();
}
