package com.br.anunciazo.application.service;

import java.util.Optional;

import com.br.anunciazo.application.model.Imagem;

public interface ImagemService extends ServiceBase<Imagem, Long> {
	Optional<Imagem> findByAnuncio_Id(Long idAnuncio);
}
