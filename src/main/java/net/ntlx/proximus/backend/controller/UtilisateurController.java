package net.ntlx.proximus.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.ntlx.proximus.backend.metier.abst.UtilisateurMetier;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.Utilisateur;

@RestController
public class UtilisateurController {

	@Autowired
	private UtilisateurMetier utilisateurMetier;

	@GetMapping("/utilisateurs")
	public ResponseEntity<Page<Utilisateur>> listUtilisateur(
			@Or({ @Spec(path = "nom", spec = LikeIgnoreCase.class), @Spec(path = "prenom", spec = LikeIgnoreCase.class),
					@Spec(path = "email", spec = LikeIgnoreCase.class) }) Specification<Utilisateur> utiliSpec,
			@RequestParam(value = "sort", required = false) List<String> dirAsc,
			@RequestParam(value = "desc", required = false) List<String> dirDesc,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "4") Integer size) {
		return utilisateurMetier.listUtilisateurs(utiliSpec, dirAsc, dirDesc, page, size);
	}

	@GetMapping("/utilisateurs/{idu}")
	public Utilisateur rechercherUtilisateur(@PathVariable("idu") Long id) {
		return utilisateurMetier.rechercherUtilisateur(id);
	}

	@PostMapping("/utilisateurs")
	public ResponseEntity<Utilisateur> ajouterUtilisateur(@Valid @RequestBody Utilisateur u) {
		return utilisateurMetier.ajouterUtilisateur(u);
	}

	@DeleteMapping("/utilisateurs/{idu}")
	public ResponseEntity<String> supprimerUtilisateur(@PathVariable("idu") Long id) {
		return utilisateurMetier.supprimerUtilisateur(id);
	}

	@PutMapping("/utilisateurs/{idu}")
	public ResponseEntity<Utilisateur> modifierUtilisateur(@PathVariable("idu") Long id,
			@Valid @RequestBody Utilisateur u) {
		return utilisateurMetier.modifierUtilisateur(id, u);
	}

	/**
	 * BOOKMARK
	 */
	@PutMapping("/utilisateurs/{idu}/entreprises/{ide}")
	public Utilisateur ajouterOrSupprimerFavoris(@PathVariable("idu") Long idu, @PathVariable("ide") Long ide) {
		return utilisateurMetier.ajouterOrSupprimerFavoris(idu, ide);
	}

	/**
	 * BOOKMARK
	 */
	@GetMapping("/utilisateurs/{idu}/entreprises")
	public List<Entreprise> listFavoris(@PathVariable("idu") Long id) {
		return utilisateurMetier.listFavoris(id);
	}
}
