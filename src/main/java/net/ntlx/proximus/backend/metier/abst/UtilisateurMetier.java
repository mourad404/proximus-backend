package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.Utilisateur;
import net.ntlx.proximus.backend.model.UtilisateurForm;

public interface UtilisateurMetier {

	public ResponseEntity<Page<Utilisateur>> listUtilisateurs(Specification<Utilisateur> utiliSpec, List<String> dirAsc,
			List<String> dirDesc, Integer page, Integer size);

	public ResponseEntity<Utilisateur> ajouterUtilisateur(UtilisateurForm uf);

	public Utilisateur rechercherUtilisateur(Long id);

	public ResponseEntity<String> supprimerUtilisateur(Long id);

	public ResponseEntity<Utilisateur> modifierUtilisateur(Long id, Utilisateur u);

	public Utilisateur ajouterOrSupprimerFavoris(Long idu, Long ide);

	public List<Entreprise> listFavoris(Long id);
}
