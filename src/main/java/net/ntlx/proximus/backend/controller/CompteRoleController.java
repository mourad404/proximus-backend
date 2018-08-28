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

import net.ntlx.proximus.backend.metier.abst.CompteRoleMetier;
import net.ntlx.proximus.backend.model.CompteRole;

@RestController
public class CompteRoleController {

	@Autowired
	private CompteRoleMetier compteRoleMetier;

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@PostMapping("/roles")
	public ResponseEntity<CompteRole> ajtRole(@Valid @RequestBody CompteRole cr) {
		return compteRoleMetier.ajtRole(cr);
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@GetMapping("/roles")
	public List<CompteRole> listRoles() {
		return compteRoleMetier.listRoles();
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@DeleteMapping("/roles")
	public ResponseEntity<String> supprimerRole(@RequestParam String rolename) {
		return compteRoleMetier.supprimerRole(rolename);
	}

	@Secured(value = { "ROLE_SUPER_ADMIN" })
	@GetMapping("/roles/{rolename}")
	public CompteRole findByRolename(@PathVariable String rolename) {
		return compteRoleMetier.findByRolename(rolename);
	}
}
