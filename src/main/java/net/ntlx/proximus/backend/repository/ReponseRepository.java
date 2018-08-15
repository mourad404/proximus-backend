package net.ntlx.proximus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.Reponse;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {

	@Query("SELECT r FROM Reponse r WHERE r.question.id = :idq ORDER BY r.dateAjout DESC")
	public List<Reponse> getReponsesByQst(@Param("idq") Long idq);
}
