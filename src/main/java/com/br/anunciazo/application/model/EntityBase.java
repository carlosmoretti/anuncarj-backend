package com.br.anunciazo.application.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EntityBase {
	
	public abstract UUID getUuid();
	public abstract void setUuid(UUID id);
	

}
