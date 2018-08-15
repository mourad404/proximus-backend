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

import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.ntlx.proximus.backend.metier.abst.EntrepriseMetier;
import net.ntlx.proximus.backend.model.Entreprise;

@RestController
public class EntrepriseController {

	@Autowired
	private EntrepriseMetier entrepriseMetier;

	@GetMapping("/entreprises")
	public ResponseEntity<Page<Entreprise>> listEntreprise(@Join(path = "categorie", alias = "c") @Conjunction({
			@Or({ @Spec(path = "rs", spec = LikeIgnoreCase.class),
					@Spec(path = "c.label", params = "cat", spec = LikeIgnoreCase.class) }),
			@Or({ @Spec(path = "adresse", spec = LikeIgnoreCase.class),
					@Spec(path = "codePostal", params = "cp", spec = LikeIgnoreCase.class) }),
			@Or({ @Spec(path = "notation", params = "ratePlus", spec = GreaterThanOrEqual.class),
					@Spec(path = "notation", params = "rateMoins", spec = LessThanOrEqual.class) }),
			@Or({ @Spec(path = "dateCreation", params = "createdAfter", spec = GreaterThanOrEqual.class),
					@Spec(path = "dateCreation", params = "createdBefore", spec = LessThanOrEqual.class) })

	}) Specification<Entreprise> entreSpec, @RequestParam(value = "sort", required = false) List<String> dirAsc,
			@RequestParam(value = "desc", required = false) List<String> dirDesc,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "4") Integer size) {

		return entrepriseMetier.listEntreprises(entreSpec, dirAsc, dirDesc, page, size);
	}

	@PostMapping("/bos/{idb}/entreprises")
	public ResponseEntity<Entreprise> ajouterEntreprise(@PathVariable("idb") Long idb,
			@Valid @RequestBody Entreprise e) {
		return entrepriseMetier.ajouterEntreprise(idb, e);
	}

	@GetMapping("/entreprises/{ide}")
	public Entreprise rechercherEntreprise(@PathVariable("ide") Long id) {
		return entrepriseMetier.rechercherEntreprise(id);
	}

	@DeleteMapping("/entreprises/{ide}")
	public ResponseEntity<String> supprimerEntreprise(@PathVariable("ide") Long id) {
		return entrepriseMetier.supprimerEntreprise(id);
	}

	@PutMapping("/entreprises/{ide}")
	public ResponseEntity<Entreprise> modifierEntreprise(@PathVariable("ide") Long id,
			@Valid @RequestBody Entreprise e) {
		return entrepriseMetier.modifierEntreprise(id, e);
	}
}
