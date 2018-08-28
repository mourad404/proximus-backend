package net.ntlx.proximus.backend.security;

import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class SocialSignInAdapter implements SignInAdapter {
	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
		String username;
		if (connection.createData().getProviderId() == "google") {
			final UserProfile userProfile = connection.fetchUserProfile();
			username = userProfile.getEmail();
		} else {
			final Facebook facebook = (Facebook) connection.getApi();
			final String[] fields = { "id", "email", "first_name", "last_name" };
			final User userProfile = facebook.fetchObject("me", User.class, fields);
			username = userProfile.getEmail();
		}
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null,
				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
		return null;
	}
}
