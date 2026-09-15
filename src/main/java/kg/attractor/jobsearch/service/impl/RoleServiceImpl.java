package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl
        implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role findByRole(String role) {
        return roleRepository
                .findByRole(role)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Role " + role + " not found"
                        )
                );
    }
}