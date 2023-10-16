package com.br.anunciazo.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.br.anunciazo.application.repository.BaseRepository;
import com.br.anunciazo.application.service.ServiceBase;

public abstract class ServiceBaseImpl<T, L> implements ServiceBase<T, L> {
	
	public abstract BaseRepository<T, L> getRepository();

	@Override
	public List<T> getAll() {
		return this.getRepository().findAll();
	}
	
	@Override
	public T get(L id) {
		Optional<T> obj = this.getRepository().findById(id);
		return obj.get();
	}

	@Override
	public Optional<T> find(L id) {
		return this.getRepository().findById(id);
	}

	@Override
	public Page<T> get(Pageable pageable, Specification<T> specification) {
		return this.getRepository().findAll(specification, pageable);
	}

	@Override
	public T add(T obj) {
		return this.getRepository().save(obj);
	}

	@Override
	public T update(T obj) {
		return this.getRepository().save(obj);
	}

	@Override
	public void delete(T obj) {
		this.getRepository().delete(obj);
	}

}
