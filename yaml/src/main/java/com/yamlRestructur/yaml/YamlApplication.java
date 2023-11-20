package com.yamlRestructur.yaml;

import com.yamlRestructur.yaml.clases.SwaggerParserServiceExcel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YamlApplication {

	public static void main(String[] args) {
		SpringApplication.run(YamlApplication.class, args);
		//SwaggerParserService parserService = new SwaggerParserService();
		//parserService.readSwaggerFiles("C:\\Users\\edson\\Documents\\yamlsPrueba");

		SwaggerParserServiceExcel swaggerParserServiceExcel= new SwaggerParserServiceExcel();
		swaggerParserServiceExcel.writeSwaggerInfoToExcel("C:\\Users\\edson\\Documents\\yamlsPrueba","C:\\Users\\edson\\Documents\\yamlsPrueba\\excel.xlsx");

		//SwaggerReader reader = new SwaggerReader();
		//reader.readSwaggerFiles("C:\\Users\\edson\\Documents\\yamlsPrueba"); // Reemplaza con la ruta a tu directorio de Swagger
	}

}
