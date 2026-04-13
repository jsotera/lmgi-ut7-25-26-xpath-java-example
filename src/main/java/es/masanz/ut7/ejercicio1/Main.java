package es.masanz.ut7.ejercicio1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static final String BASE_PATH = "doc/data/";
    public static final String FILE_NAME = "ejemplo.txt";

    public static final String URL_BASE = "https://www.w3schools.com";
    public static final String RELATIVE_URL = URL_BASE + "/html/";

    public static StringBuilder sb;

    public static void main(String[] args) {
        sb = new StringBuilder();
        sb.append("<indice tema=\"HTML\">\n");
        int numTabs = 0;

        String url = RELATIVE_URL + "default.asp";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int cont = 0;
        Elements elements = doc.selectXpath("//div[@id='leftmenuinnerinner']/a");

        for (Element element : elements) {
            sb.append(procesarElemento(element, numTabs, doc));
        }
        sb.append("</indice>\n");
        escribir(FILE_NAME, sb.toString(), false);
    }

    public static String procesarElemento(Element elemento, int numTabs, Document doc){
        StringBuilder sb = new StringBuilder();

        numTabs++;
        sb.append("\t".repeat(numTabs));
        sb.append("<h"+numTabs+" id=\"1\">\n");

        // TITULO
        numTabs++;
        sb.append("\t".repeat(numTabs));
        sb.append("<titulo>");
        sb.append(elemento.text());
        sb.append("</titulo>");
        sb.append("\n");
        numTabs--;

        //ENLACE
        numTabs++;
        sb.append("\t".repeat(numTabs));
        sb.append("<enlace>");
        String enlaceCompleto = "";
        if(elemento.attr("href").startsWith("/")){
            sb.append(URL_BASE);
            enlaceCompleto = URL_BASE;
        } else {
            sb.append(RELATIVE_URL);
            enlaceCompleto = RELATIVE_URL;
        }
        sb.append(elemento.attr("href"));
        sb.append("</enlace>");
        sb.append("\n");
        numTabs--;


        String enlace = elemento.attr("href");

        enlaceCompleto = enlaceCompleto + enlace;

        Connection connection = Jsoup.connect(enlaceCompleto);
        try {
            int codigoEstado = connection.execute().statusCode();
            if(codigoEstado>=200 && codigoEstado<200){
                System.out.println("SI procesar enlace");
            }
            System.out.println(codigoEstado);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements hijitos = doc.selectXpath("//div[@id='leftmenuinnerinner']"+"/div".repeat(numTabs)+"/a[@href='"+enlace+"']/../a");
        for (Element hijito : hijitos) {
            // TODO: CONTINUAR AQUI LA RECURSIVIDAD
            sb.append(procesarElemento(hijito, numTabs, doc));
        }

        sb.append("\t".repeat(numTabs));
        sb.append("</h"+numTabs+">\n");
        numTabs--;

        return sb.toString();
    }

    public static void main2(String[] args) throws IOException {
        String url = "https://www.aemet.es/xml/municipios_h/localidad_h_31201.xml";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.selectXpath("//prediccion/dia[@fecha='2026-03-30']");
        for (Element element : elements) {
            System.out.println(element.text());
        }
        escribir(FILE_NAME, elements.text(), true);
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