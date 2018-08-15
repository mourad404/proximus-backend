package net.ntlx.proximus.backend.metier.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.BusinessOwnerMetier;
import net.ntlx.proximus.backend.model.BusinessOwner;
import net.ntlx.proximus.backend.repository.BusinessOwnerRepository;

@Service
public class BusinessOwnerMetierImpl implements BusinessOwnerMetier {

	@Autowired
	private BusinessOwnerRepository businessOwnerRepository;
	
	@Override
	public List<BusinessOwner> listBO() {
		return businessOwnerRepository.findAll();
	}

	@Override
	public ResponseEntity<BusinessOwner> ajouterBO(BusinessOwner bo) {
		final BusinessOwner newBO = businessOwnerRepository.save(bo);
		if(newBO == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newBO);
		}
	}

	@Override
	public BusinessOwner rechercherBO(Long id) {
		final BusinessOwner newBO = businessOwnerRepository.findById(id).get();
		if(newBO == null) 
			throw new ResourceNotFoundException("Business Owner (id: "+id+") est introuvable !!");
		return newBO;
	}
	
	@Override
	public ResponseEntity<String> supprimerBO(Long id) {
		businessOwnerRepository.deleteById(id);
		if(businessOwnerRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le Business Owner ("+id+") n'est pas supprimée");
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public ResponseEntity<BusinessOwner> modifierBO(Long id, BusinessOwner bo) {
		bo.setId(id);
		final BusinessOwner newBO = businessOwnerRepository.save(bo);
		if(newBO == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newBO);
		}
	}
	
	
}
