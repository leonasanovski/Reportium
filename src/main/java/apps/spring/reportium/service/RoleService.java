package apps.spring.reportium.service;

import apps.spring.reportium.entity.Role;

import java.util.List;

public interface RoleService {
    Role findById(Integer roleId);
    List<Role> findAll();
}
