package com.exact.oauth.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.exact.oauth.dao.UsuarioDAO;
import com.exact.oauth.entity.Usuario;

@Component
public class TokenInfoAdicional implements TokenEnhancer {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info =  new HashMap<String, Object>();
		Usuario usuario =  usuarioDAO.getUsuarioByName(authentication.getName());
		info.put("nombre", usuario.getNombre());
		info.put("correo", usuario.getCorreo());
		info.put("pefilId", usuario.getPerfilId());
		info.put("id", usuario.getId());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
