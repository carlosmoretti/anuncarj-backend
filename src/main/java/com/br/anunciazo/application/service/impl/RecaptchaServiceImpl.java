package com.br.anunciazo.application.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.anunciazo.application.handler.NaoAutorizadoException;
import com.br.anunciazo.application.service.RecaptchaService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {
	
	@Value("${google.recaptcha.private-key}")
	private String privateKey;
	
	private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

	@Override
	public boolean verificar(String token) throws NaoAutorizadoException {
		RestTemplate restTemplate = new RestTemplate();
		String url = new StringBuilder()
				.append(RECAPTCHA_URL)
				.append("?secret=")
				.append(privateKey)
				.append("&response=")
				.append(token)
				.toString();
		
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
		
		if(Objects.nonNull(response))
			return response.getBody().get("success").asBoolean();
		
		throw new NaoAutorizadoException();
	}

}
