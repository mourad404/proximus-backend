package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Avis;

public interface AvisMetier {

	public List<Avis> listAvis();

	public Avis chercherAvisParUtilisateur(Long idu, Long ide);

	public ResponseEntity<Avis> ajouterAvis(Long ide, Avis a);

	public List<Avis> listAvisParEntreprise(Long id);

	public ResponseEntity<String> supprimerAvis(Long idu, Long ida);

	public ResponseEntity<String> supprimerAvisAdmin(Long ida);

	public ResponseEntity<Avis> modifierAvis(Long ida, Avis newAvis);
}
