package com.br.anunciazo.application.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.anunciazo.application.model.Imagem;
import com.br.anunciazo.application.repository.BaseRepository;
import com.br.anunciazo.application.repository.ImagemRepository;
import com.br.anunciazo.application.service.ImagemService;

@Service
public class ImagemServiceImpl extends ServiceBaseImpl<Imagem, Long> implements ImagemService {
	
	private final ImagemRepository imagemRepository;
	
	@Autowired
	public ImagemServiceImpl(final ImagemRepository imagemRepository) {
		this.imagemRepository = imagemRepository;
	}

	@Override
	public BaseRepository<Imagem, Long> getRepository() {
		return this.imagemRepository;
	}

	@Override
	public Optional<Imagem> findByAnuncio_Id(Long idAnuncio) {
		return this.imagemRepository.findByAnuncio_Id(idAnuncio);
	}

}
