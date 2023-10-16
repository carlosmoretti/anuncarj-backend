package com.br.anunciazo.application.service;

import java.util.Optional;

import com.br.anunciazo.application.model.Sessao;

public interface SessaoService extends ServiceBase<Sessao, Long> {
	Optional<Sessao> findByToken(String token);
	Optional<Sessao> obterSessao();
}
