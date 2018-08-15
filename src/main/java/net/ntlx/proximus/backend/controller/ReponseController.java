package net.ntlx.proximus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ntlx.proximus.backend.metier.abst.ReponseMetier;
import net.ntlx.proximus.backend.model.Reponse;

@RestController
public class ReponseController {

	@Autowired
	private ReponseMetier reponseMetier;

	@GetMapping("/reponses")
	public List<Reponse> listReponses() {
		return reponseMetier.listReponses();
	}

	@PostMapping("/questions/{idq}/reponses")
	public ResponseEntity<Reponse> ajouterReponse(@PathVariable("idq") Long idq, @RequestBody Reponse r) {
		return reponseMetier.ajouterReponse(idq, r);
	}

	@GetMapping("/questions/{idq}/reponses")
	public List<Reponse> listReponsesParQuestion(@PathVariable("idq") Long id) {
		return reponseMetier.listReponsesParQuestion(id);
	}

	@DeleteMapping("/reponses/{idr}")
	public ResponseEntity<String> supprimerReponse(@PathVariable("idr") Long id) {
		return reponseMetier.supprimerReponse(id);
	}

	@PutMapping("/questions/{idq}/reponses/{idr}")
	public ResponseEntity<Reponse> modifierReponse(@PathVariable("idr") Long idr, @RequestBody Reponse newReponse) {
		return reponseMetier.modifierReponse(idr, newReponse);
	}
}
