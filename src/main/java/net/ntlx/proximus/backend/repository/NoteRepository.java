package net.ntlx.proximus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

	@Query("SELECT avg(n.valeur) FROM Note n WHERE n.entreprise.id = :id")
	public Double notationOfEse(@Param("id") Long id);

	@Query("SELECT count(n) FROM Note n WHERE n.entreprise.id = :ide AND n.utilisateur.id = :idu")
	public Long nombreNotesOnEseByUtilis(@Param("ide") Long ide, @Param("idu") Long idu);

	@Query("SELECT n FROM Note n WHERE n.entreprise.id = :ide AND n.utilisateur.id = :idu")
	public Note getNoteOnEseByUtilis(@Param("ide") Long ide, @Param("idu") Long idu);

	@Query("SELECT n FROM Note n WHERE n.entreprise.id = :id ORDER BY n.dateAjout DESC")
	public List<Note> getNotesByEse(@Param("id") Long id);
}
