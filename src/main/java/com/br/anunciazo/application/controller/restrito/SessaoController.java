package com.br.anunciazo.application.controller.restrito;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.anunciazo.application.handler.NaoAutorizadoException;
import com.br.anunciazo.application.model.Sessao;
import com.br.anunciazo.application.service.RecaptchaService;
import com.br.anunciazo.application.service.SessaoService;

@RestController
@RequestMapping(value = "/restrito/sessao")
public class SessaoController {
	
	private final SessaoService service;
	private final RecaptchaService recaptchaService;
	
	@Autowired
	public SessaoController(final SessaoService service,
			final RecaptchaService recaptchaService) {
		this.service = service;
		this.recaptchaService = recaptchaService;
	}
	
	@PostMapping
	public Sessao add(@RequestBody Sessao sessao,
			@RequestParam(required = true) String recaptchaToken) throws NaoAutorizadoException {
		this.recaptchaService.verificar(recaptchaToken);
		return this.service.add(sessao);
	}
	
	@GetMapping
	public Sessao get() {
		return this.service.obterSessao().get();
	}
	
	@PutMapping(value = "/{token}")
	public Sessao atualizarToken(@RequestBody Sessao sessao, @PathVariable String token) {
		Optional<Sessao> obj = service.findByToken(token);
		
		if(!obj.isPresent())
			throw new NullPointerException();
		
		Sessao sessaoPresent = obj.get();
		sessaoPresent.setX(sessao.getX());
		sessaoPresent.setY(sessao.getY());
		return this.service.update(sessaoPresent);
	}
}
