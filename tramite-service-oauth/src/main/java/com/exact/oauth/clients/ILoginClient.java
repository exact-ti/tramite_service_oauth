package com.exact.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exact.oauth.model.Usuario;

@FeignClient(name="servicio-usuario")
public interface ILoginClient {
	
	@GetMapping("/login")
	Usuario  listarUsuarioPorUsername(@RequestParam("username") String username);
	
	@PostMapping("usuarios/{id}/sesiones")
	void guardarInicioDeSesion(@PathVariable Long id);
	
}
