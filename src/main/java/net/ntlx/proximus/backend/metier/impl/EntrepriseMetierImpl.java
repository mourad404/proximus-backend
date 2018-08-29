package net.ntlx.proximus.backend.metier.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.EntrepriseMetier;
import net.ntlx.proximus.backend.model.BusinessOwner;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.repository.BusinessOwnerRepository;
import net.ntlx.proximus.backend.repository.EntrepriseRepository;

@Service
public class EntrepriseMetierImpl implements EntrepriseMetier {

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private BusinessOwnerRepository businessOwnerRepository;

	@Override
	public ResponseEntity<Page<Entreprise>> listEntreprises(Specification<Entreprise> entreSpec, List<String> dirAsc,
			List<String> dirDesc, Integer page, Integer size) {

		final List<Order> ords = new ArrayList<Order>();
		if (dirDesc != null) {
			if (dirDesc.size() != 0) {
				for (int i = 0; i < dirAsc.size(); i++)
					ords.add(new Order(Direction.ASC, dirAsc.get(i)));

				for (int i = 0; i < dirDesc.size(); i++)
					ords.add(new Order(Direction.DESC, dirDesc.get(i)));

			} else {
				for (int i = 0; i < dirAsc.size(); i++)
					ords.add(new Order(Direction.DESC, dirAsc.get(i)));
			}

		} else {
			if (dirAsc != null)
				for (int i = 0; i < dirAsc.size(); i++)
					ords.add(new Order(Direction.ASC, dirAsc.get(i)));
		}
		final Pageable p = PageRequest.of(page, size, Sort.by(ords));
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(entrepriseRepository.findAll(entreSpec, p));
	}

	@Override
	@Transactional
	public ResponseEntity<Entreprise> ajouterEntreprise(Long idb, Entreprise e) {
		e.setNotation((double) 0);
		e.setNombreAvis(0);
		final Entreprise newEntreprise = entrepriseRepository.save(e);
		if (newEntreprise == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			final BusinessOwner bo = businessOwnerRepository.findById(idb).get();
			bo.setEntreprise(newEntreprise);
			return ResponseEntity.status(HttpStatus.CREATED).body(newEntreprise);
		}
	}

	@Override
	public Entreprise rechercherEntreprise(Long id) {
		final Entreprise newEntreprise = entrepriseRepository.findById(id).get();
		if (newEntreprise == null)
			throw new ResourceNotFoundException("L'entreprise (id: " + id + ") est introuvable !!");
		return newEntreprise;
	}

	@Override
	public ResponseEntity<String> supprimerEntreprise(Long id) {

		entrepriseRepository.deleteById(id);
		if (entrepriseRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'Entreprise (" + id + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public ResponseEntity<Entreprise> modifierEntreprise(Long id, Entreprise e) {
		e.setId(id);
		final Entreprise newEntreprise = entrepriseRepository.save(e);
		if (newEntreprise == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newEntreprise);
		}
	}

}
