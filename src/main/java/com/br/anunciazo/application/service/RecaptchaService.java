package com.br.anunciazo.application.service;

import com.br.anunciazo.application.handler.NaoAutorizadoException;

public interface RecaptchaService {
	boolean verificar(String token) throws NaoAutorizadoException;
}
