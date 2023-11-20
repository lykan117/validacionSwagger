package com.yamlRestructur.yaml.clases;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwaggerParserServiceExcel {

    public void writeSwaggerInfoToExcel(String directoryPath, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Swagger Info");
            List<String> pathsList = new ArrayList<>();
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Title");
            headerRow.createCell(1).setCellValue("BasePath");
            headerRow.createCell(2).setCellValue("Path");
            headerRow.createCell(3).setCellValue("UniqueBasepath");

            File directory = new File(directoryPath);

            if (directory.isDirectory()) {
                File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".yaml") || name.toLowerCase().endsWith(".yml"));

                for (File file : files) {
                    OpenAPI openAPI = new OpenAPIV3Parser().read(file.getAbsolutePath());
                    Info info = openAPI.getInfo();
                    String title = info.getTitle();
                    String basePath = getBasePath(openAPI);
                    Paths paths = openAPI.getPaths();
                    pathsList.clear();
                    System.out.println("paths de los for:  " + paths.keySet());
                    int aux=0;
                    for (String path : paths.keySet()) {
                        aux++;
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(title);
                        row.createCell(1).setCellValue(basePath);
                        row.createCell(2).setCellValue(path);
                        pathsList.add(path);


                        if(paths.keySet().size()==aux){
                            System.out.println("Este es el ultimo "+ " paths.keySet().size(): " + paths.keySet().size() + " rowNum: "+ aux);
                            String result = removeLastLetters(findCommonPrefix(pathsList));
                            if (result.equals("/")){
                                result="requiere UID";
                            }
                            row.createCell(3).setCellValue(removeTrailingSlash(result));


                        }else{
                            System.out.println("not now " + " paths.keySet().size(): " + paths.keySet().size() + " rowNum: "+ aux);
                        }


                    }
                    System.out.println("***Console:: " + title+ " tamaño "+pathsList.size()+" ejemplo solo uno: "+ pathsList.get(1)+
                            " all: " + pathsList);
                }


                try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                    workbook.write(fileOut);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String findCommonPrefix(List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            return "";
        }

        // Obtén el primer camino
        String firstPath = paths.get(0);

        // Encuentra la última barra en el primer camino
        int lastSlashIndex = firstPath.lastIndexOf('/');

        // Si no hay barra, devuelve la cadena completa
        if (lastSlashIndex < 0) {
            return firstPath;
        }

        // Extrae el prefijo antes de la última barra
        String prefix = firstPath.substring(0, lastSlashIndex);

        // Verifica si el prefijo es un prefijo común para todos los caminos
        for (String path : paths) {
            if (!path.startsWith(prefix)) {
                // Si no es un prefijo común, ajusta el prefijo
                prefix = getCommonPrefix(prefix, path);
            }
        }

        return prefix;
    }

    // Función para encontrar el prefijo común entre dos cadenas
    private static String getCommonPrefix(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        int commonLength = 0;

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
            commonLength++;
        }

        return commonLength > 0 ? str1.substring(0, commonLength) : "";
    }

    public static String removeLastLetters(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Encuentra la última barra en el string
        int lastSlashIndex = input.lastIndexOf('/');

        // Si no hay barra, devuelve el string original
        if (lastSlashIndex < 0) {
            return input;
        }

        // Elimina las letras después de la última barra
        return input.substring(0, lastSlashIndex + 1);
    }
    private String getBasePath(OpenAPI openAPI) {
        List<Server> servers = openAPI.getServers();
        if (!servers.isEmpty()) {
            return servers.get(0).getUrl();
        }
        return "";
    }
    public static String removeTrailingSlash(String input) {
        if (input != null && input.endsWith("/")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }

}
