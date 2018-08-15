package net.ntlx.proximus.backend.metier.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.CategorieMetier;
import net.ntlx.proximus.backend.model.Categorie;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.repository.CategorieRepository;

@Service
public class CategorieMetierImpl implements CategorieMetier {

	@Autowired
	private CategorieRepository categorieRepository;

	@Override
	public List<Categorie> listCategories() {
		return categorieRepository.findAll();
	}

	@Override
	public ResponseEntity<Categorie> ajouterCategorie(Categorie c) {
		final Categorie newCategorie = categorieRepository.save(c);
		if (newCategorie == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newCategorie);
		}
	}

	@Override
	public Categorie rechercherCategorie(Long id) {
		final Categorie newCategorie = categorieRepository.findById(id).get();
		if (newCategorie == null)
			throw new ResourceNotFoundException("la Catégorie (id: " + id + ") est introuvable !!");
		return newCategorie;
	}

	@Override
	public ResponseEntity<String> supprimerCategorie(Long id) {
		categorieRepository.deleteById(id);
		if (categorieRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La Catégorie (" + id + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public ResponseEntity<Categorie> modifierCategorie(Long id, Categorie c) {
		c.setId(id);
		final Categorie newCategorie = categorieRepository.save(c);
		if (newCategorie == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newCategorie);
		}
	}

	/**
	 * Classement par categorie !!
	 */
	@Override
	public ResponseEntity<Page<Entreprise>> classementParCategorie(Long idc, Integer page, Integer size) {
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.body(categorieRepository.rankingByCat(idc, PageRequest.of(page, size)));
	}

}
