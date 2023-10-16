package com.br.anunciazo.application.service;

import org.springframework.data.geo.Point;

import com.br.anunciazo.application.model.Anuncio;

public interface GeolocalizacaoService {

	Double obterDistanciaEmMetros(Point posicaoUsuario, Anuncio anuncio);
	Double obterDistanciaEmMetros(Point posicaoUsuario, Point posicaoAnuncio);
}
