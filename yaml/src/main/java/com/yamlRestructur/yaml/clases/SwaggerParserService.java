package com.yamlRestructur.yaml.clases;
//SwaggerParserService

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.OpenAPIV3Parser;

import java.io.File;
import java.util.List;

public class SwaggerParserService {

    public void readSwaggerFiles(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".yaml") || name.toLowerCase().endsWith(".yml"));

            for (File file : files) {
                OpenAPI openAPI = new OpenAPIV3Parser().read(file.getAbsolutePath());
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
        }
    }

    private String getBasePath(OpenAPI openAPI) {
        List<Server> servers = openAPI.getServers();
        if (!servers.isEmpty()) {
            return servers.get(0).getUrl();
        }
        return "";
    }


}
