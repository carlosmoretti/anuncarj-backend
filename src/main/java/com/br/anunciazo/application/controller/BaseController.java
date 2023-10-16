package com.br.anunciazo.application.controller;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.anunciazo.application.service.ServiceBase;

public abstract class BaseController<T, L> {
	public abstract ServiceBase<T, L> getService();
	
	public abstract Specification<T> montarFiltro(MultiValueMap<String, Object> filter);
	
	@GetMapping(value = "/page/{page}/size/{size}")
	public Page<T> getPage(@PathVariable int page, @PathVariable int size, 
			@RequestParam(required = false) String prop, 
			@RequestParam(required = false) Direction direction,
			@RequestParam MultiValueMap<String, Object> filter) {
		
		Sort sort = null;
		Pageable pageable = PageRequest.of(page, size);
		
		if(Objects.nonNull(prop)) {
			sort = Sort.by(Objects.isNull(direction) ? Direction.ASC : direction, prop);
			pageable = PageRequest.of(page, size, sort);
		}
		
		return this.getService().get(pageable, montarFiltro(filter));
	}
	
	@GetMapping(value = "/{id}")
	public T get(@PathVariable L id) {
		return this.getService().get(id);
	}
	
	@PostMapping
	public T add(@RequestBody T obj) {
		return this.getService().add(obj);
	}
	
	@PutMapping
	public T update(@RequestBody T obj) {
		return this.getService().update(obj);
	}
	
	@DeleteMapping
	public void delete(@RequestBody T obj) {
		this.getService().delete(obj);
	}
}
