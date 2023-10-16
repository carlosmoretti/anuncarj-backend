package com.br.anunciazo.application.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.br.anunciazo.application.model.Imagem;

@Repository
public interface ImagemRepository extends BaseRepository<Imagem, Long> {
	Optional<Imagem> findByAnuncio_Id(Long idAnuncio);
}
