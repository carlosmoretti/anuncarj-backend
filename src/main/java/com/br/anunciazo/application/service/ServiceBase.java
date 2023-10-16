package com.br.anunciazo.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ServiceBase<T, L> {
	public List<T> getAll();
	public Optional<T> find(L id);
	public Page<T> get(Pageable pageable, Specification<T> specification);
	public T get(L id);
	
	public T add(T obj);
	public T update(T obj);
	public void delete(T obj);
}
