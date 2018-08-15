package net.ntlx.proximus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.Avis;

public interface AvisRepository extends JpaRepository<Avis, Long> {

	@Query("SELECT count(a) FROM Avis a WHERE a.entreprise.id = :ide AND a.utilisateur.id = :idu")
	public Long nombreAvisOnEseByUtilis(@Param("ide") Long ide, @Param("idu") Long idu);

	@Query("SELECT a FROM Avis a WHERE a.entreprise.id = :ide AND a.utilisateur.id = :idu")
	public Avis getAvisOnEseByUtilis(@Param("ide") Long ide, @Param("idu") Long idu);

	@Query("SELECT a FROM Avis a WHERE a.entreprise.id = :id ORDER BY a.dateAjout DESC")
	public List<Avis> getAvisByEse(@Param("id") Long id);
}
