package es.masanz.ut7.ejercicio2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;

public class TimeWidget {

    public static final String URL = "https://www.aemet.es/xml/municipios_h/localidad_h_31201.xml";

    public String obtenerInfo() {

        LocalDateTime ahora = LocalDateTime.now();

        String anioMesDia = ahora.getYear()+"-"+(ahora.getMonthValue() > 9 ? ahora.getMonthValue() : "0" + ahora.getMonthValue())+"-"+(ahora.getDayOfMonth() > 9 ? ahora.getDayOfMonth() : "0"+ahora.getDayOfMonth());
        String horaActual = ahora.getHour() > 9 ? ""+ahora.getHour() : "0"+ahora.getHour();

        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String estado = null;
        Elements elements = doc.selectXpath("//dia[@fecha='"+anioMesDia+"']/estado_cielo[@periodo='"+horaActual+"']");
        for (Element element : elements) {
            estado = element.attr("descripcion");
        }

        String temperatura = null;
        elements = doc.selectXpath("//dia[@fecha='"+anioMesDia+"']/temperatura[@periodo='"+horaActual+"']");
        for (Element element : elements) {
            temperatura = element.text();
        }

        String humedadRel = null;
        elements = doc.selectXpath("//dia[@fecha='"+anioMesDia+"']/humedad_relativa[@periodo='"+horaActual+"']");
        for (Element element : elements) {
            humedadRel = element.text();
        }

        /*
********** INFO CLIMA PAMPLONA **********
Fecha: 2026-03-30 | Hora: 11:00
Estado: Despejado
Temperatura: 18°C
Humedad: 55%
*****************************************
         */

        StringBuilder sb = new StringBuilder();
        sb.append("********** INFO CLIMA PAMPLONA **********\n");
        sb.append("Fecha: ");
        sb.append(anioMesDia);
        sb.append(" | Hora: ");
        sb.append(horaActual);
        sb.append("\n");
        sb.append("Estado: ");
        sb.append(estado);
        sb.append("\n");
        sb.append("Temperatura: ");
        sb.append(temperatura);
        sb.append("\n");
        sb.append("Humedad Rel.: ");
        sb.append(humedadRel);
        sb.append("\n");
        sb.append("*****************************************\n");
        return sb.toString();
    }


}
