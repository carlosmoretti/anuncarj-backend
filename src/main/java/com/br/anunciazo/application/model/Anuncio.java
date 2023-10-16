package com.br.anunciazo.application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "anuncio")
@Getter
@Setter
public class Anuncio {
	
	@Id
	@Column(name = "anun_cd_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "anun_tx_titulo")
	private String titulo;
	
	@Column(name = "anun_vl_qtd_estrelas")
	private Integer estrelas;
	
	@Column(name = "anun_vl_localizacao_x", columnDefinition = "decimal(10,6)")
	private Double x;
	
	@Column(name = "anun_vl_localizacao_y", columnDefinition = "decimal(10,6)")
	private Double y;
	
	@ManyToOne
	@JoinColumn(name = "cate_cd_id")
	private Categoria categoria;
	
	@Transient
	private Double distancia;
	
	@Column(name = "anun_tx_endereco")
	private String endereco;
	
	@Column(name = "anun_tx_numero")
	private String numero;
	
	@Column(name = "anun_tx_complemento")
	private String complemento;
	
	@Column(name = "anun_tx_cep")
	private String cep;
	
	@Column(name = "anun_tx_whatsapp")
	private String whatsapp;
	
	@Column(name = "anun_tx_instagram")
	private String instagram;
	
	@Column(name = "anun_tx_facebook")
	private String facebook;
	
	@Column(name = "anun_tx_descricao")
	private String descricao;
}
