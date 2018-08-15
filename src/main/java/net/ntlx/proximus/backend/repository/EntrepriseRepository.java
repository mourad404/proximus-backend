package net.ntlx.proximus.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ntlx.proximus.backend.model.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

	public Page<Entreprise> findAll(Specification<Entreprise> entreSpec, Pageable p);
}
