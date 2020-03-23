package com.exact.oauth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exact.oauth.clients.IPerfilClient;
import com.exact.oauth.clients.ILoginClient;
import com.exact.oauth.model.Usuario;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	ILoginClient usuarioClient;
	
	@Autowired
	IPerfilClient perfilClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioClient.listarUsuarioPorUsername(username);
		if (usuario == null) throw new UsernameNotFoundException(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() ; 
		Iterable<Map<String, Object>> acciones = perfilClient.findAccionesById(usuario.getPerfilId());
		for(Map<String, Object> accion  : acciones) {
			authorities.add( new SimpleGrantedAuthority(accion.get("nombre").toString()    )  );					
		}
		
		return new User(usuario.getNombre(), usuario.getPassword(), true , true, true, true, authorities);
	}

}
