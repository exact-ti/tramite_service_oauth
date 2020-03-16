package com.exact.oauth.clients;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="servicio-perfil")
public interface IPerfilClient {
	
	@GetMapping("/perfiles/{id}/acciones")
	public Iterable<Map<String, Object>> findAccionesById(@PathVariable Long id);

}
