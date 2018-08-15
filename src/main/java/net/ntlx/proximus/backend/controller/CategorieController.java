package net.ntlx.proximus.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ntlx.proximus.backend.metier.abst.CategorieMetier;
import net.ntlx.proximus.backend.model.Categorie;
import net.ntlx.proximus.backend.model.Entreprise;

@RestController
public class CategorieController {

	@Autowired
	private CategorieMetier categorieMetier;

	@GetMapping("/categories")
	public List<Categorie> listCategories() {
		return categorieMetier.listCategories();
	}

	@PostMapping("/categories")
	public ResponseEntity<Categorie> ajouterCategorie(@Valid @RequestBody Categorie c) {
		return categorieMetier.ajouterCategorie(c);
	}

	@GetMapping("/categories/{idc}")
	public Categorie rechercherCategories(@PathVariable("idc") Long id) {
		return categorieMetier.rechercherCategorie(id);
	}

	@DeleteMapping("/categories/{idc}")
	public ResponseEntity<String> supprimerCategorie(@PathVariable("idc") Long id) {
		return categorieMetier.supprimerCategorie(id);
	}

	@PutMapping("/categories/{idc}")
	public ResponseEntity<Categorie> modifierCategorie(@PathVariable("idc") Long id, @RequestBody Categorie c) {
		return categorieMetier.modifierCategorie(id, c);
	}

	@GetMapping("/categories/{idc}/ranking")
	public ResponseEntity<Page<Entreprise>> classementParCategorie(@PathVariable("idc") Long idc,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "4") Integer size) {
		return categorieMetier.classementParCategorie(idc, page, size);
	}
}
