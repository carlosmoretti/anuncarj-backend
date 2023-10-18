package com.br.anunciazo.application.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.anunciazo.application.controller.dto.SessaoDTO;
import com.br.anunciazo.application.repository.BaseRepository;
import com.br.anunciazo.application.service.SessaoService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SessaoServiceImpl implements SessaoService {
	
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	
	private final HttpServletRequest request;
	
	@Autowired
	public SessaoServiceImpl(final HttpServletRequest request) {
		this.request = request;
	}
	
	public static String criarToken() {
	    byte[] randomBytes = new byte[128];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}

	@Override
	public Optional<SessaoDTO> obterSessao() {
		BigDecimal x = BigDecimal.valueOf(Double.valueOf(request.getHeader("Geolocalizacaox").toString()));
		BigDecimal y = BigDecimal.valueOf(Double.valueOf(request.getHeader("Geolocalizacaoy").toString()));
		
		return Optional.of(new SessaoDTO(x, y));
	}
	
}
