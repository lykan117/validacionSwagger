package com.yamlRestructur.yaml;

import com.yamlRestructur.yaml.clases.SwaggerParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YamlApplication {

	public static void main(String[] args) {
		SpringApplication.run(YamlApplication.class, args);
		SwaggerParserService parserService = new SwaggerParserService();
		parserService.readSwaggerFiles("C:\\Users\\edson\\Documents\\yamlsPrueba");

		//SwaggerReader reader = new SwaggerReader();
		//reader.readSwaggerFiles("C:\\Users\\edson\\Documents\\yamlsPrueba"); // Reemplaza con la ruta a tu directorio de Swagger
	}

}
