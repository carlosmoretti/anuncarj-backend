package com.br.anunciazo.application.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.br.anunciazo.application.model.Anuncio;
import com.br.anunciazo.application.model.Sessao;
import com.br.anunciazo.application.repository.AnuncioRepository;
import com.br.anunciazo.application.repository.BaseRepository;
import com.br.anunciazo.application.service.AnuncioService;
import com.br.anunciazo.application.service.GeolocalizacaoService;
import com.br.anunciazo.application.service.SessaoService;

@Service
public class AnuncioServiceImpl extends ServiceBaseImpl<Anuncio, Long> implements AnuncioService {
	
	private final AnuncioRepository repository;
	private final GeolocalizacaoService geolocalizacaoService;
	private final SessaoService sessaoService;
	
	@Autowired
	public AnuncioServiceImpl(final AnuncioRepository repository,
			GeolocalizacaoService geolocalizacaoService,
			final SessaoService sessaoService) {
		this.geolocalizacaoService = geolocalizacaoService;
		this.repository = repository;
		this.sessaoService = sessaoService;
	}

	@Override
	public BaseRepository<Anuncio, Long> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	@Override
	public Page<Anuncio> get(Pageable pageable, Specification<Anuncio> specification) {
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		Page<Anuncio> anuncios = super.get(pageable, specification);
		Optional<Sessao> sessao = this.sessaoService.obterSessao();
		
		
		for(Anuncio anuncio : anuncios.getContent())
			anuncio.setDistancia(geolocalizacaoService
					.obterDistanciaEmMetros(new Point(sessao.get().getX(), sessao.get().getY()), anuncio));
		
		return anuncios;
	}
	
	@Override
	public Anuncio get(Long id) {
		Anuncio anuncio = super.get(id);
		Optional<Sessao> sessao = this.sessaoService.obterSessao();
		anuncio.setDistancia(geolocalizacaoService
				.obterDistanciaEmMetros(new Point(sessao.get().getX(), sessao.get().getY()), anuncio));
		return anuncio;
	}
}
