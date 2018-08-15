package net.ntlx.proximus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query("SELECT q FROM Question q WHERE q.entreprise.id = :ide ORDER BY q.dateAjout DESC")
	public List<Question> getQuestionsByEse(@Param("ide") Long id);
}
