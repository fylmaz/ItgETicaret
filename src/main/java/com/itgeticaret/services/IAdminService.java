package com.itgeticaret.services;

import com.itgeticaret.models.Admin;

import java.util.List;

public interface IAdminService {
    Admin getAdminById(long id);
    List<Admin> getAllAdmins();
    void createAdmin(Admin admin);
    void updateAdmin(long id, Admin admin);
    void deleteAdmin(long id);

    void deleteAdmin(Admin admin);
}


