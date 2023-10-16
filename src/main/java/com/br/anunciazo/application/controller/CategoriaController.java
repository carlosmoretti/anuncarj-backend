package com.br.anunciazo.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.anunciazo.application.model.Categoria;
import com.br.anunciazo.application.service.CategoriaService;

@RestController
@RequestMapping(value = "/restrito/categoria")
public class CategoriaController {
	
	private final CategoriaService categoriaService;
	
	public CategoriaController(final CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}
	
	@GetMapping
	public List<Categoria> get() {
		return this.categoriaService.getAll();
	}
}
