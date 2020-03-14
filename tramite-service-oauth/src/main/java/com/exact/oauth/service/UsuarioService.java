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
import com.exact.oauth.dao.UsuarioDAO;
import com.exact.oauth.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	IPerfilClient perfilClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDAO.getUsuarioByName(username);
		if (usuario == null) throw new UsernameNotFoundException(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() ; 
		Iterable<Map<String, Object>> acciones = perfilClient.findAccionesById(usuario.getPerfilId());
		for(Map<String, Object> accion  : acciones) {
			authorities.add( new SimpleGrantedAuthority(accion.get("nombre").toString()    )  );					
		}
		
		return new User(usuario.getNombre(), usuario.getPassword(), true , true, true, true, authorities);
	}

}
