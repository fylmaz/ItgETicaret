package com.itgeticaret.services;

import com.itgeticaret.models.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    void saveRole(Role role);
}
