package net.ntlx.proximus.backend.metier.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.CompteRoleMetier;
import net.ntlx.proximus.backend.model.CompteRole;
import net.ntlx.proximus.backend.repository.CompteRoleRepository;

@Service
@Transactional
public class CompteRoleMetierImpl implements CompteRoleMetier {

	@Autowired
	private CompteRoleRepository compteRoleRepository;

	@Override
	public ResponseEntity<CompteRole> ajtRole(CompteRole cr) {
		final CompteRole newCompteRole = compteRoleRepository.save(cr);
		if (newCompteRole == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newCompteRole);
		}
	}

	@Override
	public List<CompteRole> listRoles() {
		return compteRoleRepository.findAll();
	}

	@Override
	public ResponseEntity<String> supprimerRole(String rolename) {
		compteRoleRepository.delete(compteRoleRepository.findByRolename(rolename));
		if (compteRoleRepository.existsByRolename(rolename)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("L'utilisateur (" + rolename + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public CompteRole findByRolename(String rolename) {
		final CompteRole newRole = compteRoleRepository.findByRolename(rolename);
		if (newRole == null)
			throw new ResourceNotFoundException("Utilisateur :" + rolename + ") est introuvable !!");
		return newRole;
	}

}
