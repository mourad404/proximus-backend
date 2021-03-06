package net.ntlx.proximus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ntlx.proximus.backend.metier.abst.ImageEseMetier;
import net.ntlx.proximus.backend.model.ImageEse;

@RestController
public class ImageEseController {

	@Autowired
	private ImageEseMetier imageMetier;

	@GetMapping("/public/entreprises/{ide}/images")
	public List<ImageEse> listImagesParEntreprise(@PathVariable("ide") Long id) {
		return imageMetier.listImagesParEntreprise(id);
	}

	@Secured(value = { "ROLE_BO" })
	@PostMapping("/entreprises/{ide}/images")
	public ResponseEntity<ImageEse> ajouterImage(@PathVariable("ide") Long ide, @RequestBody ImageEse i) {
		return imageMetier.ajouterImage(ide, i);
	}

	@GetMapping("/public/images/{idi}")
	public ImageEse rechercherImage(@PathVariable("idi") Long id) {
		return imageMetier.rechercherImage(id);
	}

	@Secured(value = { "ROLE_BO" })
	@DeleteMapping("/images/{idi}")
	public ResponseEntity<String> supprimerImage(@PathVariable("idi") Long id) {
		return imageMetier.supprimerImage(id);
	}

	@Secured(value = { "ROLE_BO" })
	@PutMapping("/entreprises/{ide}/images/{idi}")
	public ResponseEntity<ImageEse> remplacerImage(@PathVariable("ide") Long ide, @PathVariable("idi") Long idi,
			@RequestBody ImageEse i) {
		return imageMetier.remplacerImage(ide, idi, i);
	}

}
