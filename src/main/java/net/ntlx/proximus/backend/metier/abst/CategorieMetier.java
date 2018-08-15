package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Categorie;
import net.ntlx.proximus.backend.model.Entreprise;

public interface CategorieMetier {

	public List<Categorie> listCategories();

	public ResponseEntity<Categorie> ajouterCategorie(Categorie c);

	public Categorie rechercherCategorie(Long id);

	public ResponseEntity<String> supprimerCategorie(Long id);

	public ResponseEntity<Categorie> modifierCategorie(Long id, Categorie c);

	public ResponseEntity<Page<Entreprise>> classementParCategorie(Long idc, Integer page, Integer size);

}
