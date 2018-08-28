package net.ntlx.proximus.backend.security;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.model.CompteUtilisateur;
import net.ntlx.proximus.backend.model.Utilisateur;
import net.ntlx.proximus.backend.repository.CompteUtilisateurRepository;
import net.ntlx.proximus.backend.repository.UtilisateurRepository;

@Service
@Transactional
public class SocialConnectionSignup implements ConnectionSignUp {

	@Autowired
	private CompteUtilisateurRepository cuRepo;

	@Autowired
	private UtilisateurRepository uRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public String execute(Connection<?> connection) {
		if (connection.createData().getProviderId() == "google") {
			final UserProfile userProfile = connection.fetchUserProfile();
			final Utilisateur u = new Utilisateur();
			u.setNom(userProfile.getLastName());
			u.setPrenom(userProfile.getFirstName());
			u.setEmail(userProfile.getEmail());
			final Utilisateur newU = uRepo.save(u);
			final CompteUtilisateur cu = new CompteUtilisateur();
			cu.setUsername(userProfile.getEmail());
			cu.setPassword(bCryptPasswordEncoder.encode(randomAlphabetic(8)));
			cu.setEnabled(true);
			cu.setUtilisateur(newU);
			cuRepo.save(cu);
			if (!cuRepo.existsByUsername(userProfile.getEmail()))
				throw new RuntimeException("une erreur est survenue lors de l'enregistrement de vos données !!");
			return userProfile.getEmail();
		} else {
			final Facebook facebook = (Facebook) connection.getApi();
			final String[] fields = { "id", "email", "first_name", "last_name" };
			final User userProfile = facebook.fetchObject("me", User.class, fields);
			final Utilisateur u = new Utilisateur();
			u.setNom(userProfile.getLastName());
			u.setPrenom(userProfile.getFirstName());
			u.setEmail(userProfile.getEmail());
			final Utilisateur newU = uRepo.save(u);
			final CompteUtilisateur cu = new CompteUtilisateur();
			cu.setUsername(userProfile.getEmail());
			cu.setPassword(bCryptPasswordEncoder.encode(randomAlphabetic(8)));
			cu.setEnabled(true);
			cu.setUtilisateur(newU);
			cuRepo.save(cu);
			if (!cuRepo.existsByUsername(userProfile.getEmail()))
				throw new RuntimeException("une erreur est survenue lors de l'enregistrement de vos données !!");
			return userProfile.getEmail();
		}
	}

}
