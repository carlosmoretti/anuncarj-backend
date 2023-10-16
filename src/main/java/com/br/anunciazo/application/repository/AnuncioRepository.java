package com.br.anunciazo.application.repository;

import org.springframework.stereotype.Repository;

import com.br.anunciazo.application.model.Anuncio;

@Repository
public interface AnuncioRepository extends BaseRepository<Anuncio, Long> {
}
