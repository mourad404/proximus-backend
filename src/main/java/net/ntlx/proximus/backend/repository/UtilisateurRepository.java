package net.ntlx.proximus.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ntlx.proximus.backend.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

	public Page<Utilisateur> findAll(Specification<Utilisateur> utiliSpec, Pageable p);
}
