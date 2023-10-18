package com.br.anunciazo.application.service;

import java.util.Optional;

import com.br.anunciazo.application.controller.dto.SessaoDTO;

public interface SessaoService {
	Optional<SessaoDTO> obterSessao();
}
