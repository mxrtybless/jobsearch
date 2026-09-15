package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Role;

public interface RoleService {

    Role findByRole(String role);
}