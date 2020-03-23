package com.exact.oauth.model;

import lombok.Data;

@Data
public class Usuario {
	
	private Long id;	
	private String username;
	private String nombre;
	private Long perfilId;
	private String correo;
	private String password;
}
