package es.masanz.ut7.ejercicio3;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinFreemarker;

public class WeatherServer {

    public static void execute(){

        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinFreemarker());
        }).start(8080);

        app.get("/", WeatherController::index);


    }

}
