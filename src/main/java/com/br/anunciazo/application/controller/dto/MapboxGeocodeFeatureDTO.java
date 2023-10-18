package com.br.anunciazo.application.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapboxGeocodeFeatureDTO {
	private List<BigDecimal> center;
}
