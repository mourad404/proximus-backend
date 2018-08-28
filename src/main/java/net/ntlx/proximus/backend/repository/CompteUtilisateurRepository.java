package net.ntlx.proximus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ntlx.proximus.backend.model.CompteUtilisateur;

public interface CompteUtilisateurRepository extends JpaRepository<CompteUtilisateur, Long> {

	public CompteUtilisateur findByUsername(String username);

	public boolean existsByUsername(String username);

}
