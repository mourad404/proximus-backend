package net.ntlx.proximus.backend.metier.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.BusinessOwnerMetier;
import net.ntlx.proximus.backend.metier.abst.CompteUtilisateurMetier;
import net.ntlx.proximus.backend.model.BusinessOwner;
import net.ntlx.proximus.backend.model.CompteUtilisateur;
import net.ntlx.proximus.backend.model.UtilisateurForm;
import net.ntlx.proximus.backend.repository.BusinessOwnerRepository;
import net.ntlx.proximus.backend.repository.CompteUtilisateurRepository;

@Service
public class BusinessOwnerMetierImpl implements BusinessOwnerMetier {

	@Autowired
	private BusinessOwnerRepository businessOwnerRepository;

	@Autowired
	private CompteUtilisateurRepository compteUtilisateurRepository;

	@Autowired
	private CompteUtilisateurMetier compteUtilisateurMetier;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<BusinessOwner> listBO() {
		return businessOwnerRepository.findAll();
	}

	@Override
	@Transactional
	public ResponseEntity<BusinessOwner> ajouterBO(UtilisateurForm uf) {
		if (compteUtilisateurRepository.existsByUsername(uf.getEmail()))
			throw new RuntimeException("Cet email est déjà utilisé !!");
		if (uf.getPassword() != uf.getRepassword())
			throw new RuntimeException("Veuillez confirmer votre mot de passe !! ");
		final BusinessOwner bo = new BusinessOwner();
		bo.setNom(uf.getNom());
		bo.setPrenom(uf.getPrenom());
		bo.setEmail(uf.getEmail());
		bo.setCodePostal(uf.getCodePostal());
		bo.setDateNaissance(uf.getDateNaissance());
		bo.setTel(uf.getTel());
		final BusinessOwner newBO = businessOwnerRepository.save(bo);
		if (newBO == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			final String passEncoded = bCryptPasswordEncoder.encode(uf.getPassword());
			final CompteUtilisateur cu = new CompteUtilisateur();
			cu.setUsername(uf.getEmail());
			cu.setPassword(passEncoded);
			cu.setEnabled(true);
			cu.setUtilisateur(newBO);
			compteUtilisateurRepository.save(cu);
			compteUtilisateurMetier.roleToUser(uf.getEmail(), "ROLE_BO");
			compteUtilisateurMetier.roleToUser(uf.getEmail(), "ROLE_USER");
			return ResponseEntity.status(HttpStatus.CREATED).body(newBO);
		}
	}

	@Override
	public BusinessOwner rechercherBO(Long id) {
		final BusinessOwner newBO = businessOwnerRepository.findById(id).get();
		if (newBO == null)
			throw new ResourceNotFoundException("Business Owner (id: " + id + ") est introuvable !!");
		return newBO;
	}

	@Override
	public ResponseEntity<String> supprimerBO(Long id) {
		businessOwnerRepository.deleteById(id);
		if (businessOwnerRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Le Business Owner (" + id + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public ResponseEntity<BusinessOwner> modifierBO(Long id, BusinessOwner bo) {
		bo.setId(id);
		final BusinessOwner newBO = businessOwnerRepository.save(bo);
		if (newBO == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newBO);
		}
	}

}
