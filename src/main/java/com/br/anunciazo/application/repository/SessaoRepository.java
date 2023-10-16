package com.br.anunciazo.application.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.br.anunciazo.application.model.Sessao;

@Repository
public interface SessaoRepository extends BaseRepository<Sessao, Long> {
	Optional<Sessao> findByToken(String token);
}
