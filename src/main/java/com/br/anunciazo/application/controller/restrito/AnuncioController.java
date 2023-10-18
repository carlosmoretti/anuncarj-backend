package com.br.anunciazo.application.controller.restrito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.anunciazo.application.controller.BaseController;
import com.br.anunciazo.application.controller.dto.SessaoDTO;
import com.br.anunciazo.application.model.Anuncio;
import com.br.anunciazo.application.model.Imagem;
import com.br.anunciazo.application.service.AnuncioService;
import com.br.anunciazo.application.service.ImagemService;
import com.br.anunciazo.application.service.ServiceBase;
import com.br.anunciazo.application.service.SessaoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@RestController
@RequestMapping(value = "/restrito/anuncio")
public class AnuncioController extends BaseController<Anuncio, Long> {
	
	private final ObjectMapper objectMapper;
	private final ImagemService imagemService;
	private final AnuncioService anuncioService;
	private final SessaoService sessaoService;
	
	@Autowired
	public AnuncioController(final ObjectMapper objectMapper,
			final ImagemService imagemService,
			final AnuncioService anuncioService,
			final SessaoService sessaoService) {
		this.objectMapper = objectMapper;
		this.anuncioService = anuncioService;
		this.imagemService = imagemService;
		this.sessaoService = sessaoService;
	}	

	@PostMapping(value = "/com-imagem")
	public Anuncio incluir(@RequestPart MultipartFile image, @RequestPart String content) throws IOException {
		Anuncio anuncio = this.objectMapper.readValue(content, Anuncio.class);
		anuncio = this.anuncioService.add(anuncio);
		
		Imagem imagem = new Imagem();
		imagem.setAnexo(image.getBytes());
		imagem.setAnuncio(anuncio);
		
		imagem = this.imagemService.add(imagem);
		return anuncio;
	}
	
	@GetMapping(value = "/imagem/{idAnuncio}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImagem(@PathVariable Long idAnuncio) {
		Optional<Imagem> imagem = this.imagemService.findByAnuncio_Id(idAnuncio);
		
		if(imagem.isPresent())
			return imagem.get().getAnexo();
		
		return null;
	}

	@Override
	public ServiceBase<Anuncio, Long> getService() {
		return this.anuncioService;
	}

	@Override
	public Specification<Anuncio> montarFiltro(MultiValueMap<String, Object> filter) {
		Optional<SessaoDTO> sessao = this.sessaoService.obterSessao();
		Point localizacaoUsuario = new Point(sessao.get().getX().doubleValue(), sessao.get().getY().doubleValue());
		return new Specification<Anuncio>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Anuncio> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				Path<Double> latitude = root.get("x");
				Path<Double> longitude = root.get("y");
				
				if(Objects.nonNull(filter.getFirst("distancia"))) {
					Expression<Double> distancia = criteriaBuilder.function("dbo.CALCULAR_DISTANCIA", Double.class, latitude, longitude, 
							criteriaBuilder.literal(localizacaoUsuario.getX()), criteriaBuilder.literal(localizacaoUsuario.getY()));
					
					predicates.add(criteriaBuilder.lessThan(distancia, Double.valueOf(filter.getFirst("distancia").toString())));
				}
				
				if(Objects.nonNull(filter.getFirst("titulo")))
					predicates.add(criteriaBuilder.like(root.get("titulo"), "%" + filter.getFirst("titulo") + "%"));
				
				if(Objects.nonNull(filter.getFirst("categoria")))
					predicates.add(criteriaBuilder.equal(root.get("categoria").get("id"), filter.getFirst("categoria")));
				
				if(Objects.nonNull(filter.getFirst("avaliacao")))
					predicates.add(criteriaBuilder.equal(root.get("estrelas"), filter.getFirst("avaliacao")));
				
				Order order = criteriaBuilder.asc(criteriaBuilder.function("dbo.CALCULAR_DISTANCIA", Double.class, latitude, longitude, 
						criteriaBuilder.literal(localizacaoUsuario.getX()), criteriaBuilder.literal(localizacaoUsuario.getY())));
				
				query.orderBy(order);
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
}
