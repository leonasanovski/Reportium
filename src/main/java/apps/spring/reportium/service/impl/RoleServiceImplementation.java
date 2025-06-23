package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.Role;
import apps.spring.reportium.repository.RoleRepository;
import apps.spring.reportium.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImplementation implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Integer roleId) {
        return roleRepository
                .findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Role with id=%d not found", roleId)));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
