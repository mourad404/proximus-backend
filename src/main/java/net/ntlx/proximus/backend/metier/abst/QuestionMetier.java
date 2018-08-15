package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Question;

public interface QuestionMetier {

	public List<Question> listQuestions();

	public ResponseEntity<Question> ajouterQuestion(Long ide, Question q);

	public List<Question> listQuestionsByEse(Long id);

	public ResponseEntity<String> supprimerQuestion(Long id);

	public ResponseEntity<Question> modifierQuestion(Long idq, Question newQuestion);

}
