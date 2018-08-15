package net.ntlx.proximus.backend.metier.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.UnAuthorizedException;
import net.ntlx.proximus.backend.metier.abst.ReponseMetier;
import net.ntlx.proximus.backend.model.Question;
import net.ntlx.proximus.backend.model.Reponse;
import net.ntlx.proximus.backend.repository.QuestionRepository;
import net.ntlx.proximus.backend.repository.ReponseRepository;

@Service
public class ReponseMetierImpl implements ReponseMetier {

	@Autowired
	private ReponseRepository reponseRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public List<Reponse> listReponses() {
		return reponseRepository.findAll();
	}

	@Override
	@Transactional
	public ResponseEntity<Reponse> ajouterReponse(Long idq, Reponse r) {
		final Question question = questionRepository.findById(idq).get();
		r.setQuestion(question);
		r.setDateAjout(new Date());

		final Reponse reponseAdded = reponseRepository.save(r);
		if (reponseAdded == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(reponseAdded);
		}
	}

	@Override
	public List<Reponse> listReponsesParQuestion(Long id) {
		return reponseRepository.getReponsesByQst(id);
	}

	@Override
	public ResponseEntity<String> supprimerReponse(Long id) {
		reponseRepository.deleteById(id);
		if (reponseRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La réponse (" + id + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	@Transactional
	public ResponseEntity<Reponse> modifierReponse(Long idr, Reponse newReponse) {

		final Reponse oldReponse = reponseRepository.findById(idr).get();
		if (oldReponse.getUtilisateur().getId() != newReponse.getUtilisateur().getId())
			throw new UnAuthorizedException("Vous ne pouvez modifier que vos reponses !!");
		newReponse.setId(idr);
		newReponse.setQuestion(oldReponse.getQuestion());
		newReponse.setDateAjout(new Date());

		final Reponse reponseAdded = reponseRepository.save(newReponse);
		if (reponseAdded == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(reponseAdded);
		}
	}

}
