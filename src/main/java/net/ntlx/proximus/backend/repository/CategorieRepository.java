package net.ntlx.proximus.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.Categorie;
import net.ntlx.proximus.backend.model.Entreprise;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

	@Query("SELECT e FROM Entreprise e WHERE e.categorie.id = :idc ORDER BY e.notation DESC")
	public Page<Entreprise> rankingByCat(@Param("idc") Long idc, Pageable p);
}
