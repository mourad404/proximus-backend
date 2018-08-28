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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ntlx.proximus.backend.metier.abst.CompteUtilisateurMetier;
import net.ntlx.proximus.backend.model.CompteUtilisateur;

@RestController
public class CompteUtilisateurController {

	@Autowired
	private CompteUtilisateurMetier compteUtilisateurMetier;

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@PostMapping("/users")
	public ResponseEntity<CompteUtilisateur> ajtUser(@Valid @RequestBody CompteUtilisateur cu) {
		return compteUtilisateurMetier.ajtUser(cu);
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@GetMapping("/users")
	public List<CompteUtilisateur> listUsers() {
		return compteUtilisateurMetier.listUsers();
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@DeleteMapping("/users")
	public ResponseEntity<String> supprimerUser(@RequestParam String username) {
		return compteUtilisateurMetier.supprimerUser(username);
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@GetMapping("/roletouser")
	public void roleToUser(@RequestParam String username, @RequestParam String rolename) {
		compteUtilisateurMetier.roleToUser(username, rolename);
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@GetMapping("/users/{username}")
	public CompteUtilisateur findByUsername(@PathVariable String username) {
		return compteUtilisateurMetier.findByUsername(username);
	}
}
