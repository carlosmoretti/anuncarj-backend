package com.br.anunciazo.application.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SESSAO")
@Getter
@Setter
public class Sessao {
	
	@Column(name = "SESS_CD_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "SESS_VL_COORD_X", columnDefinition = "decimal(10,6)")
	private Double x;
	
	@Column(name = "SESS_VL_COORD_Y", columnDefinition = "decimal(10,6)")
	private Double y;
	
	@Column(name = "SESS_TX_TOKEN", unique = true)
	private String token;
}
