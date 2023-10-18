package com.br.anunciazo.application.controller.restrito;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.anunciazo.application.controller.dto.GeolocalizacaoDTO;
import com.br.anunciazo.application.service.GeolocalizacaoService;

@RestController
@RequestMapping(value = "/restrito/localizacao")
public class LocalizacaoController {
	
	private final GeolocalizacaoService geolocalizacaoService;
	
	public LocalizacaoController(final GeolocalizacaoService geolocalizacaoService) {
		this.geolocalizacaoService = geolocalizacaoService;
	}

	@GetMapping
	public GeolocalizacaoDTO get(@RequestParam(required = true) String endereco) {
		return this.geolocalizacaoService.consultar(endereco);
	}
	
}
