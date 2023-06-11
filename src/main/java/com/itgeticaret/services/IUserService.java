package com.itgeticaret.services;

import com.itgeticaret.models.Role;
import com.itgeticaret.models.User;

import java.util.List;

public interface IUserService {

    User getUserById(long id);
    List<User> getAllUsers();
    void createUser(User user);
    void updateUser(long id,User user);
    void deleteUser(long id);
    void deleteUser(User user);
    //void assignRoles(User user, List<Role> roles);

    User getUserByActivationCode(String activationCode);
}
