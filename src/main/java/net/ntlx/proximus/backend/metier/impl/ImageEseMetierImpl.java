package net.ntlx.proximus.backend.metier.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.exception.UnAuthorizedException;
import net.ntlx.proximus.backend.metier.abst.ImageEseMetier;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.ImageEse;
import net.ntlx.proximus.backend.repository.EntrepriseRepository;
import net.ntlx.proximus.backend.repository.ImageEseRepository;

@Service
public class ImageEseMetierImpl implements ImageEseMetier {

	@Autowired
	private ImageEseRepository imageEseRepository;
	
	@Autowired
	private EntrepriseRepository entrepriseRepository;
	
	@Override
	public List<ImageEse> listImagesParEntreprise(Long ide) {
		return imageEseRepository.getImagesByEse(ide);
	}

	@Override
	public ResponseEntity<ImageEse> ajouterImage(Long ide, ImageEse i) {
		final Entreprise entreprise = entrepriseRepository.findById(ide).get();
	    if(entreprise == null) 
	    	throw new ResourceNotFoundException("L'entreprise est introuvable (id :"+ide+" )");
	    
	    final Collection<ImageEse> ImagesExistantes = entreprise.getImages();
	    if(ImagesExistantes.size() < 3){
	    	i.setEntreprise(entreprise);
	    	final ImageEse newImage = imageEseRepository.save(i);
	    	if(newImage == null) {
	    		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    	}else {
	    		return ResponseEntity.status(HttpStatus.CREATED).body(newImage);
	    	}
	    }else {
	    	throw new UnAuthorizedException("Vous ne pouvez pas ajouter plus que 3 photos !!");
	    }
	}

	@Override
	public ImageEse rechercherImage(Long idi) {
		final ImageEse newImageEse = imageEseRepository.findById(idi).get();
		if(newImageEse == null) 
			throw new ResourceNotFoundException("l'image (id: "+idi+") est introuvable !!");
		return newImageEse;
	}

	@Override
	public ResponseEntity<String> supprimerImage(Long idi) {
		imageEseRepository.deleteById(idi);
		if(imageEseRepository.existsById(idi)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La Catégorie ("+idi+") n'est pas supprimée");
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public ResponseEntity<ImageEse> remplacerImage(Long ide, Long idi, ImageEse i) {
		i.setId(idi);
		i.setEntreprise(entrepriseRepository.findById(ide).get());
		final ImageEse newImage = imageEseRepository.save(i);
		if(newImage == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newImage);
		}
	}

}
