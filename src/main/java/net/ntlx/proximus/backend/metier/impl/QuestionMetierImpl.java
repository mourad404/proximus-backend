package net.ntlx.proximus.backend.metier.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.UnAuthorizedException;
import net.ntlx.proximus.backend.metier.abst.QuestionMetier;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.Question;
import net.ntlx.proximus.backend.model.Utilisateur;
import net.ntlx.proximus.backend.repository.BusinessOwnerRepository;
import net.ntlx.proximus.backend.repository.EntrepriseRepository;
import net.ntlx.proximus.backend.repository.QuestionRepository;

@Service
public class QuestionMetierImpl implements QuestionMetier {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private BusinessOwnerRepository boRepository;

	@Override
	public List<Question> listQuestions() {
		return questionRepository.findAll();
	}

	@Override
	@Transactional
	public ResponseEntity<Question> ajouterQuestion(Long ide, Question q) {

		final Entreprise entreprise = entrepriseRepository.findById(ide).get();
		final Utilisateur utiliBO = boRepository.getBoByEntreprise(ide);
		final Utilisateur utiliSession = q.getUtilisateur();

		if (utiliBO.getId() == utiliSession.getId())
			throw new UnAuthorizedException("Vous ne pouvez pas ajouter une question sur votre entreprise !!");

		q.setEntreprise(entreprise);
		q.setDateAjout(new Date());

		final Question newQuestion = questionRepository.save(q);
		if (newQuestion == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion);
		}
	}

	@Override
	public List<Question> listQuestionsByEse(Long id) {
		return questionRepository.getQuestionsByEse(id);
	}

	@Override
	public ResponseEntity<String> supprimerQuestion(Long id) {
		questionRepository.deleteById(id);
		if (questionRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La question (" + id + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	@Transactional
	public ResponseEntity<Question> modifierQuestion(Long idq, Question newQuestion) {

		final Question oldQuestion = questionRepository.findById(idq).get();
		if (oldQuestion.getUtilisateur().getId() != newQuestion.getUtilisateur().getId())
			throw new UnAuthorizedException("Vous ne pouvez modifier que vos quetions !!");
		newQuestion.setId(idq);
		newQuestion.setEntreprise(oldQuestion.getEntreprise());
		newQuestion.setDateAjout(new Date());

		final Question questionAdded = questionRepository.save(newQuestion);
		if (questionAdded == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(questionAdded);
		}
	}

}
