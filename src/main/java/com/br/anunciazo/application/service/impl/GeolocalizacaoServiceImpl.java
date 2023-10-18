package com.br.anunciazo.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.br.anunciazo.application.controller.dto.GeolocalizacaoDTO;
import com.br.anunciazo.application.controller.dto.MapboxGeocodeDTO;
import com.br.anunciazo.application.controller.dto.MapboxGeocodeFeatureDTO;
import com.br.anunciazo.application.model.Anuncio;
import com.br.anunciazo.application.service.GeolocalizacaoService;

@Service
public class GeolocalizacaoServiceImpl implements GeolocalizacaoService {
	
	@Value("${mapbox.api}")
	private String mapboxToken;
	
	double SEMI_MAJOR_AXIS_MT = 6378137;
	double SEMI_MINOR_AXIS_MT = 6356752.314245;
	double FLATTENING = 1 / 298.257223563;
	double ERROR_TOLERANCE = 1e-12;
	
	@Override
	public Double obterDistanciaEmMetros(Point posicaoUsuario, Anuncio anuncio) {
		double U1 = Math.atan((1 - FLATTENING) * Math.tan(Math.toRadians(anuncio.getX())));
	    double U2 = Math.atan((1 - FLATTENING) * Math.tan(Math.toRadians(posicaoUsuario.getX())));

	    double sinU1 = Math.sin(U1);
	    double cosU1 = Math.cos(U1);
	    double sinU2 = Math.sin(U2);
	    double cosU2 = Math.cos(U2);

	    double longitudeDifference = Math.toRadians(posicaoUsuario.getY() - anuncio.getY());
	    double previousLongitudeDifference;

	    double sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;

	    do {
	        sinSigma = Math.sqrt(Math.pow(cosU2 * Math.sin(longitudeDifference), 2) +
	            Math.pow(cosU1 * sinU2 - sinU1 * cosU2 * Math.cos(longitudeDifference), 2));
	        cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * Math.cos(longitudeDifference);
	        sigma = Math.atan2(sinSigma, cosSigma);
	        sinAlpha = cosU1 * cosU2 * Math.sin(longitudeDifference) / sinSigma;
	        cosSqAlpha = 1 - Math.pow(sinAlpha, 2);
	        cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
	        if (Double.isNaN(cos2SigmaM)) {
	            cos2SigmaM = 0;
	        }
	        previousLongitudeDifference = longitudeDifference;
	        double C = FLATTENING / 16 * cosSqAlpha * (4 + FLATTENING * (4 - 3 * cosSqAlpha));
	        longitudeDifference = Math.toRadians(anuncio.getY() - posicaoUsuario.getY()) + (1 - C) * FLATTENING * sinAlpha *
	            (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2))));
	    } while (Math.abs(longitudeDifference - previousLongitudeDifference) > ERROR_TOLERANCE);

	    double uSq = cosSqAlpha * (Math.pow(SEMI_MAJOR_AXIS_MT, 2) - Math.pow(SEMI_MINOR_AXIS_MT, 2)) / Math.pow(SEMI_MINOR_AXIS_MT, 2);

	    double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
	    double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));

	    double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2))
	        - B / 6 * cos2SigmaM * (-3 + 4 * Math.pow(sinSigma, 2)) * (-3 + 4 * Math.pow(cos2SigmaM, 2))));

	    double distanceMt = SEMI_MINOR_AXIS_MT * A * (sigma - deltaSigma);
	    return distanceMt / 1000;
	}

	@Override
	public Double obterDistanciaEmMetros(Point posicaoUsuario, Point posicaoAnuncio) {
		Anuncio anuncio = new Anuncio();
		anuncio.setX(posicaoAnuncio.getX());
		anuncio.setY(posicaoAnuncio.getY());
		return this.obterDistanciaEmMetros(posicaoUsuario, anuncio);
	}
	
	@Override
	public GeolocalizacaoDTO consultar(String endereco) {
		RestTemplate restTemplate = new RestTemplate();
		
		String url = new StringBuilder()
				.append("https://api.mapbox.com/geocoding/v5/mapbox.places/")
				.append(endereco)
				.append(".json")
				.append("?access_token=")
				.append(this.mapboxToken)
				.toString();
		
		ResponseEntity<MapboxGeocodeDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, MapboxGeocodeDTO.class);
		
		if(response.hasBody()) {
			if(!CollectionUtils.isEmpty(response.getBody().getFeatures())) {
				List<MapboxGeocodeFeatureDTO> dto = response.getBody().getFeatures();
				MapboxGeocodeFeatureDTO primeiroItem = dto.get(0);
				return new GeolocalizacaoDTO(primeiroItem.getCenter().get(1), primeiroItem.getCenter().get(0));
			}
		}
		
		return null;
	}
	
}
