package com.br.anunciazo.application.config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Configuration;

import com.br.anunciazo.application.model.Anuncio;
import com.br.anunciazo.application.model.Categoria;
import com.br.anunciazo.application.model.Imagem;
import com.br.anunciazo.application.service.AnuncioService;
import com.br.anunciazo.application.service.CategoriaService;
import com.br.anunciazo.application.service.ImagemService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApplicationConfig {
		
	private final AnuncioService anuncioService;
	private final CategoriaService categoriaService;
	private final ImagemService imagemService;
	
	public ApplicationConfig(final AnuncioService anuncioService,
			final CategoriaService categoriaService,
			final ImagemService imagemService) {
		this.anuncioService = anuncioService;
		this.categoriaService = categoriaService;
		this.imagemService = imagemService;
	}
	
	@PostConstruct
	public void postConstruct() throws IOException {
		
		for(int i = 0; i < 5; i++) {
			Categoria categoria = new Categoria();
			categoria.setNome("Teste " + i);
			this.categoriaService.add(categoria);
		}
		
		for(int i = 0; i < 450; i++) {
			Anuncio anuncio = new Anuncio();
			anuncio.setTitulo("Teste " + i);
			anuncio.setEstrelas(3);
			anuncio.setX(-22.837348);
			anuncio.setY(-43.047517);
			anuncio.setCategoria(categoriaService.getAll().get(0));
			anuncio.setCep("21820096");
			anuncio.setEndereco("Rua Rio da Prata");
			anuncio.setNumero("481");
			anuncio.setComplemento("N/A");
			anuncio = anuncioService.add(anuncio);
			
			Imagem imagem = new Imagem();
			imagem.setAnuncio(anuncio);
			
			URL url = new URL("https://picsum.photos/200/120");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage img = ImageIO.read(url);
			ImageIO.write(img, "jpg", baos);
			imagem.setAnexo(baos.toByteArray());
			imagemService.add(imagem);
			
		}
		
		log.info("Concluiu seed");
	}
	
}
