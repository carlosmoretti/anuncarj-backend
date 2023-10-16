package com.br.anunciazo.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.anunciazo.application.model.Categoria;
import com.br.anunciazo.application.repository.BaseRepository;
import com.br.anunciazo.application.repository.CategoriaRepository;
import com.br.anunciazo.application.service.CategoriaService;

@Service
public class CategoriaServiceImpl extends ServiceBaseImpl<Categoria, Long> implements CategoriaService {
	
	final CategoriaRepository categoriaRepository;
	
	@Autowired
	public CategoriaServiceImpl(final CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@Override
	public BaseRepository<Categoria, Long> getRepository() {
		return categoriaRepository;
	}
	
}
