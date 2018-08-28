package net.ntlx.proximus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ntlx.proximus.backend.model.CompteRole;

public interface CompteRoleRepository extends JpaRepository<CompteRole, Long> {

	public CompteRole findByRolename(String rolename);

	public boolean existsByRolename(String rolename);
}
