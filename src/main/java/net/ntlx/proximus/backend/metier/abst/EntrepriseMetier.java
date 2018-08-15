package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Entreprise;

public interface EntrepriseMetier {

	public ResponseEntity<Page<Entreprise>> listEntreprises(Specification<Entreprise> entreSpec, List<String> dirAsc,
			List<String> dirDesc, Integer page, Integer size);

	public ResponseEntity<Entreprise> ajouterEntreprise(Long idb, Entreprise e);

	public Entreprise rechercherEntreprise(Long id);

	public ResponseEntity<String> supprimerEntreprise(Long id);

	public ResponseEntity<Entreprise> modifierEntreprise(Long id, Entreprise e);

}
