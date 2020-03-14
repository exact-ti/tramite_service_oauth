package com.exact.oauth.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Value("${config.security.oauth.client.id}")
	private String clientId;

	@Value("${config.security.oauth.client.secret}")
	private String clientSecret;

	@Value("${config.security.oauth.client.jwt.key}")
	private String jwtKey;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenInfoAdicional tokenInfoAdicional;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permiteAll()")
		.checkTokenAccess("isAuthenticated");//validar el token
	}
	
	
	//registrar clientes
	//registrar a las aplicaciones 
	//proporciona  mayor de seguridad, atributos de nuestra aplicacion cliente
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId )//identificador de nuestra aplicacion
		.secret(passwordEncoder.encode(clientSecret))
		.scopes("write")//el alcance de nuestra aplicacion cliente, en este caso puede leer y escribir
		.authorizedGrantTypes("password", "refresh_token")//el tipo de consesion de nuestra authenticacion// como vamos a obtener el jwt y obtener un nuevo token
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
	}

	
	//agregar informacion que queremos registrar al token //generar
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList( tokenInfoAdicional, accessTokenConverter() ));
		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(TokenStore())//generar y guardar el token
		.accessTokenConverter(accessTokenConverter())//agregar informacion extra	
		.tokenEnhancer(tokenEnhancerChain);
	}
	
	@Bean
	public JwtTokenStore TokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);//agregar un codigo secreto para firmar el tokern //esta en el codigo de configuraciones
		return tokenConverter;
	}
}
