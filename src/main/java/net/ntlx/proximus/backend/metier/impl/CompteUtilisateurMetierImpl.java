package net.ntlx.proximus.backend.metier.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.CompteUtilisateurMetier;
import net.ntlx.proximus.backend.model.CompteRole;
import net.ntlx.proximus.backend.model.CompteUtilisateur;
import net.ntlx.proximus.backend.repository.CompteRoleRepository;
import net.ntlx.proximus.backend.repository.CompteUtilisateurRepository;

@Service
@Transactional
public class CompteUtilisateurMetierImpl implements CompteUtilisateurMetier {

	@Autowired
	private CompteUtilisateurRepository compteUtilisateurRepository;

	@Autowired
	private CompteRoleRepository compteRoleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public ResponseEntity<CompteUtilisateur> ajtUser(CompteUtilisateur cu) {
		if (compteUtilisateurRepository.existsByUsername(cu.getUsername()))
			throw new RuntimeException("Cet email est déjà utilisé !!");
		cu.setPassword(bCryptPasswordEncoder.encode(cu.getPassword()));
		final CompteUtilisateur newCompteUtilisateur = compteUtilisateurRepository.save(cu);
		if (newCompteUtilisateur == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newCompteUtilisateur);
		}
	}

	@Override
	public List<CompteUtilisateur> listUsers() {
		return compteUtilisateurRepository.findAll();
	}

	@Override
	public ResponseEntity<String> supprimerUser(String username) {
		compteUtilisateurRepository.delete(compteUtilisateurRepository.findByUsername(username));
		if (compteUtilisateurRepository.existsByUsername(username)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("L'utilisateur (" + username + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

	}

	@Override
	public void roleToUser(String username, String rolename) {
		final CompteUtilisateur cu = compteUtilisateurRepository.findByUsername(username);
		final CompteRole cr = compteRoleRepository.findByRolename(rolename);
		cu.getRoles().add(cr);
	}

	@Override
	public CompteUtilisateur findByUsername(String username) {
		final CompteUtilisateur newUtilisateur = compteUtilisateurRepository.findByUsername(username);
		if (newUtilisateur == null)
			throw new ResourceNotFoundException("Utilisateur :" + username + ") est introuvable !!");
		return newUtilisateur;
	}

}
