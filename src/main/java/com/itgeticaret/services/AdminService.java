package com.itgeticaret.services;

import com.itgeticaret.models.Admin;
import com.itgeticaret.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin getAdminById(long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - admin does not exist");
        }
        return adminOptional.get();
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void createAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public void updateAdmin(long id,Admin admin) {
        Optional<Admin> existingAdminOptional = adminRepository.findById(id);
        if (existingAdminOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - admin does not exist");
        }
        Admin existingAdmin = existingAdminOptional.get();
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPassword(admin.getPassword());
        adminRepository.save(existingAdmin);
    }

    @Override
    public void deleteAdmin(long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }
}
