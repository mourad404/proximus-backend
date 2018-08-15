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

import net.ntlx.proximus.backend.metier.abst.AvisMetier;
import net.ntlx.proximus.backend.model.Avis;

@RestController
public class AvisController {

	@Autowired
	private AvisMetier avisMetier;

	@GetMapping("/avis")
	public List<Avis> listAvis() {
		return avisMetier.listAvis();
	}

	@GetMapping("/entreprises/{ide}/avis")
	public List<Avis> listAvisParEntreprise(@PathVariable("ide") Long id) {
		return avisMetier.listAvisParEntreprise(id);
	}

	@GetMapping("/utilisateurs/{idu}/entreprises/{ide}/avis")
	public Avis chercherAvisEntrepriseParUtilis(@PathVariable("idu") Long idu, @PathVariable("ide") Long ide) {
		return avisMetier.chercherAvisParUtilisateur(idu, ide);
	}

	@PostMapping("/entreprises/{ide}/avis")
	public ResponseEntity<Avis> ajouterAvis(@PathVariable("ide") Long ide, @Valid @RequestBody Avis a) {
		return avisMetier.ajouterAvis(ide, a);
	}

	@DeleteMapping("/avis/{ida}")
	public ResponseEntity<String> supprimerAvis(@PathVariable("ida") Long id) {
		return avisMetier.supprimerAvis(id);
	}

	@PutMapping("/avis/{ida}")
	public ResponseEntity<Avis> modifierAvis(@PathVariable("ida") Long ida, @Valid @RequestBody Avis a) {
		return avisMetier.modifierAvis(ida, a);
	}

}
