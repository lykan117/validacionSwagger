package com.yamlRestructur.yaml.clases;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.OpenAPIV3Parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SwaggerReader {

    public void readSwaggerFiles(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".yaml") || name.toLowerCase().endsWith(".yml") || name.toLowerCase().endsWith(".json"));

            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(".json")) {
                    OpenAPI openAPI = parseJsonSwagger(file.getAbsolutePath());
                    processSwagger(openAPI);
                } else {

                    OpenAPI openAPI = new OpenAPIV3Parser().read(file.getAbsolutePath());

                    processSwagger(openAPI);
                }
            }
        }
    }

    private void processSwagger(OpenAPI openAPI) {
        Info info = openAPI.getInfo();
        String title = info.getTitle();
        String basePath = getBasePath(openAPI);
        Paths paths = openAPI.getPaths();

        System.out.println("Title: " + title);
        System.out.println("BasePath: " + basePath);

        for (String path : paths.keySet()) {
            System.out.println("Path: " + path);
        }
    }

    private String getBasePath(OpenAPI openAPI) {
        List<Server> servers = openAPI.getServers();
        if (!servers.isEmpty()) {
            return servers.get(0).getUrl();
        }
        return "";
    }

    private OpenAPI parseJsonSwagger(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OpenAPI openAPI = objectMapper.readValue(new File(filePath), OpenAPI.class);
            return openAPI;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }





}
