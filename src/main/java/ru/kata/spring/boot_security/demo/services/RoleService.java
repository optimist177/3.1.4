package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    Collection<Role> listRoles();

    Role getRole(Long id);

    List<Role> getRolesListById(List<Long> roles);
}
