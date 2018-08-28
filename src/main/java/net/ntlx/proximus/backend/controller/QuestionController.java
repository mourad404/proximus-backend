package net.ntlx.proximus.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

	@Secured(value = { "ROLE_ADMIN" })
	@GetMapping("/questions")
	public List<Question> listQuestions() {
		return questionMetier.listQuestions();
	}

	@GetMapping("/public/entreprises/{ide}/questions")
	public List<Question> listQuestionsParEntreprise(@PathVariable("ide") Long id) {
		return questionMetier.listQuestionsByEse(id);
	}

	@Secured(value = { "ROLE_USER" })
	@PostMapping("/entreprises/{ide}/questions")
	public ResponseEntity<Question> ajouterQuestion(@PathVariable("ide") Long ide, @Valid @RequestBody Question q) {
		return questionMetier.ajouterQuestion(ide, q);
	}

	@Secured(value = { "ROLE_USER" })
	@DeleteMapping("/utilisateurs/{idu}/questions/{idq}")
	public ResponseEntity<String> supprimerQuestion(@PathVariable("idu") Long idu, @PathVariable("idq") Long idq) {
		return questionMetier.supprimerQuestion(idu, idq);
	}

	@Secured(value = { "ROLE_ADMIN" })
	@DeleteMapping("/questions/{idq}")
	public ResponseEntity<String> supprimerQuestionAdmin(@PathVariable("idq") Long idq) {
		return questionMetier.supprimerQuestionAdmin(idq);
	}

	@Secured(value = { "ROLE_USER" })
	@PutMapping("/questions/{idq}")
	public ResponseEntity<Question> modifierAvis(@PathVariable("idq") Long idq, @Valid @RequestBody Question q) {
		return questionMetier.modifierQuestion(idq, q);
	}
}
