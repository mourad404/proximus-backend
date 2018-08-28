package net.ntlx.proximus.backend.controller;

import java.util.List;

import javax.validation.Valid;

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

import net.ntlx.proximus.backend.metier.abst.BusinessOwnerMetier;
import net.ntlx.proximus.backend.model.BusinessOwner;
import net.ntlx.proximus.backend.model.UtilisateurForm;

@RestController
public class BusinessOwnerController {

	@Autowired
	private BusinessOwnerMetier businessOwnerMetier;

	@Secured(value = { "ROLE_ADMIN" })
	@GetMapping("/bos")
	public List<BusinessOwner> listBO() {
		return businessOwnerMetier.listBO();
	}

	@Secured(value = { "ROLE_ADMIN" })
	@GetMapping("/bos/{idb}")
	public BusinessOwner rechercherBO(@PathVariable("idb") Long id) {
		return businessOwnerMetier.rechercherBO(id);
	}

	@PostMapping("/public/bos")
	public ResponseEntity<BusinessOwner> ajouterBO(@Valid @RequestBody UtilisateurForm uf) {
		return businessOwnerMetier.ajouterBO(uf);
	}

	@Secured(value = { "ROLE_ADMIN" })
	@DeleteMapping("/bos/{idb}")
	public ResponseEntity<String> supprimerBO(@PathVariable("idb") Long id) {
		return businessOwnerMetier.supprimerBO(id);
	}

	@Secured(value = { "ROLE_BO" })
	@PutMapping("/bos/{idb}")
	public ResponseEntity<BusinessOwner> modifierBO(@PathVariable("idb") Long id,
			@Valid @RequestBody BusinessOwner bo) {
		return businessOwnerMetier.modifierBO(id, bo);
	}
}
