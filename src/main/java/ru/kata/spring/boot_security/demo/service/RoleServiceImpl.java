package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService  {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    @Override
    @Transactional
    public List<Role> getRolesById(List<Long> roleId) {
        return roleRepository.findAllById(roleId);
    }
}
