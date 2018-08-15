package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.ImageEse;

public interface ImageEseMetier {

	public List<ImageEse> listImagesParEntreprise(Long ide);
	
	public ResponseEntity<ImageEse> ajouterImage(Long ide, ImageEse i);
	
	public ImageEse rechercherImage(Long idi);
	
	public ResponseEntity<String> supprimerImage(Long idi);
	
	public ResponseEntity<ImageEse> remplacerImage(Long ide, Long idi, ImageEse i);
}
