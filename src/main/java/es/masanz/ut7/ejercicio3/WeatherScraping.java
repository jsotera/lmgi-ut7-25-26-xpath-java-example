package es.masanz.ut7.ejercicio3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class WeatherScraping {


    public static final String BASE_PATH = "doc/timeline/";
    public static final String URL = "https://www.aemet.es/xml/municipios_h/localidad_h_31201.xml";

    public static void execute(){

        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            System.out.println("Problemas con la conexion.");
            return;
        }

        String fecha = null;
        String hora = null;
        Elements dias = doc.selectXpath("//prediccion/dia");
        for (Element dia : dias) {
            fecha = dia.attr("fecha");
            Elements estados = doc.selectXpath("//prediccion/dia[@fecha='"+fecha+"']/estado_cielo");
            for (Element estado : estados) {
                hora = estado.attr("periodo");

                String descripcion = estado.attr("descripcion");
                String temperatura = null;
                String humedadRel = null;

                Elements elements = doc.selectXpath("//dia[@fecha='" + fecha + "']/temperatura[@periodo='" + hora + "']");
                for (Element element : elements) {
                    temperatura = element.text();
                }
                elements = doc.selectXpath("//dia[@fecha='" + fecha + "']/humedad_relativa[@periodo='" + hora + "']");
                for (Element element : elements) {
                    humedadRel = element.text();
                }

                if(descripcion==null || temperatura==null || humedadRel==null){
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("<div class=\"col-12 col-md-6 col-lg-3\">");
                sb.append("\n");
                sb.append("    <div class=\"card weather-card text-center p-4\">");
                sb.append("\n");
                sb.append("        <div class=\"text-muted mb-2\">"+hora+":00</div>");
                sb.append("\n");
                if(descripcion.contains("lluvia")){
                    sb.append("        <div class=\"weather-icon text-primary\"><i class=\"fas fa-cloud-showers-heavy\"></i></div>");
                } else if (descripcion.contains("nuboso")) {
                    sb.append("        <div class=\"weather-icon text-secondary\"><i class=\"fas fa-cloud\"></i></div>");
                } else {
                    sb.append("        <div class=\"weather-icon text-warning\"><i class=\"fas fa-sun\"></i></div>");
                }
                sb.append("\n");
                sb.append("        <div class=\"status-text\">"+descripcion+"</div>");
                sb.append("\n");
                sb.append("        <div class=\"temp-display\">"+temperatura+"°C</div>");
                sb.append("\n");
                sb.append("        <div class=\"humidity\"><i class=\"fas fa-droplet\"></i> Humedad: "+humedadRel+"%</div>");
                sb.append("\n");
                sb.append("    </div>");
                sb.append("\n");
                sb.append("</div>");
                sb.append("\n");

                escribir(fecha+"-"+hora, sb.toString(), false);
            }
        }

    }

    private static void escribir(String nombreFichero, String texto, boolean mantenerTexto) {
        PrintWriter file = null;
        try {
            file = new PrintWriter(new BufferedWriter(new FileWriter(BASE_PATH+nombreFichero, mantenerTexto)));
            file.println(texto);
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
