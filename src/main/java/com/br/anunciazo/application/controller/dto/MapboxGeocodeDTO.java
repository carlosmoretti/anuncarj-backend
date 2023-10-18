package com.br.anunciazo.application.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapboxGeocodeDTO {
	private List<MapboxGeocodeFeatureDTO> features;
}
