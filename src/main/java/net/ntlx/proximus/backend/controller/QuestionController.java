package net.ntlx.proximus.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ntlx.proximus.backend.metier.abst.QuestionMetier;
import net.ntlx.proximus.backend.model.Question;

@RestController
public class QuestionController {

	@Autowired
	private QuestionMetier questionMetier;

	@GetMapping("/questions")
	public List<Question> listQuestions() {
		return questionMetier.listQuestions();
	}

	@GetMapping("/entreprises/{ide}/questions")
	public List<Question> listQuestionsParEntreprise(@PathVariable("ide") Long id) {
		return questionMetier.listQuestionsByEse(id);
	}

	@PostMapping("/entreprises/{ide}/questions")
	public ResponseEntity<Question> ajouterQuestion(@PathVariable("ide") Long ide, @Valid @RequestBody Question q) {
		return questionMetier.ajouterQuestion(ide, q);
	}

	@DeleteMapping("/questions/{idq}")
	public ResponseEntity<String> supprimerQuestion(@PathVariable("idq") Long id) {
		return questionMetier.supprimerQuestion(id);
	}

	@PutMapping("/questions/{idq}")
	public ResponseEntity<Question> modifierAvis(@PathVariable("idq") Long idq, @Valid @RequestBody Question q) {
		return questionMetier.modifierQuestion(idq, q);
	}
}
