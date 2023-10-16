package com.br.anunciazo.application.service.impl;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.anunciazo.application.model.Sessao;
import com.br.anunciazo.application.repository.BaseRepository;
import com.br.anunciazo.application.repository.SessaoRepository;
import com.br.anunciazo.application.service.SessaoService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SessaoServiceImpl extends ServiceBaseImpl<Sessao, Long> implements SessaoService {
	
	private final SessaoRepository repository;
	
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	
	private final HttpServletRequest request;
	
	@Autowired
	public SessaoServiceImpl(final SessaoRepository repository,
			final HttpServletRequest request) {
		this.repository = repository;
		this.request = request;
	}

	@Override
	public BaseRepository<Sessao, Long> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	@Override
	public Sessao add(Sessao obj) {
		String token = criarToken();
		obj.setToken(token);
		
		return super.add(obj);
	}
	
	public static String criarToken() {
	    byte[] randomBytes = new byte[128];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}

	@Override
	public Optional<Sessao> findByToken(String token) {
		return this.repository.findByToken(token);
	}

	@Override
	public Optional<Sessao> obterSessao() {
		String sessionId = request.getHeader("sessionid");
		return this.findByToken(sessionId);
	}
	
}
