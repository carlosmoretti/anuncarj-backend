package com.br.anunciazo.application.handler;

import java.nio.file.AccessDeniedException;

public class NaoAutorizadoException extends AccessDeniedException {

	public NaoAutorizadoException() {
		super("Acesso não autorizado.");
	}

}
