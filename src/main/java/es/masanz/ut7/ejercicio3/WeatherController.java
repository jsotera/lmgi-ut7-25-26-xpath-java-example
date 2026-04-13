package es.masanz.ut7.ejercicio3;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static es.masanz.ut7.ejercicio3.WeatherScraping.BASE_PATH;

public class WeatherController {

    public static void index(@NotNull Context context) {
        Map<String, Object> model = new HashMap<>();

        model.put("tarjetasPorDia", obtenerTarjetitas());

        context.render("/templates/index.ftl", model);
    }

    public static Map<String, List<String>> obtenerTarjetitas(){

        String clave = null;
        Map<String, List<String>> mapa = new HashMap<>();
        List<String> tarjetas = new ArrayList<>();
        File baseFile = new File(BASE_PATH);
        File[] files = baseFile.listFiles();
        for (File file : files) {
            clave = file.getName().substring(0, 10);
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String linea = br.readLine();
                while(linea!=null){
                    sb.append(linea);
                    sb.append("\n");
                    linea = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            tarjetas = mapa.get(clave);
            if(tarjetas == null){
                tarjetas = new ArrayList<>();
                mapa.put(clave, tarjetas);
            }
            tarjetas.add(sb.toString());
        }

        return mapa;
    }

}
