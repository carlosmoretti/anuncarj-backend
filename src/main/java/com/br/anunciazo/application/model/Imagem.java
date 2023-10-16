package com.br.anunciazo.application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "imagem")
public class Imagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "imag_cd_id")
	private Long id;
	
	@Column(name = "imag_vl_blob")
	@Lob
	private byte[] anexo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anun_cd_id")
	private Anuncio anuncio;
}
