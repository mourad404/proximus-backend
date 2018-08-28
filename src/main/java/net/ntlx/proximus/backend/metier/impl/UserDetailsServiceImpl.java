package net.ntlx.proximus.backend.metier.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.ntlx.proximus.backend.metier.abst.CompteUtilisateurMetier;
import net.ntlx.proximus.backend.model.CompteUtilisateur;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CompteUtilisateurMetier cuMetier;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final CompteUtilisateur u = cuMetier.findByUsername(username);
		if (u == null)
			throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		u.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRolename()));
		});
		UserDetails spUser = new User(u.getUsername(), u.getPassword(), u.isEnabled(), true, true, true, authorities);
		return spUser;
	}

}
