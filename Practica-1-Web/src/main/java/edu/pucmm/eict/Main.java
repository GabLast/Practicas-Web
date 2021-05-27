package edu.pucmm.eict;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

//import static org.apache.commons.validator.routines.UrlValidator.ALLOW_ALL_SCHEMES;

public class Main {
    public static void main(String[] args) {
        System.out.println("Practica #1 - Programación Web: Cliente HTTP");
        System.out.println("Estudiante: Gabriel José Marte Lantigua | Matrícula: 2017-0388 | ID: 1013-1315\n");

        System.out.println("Digite una URL válida a inspeccionar:");
        Scanner input = null;
        String url = null;
        UrlValidator urlValidator = new UrlValidator(/*ALLOW_ALL_SCHEMES*/);
        do {
            input = new Scanner(System.in);
            url = input.nextLine();
            if(!urlValidator.isValid(url))
            {
                System.out.println("Su URL no es válida");
            }
        }while (!urlValidator.isValid(url));

        Document documento = null;

        try {
            documento = Jsoup.connect(url).get();
            System.out.println("\nURL Digitada:");
            System.out.println(url);
            System.out.println("\nTítulo del documento:");
            System.out.println(documento.title());
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

//      a) Indicar la cantidad de lineas del recurso retornado.
        System.out.println("a) Cantidad de lineas del recurso retornado:");
        try {
            System.out.println(Jsoup.connect(url).execute().body().split("\n").length);
        }catch (IOException e) {
            e.printStackTrace();
        }


//      b) Indicar la cantidad de párrafos (p) que contiene el documento HTML.
        System.out.println("\nb) Indicar la cantidad de párrafos (p) que contiene el documento HTML:");
        Elements paragraphs = documento.getElementsByTag("p");
        System.out.println(paragraphs.size());


//      c) Indicar la cantidad de imágenes (img) dentro de los párrafos que
//         contiene el archivo HTML.
        System.out.println("\nc) Indicar la cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML:");
        Elements imgs = documento.getElementsByTag("img");
        System.out.println("Total de imágenes: " + imgs.size());
        System.out.println("Imágenes dentro de <p>: " + documento.select("p img").size());


//      d) indicar la cantidad de formularios (form) que contiene el HTML por
//         categorizando por el método implementado POST o GET.
        System.out.println("\nd) Indicar la cantidad de formularios (form) que contiene el HTML por categorizando por el método implementado POST o GET:");
        Elements forms = documento.getElementsByTag("form");
        Elements gets = documento.select("form[method=GET]");
        Elements posts = documento.select("form[method=POST]");
        System.out.println("Total de forms: " + forms.size());
        System.out.println("Total de forms con método GET: " + gets.size());
        System.out.println("Total de forms con método POST: " + posts.size());


//      e) Para cada formulario mostrar los campos del tipo input y su
//         respectivo tipo que contiene en el documento HTML.
        System.out.println("\ne) Para cada formulario mostrar los campos del tipo input y su respectivo tipo que contiene en el documento HTML:");
        int i = 0;
        for(Element formu : forms)
        {
            for(Element inputTag : formu.getElementsByTag("input"))
            {
                System.out.println("Input "+ ++i + ": Tipo = " + inputTag.attr("type") + " | Nombre = " + inputTag.attr("name"));
            }
        }

//      f) Para cada formulario parseado, identificar que el método de envío
//        del formulario sea POST y enviar una petición al servidor con el
//        parámetro llamado asignatura y valor practica1 y un header llamado
//        matricula con el valor correspondiente a matrícula asignada. Debe
//        mostrar la respuesta por la salida estándar.

        System.out.println("\nf) \tPara cada formulario parseado, identificar que el método de envío");
        System.out.println("\tdel formulario sea POST y enviar una petición al servidor con el");
        System.out.println("\tparámetro llamado asignatura y valor practica1 y un header llamado");
        System.out.println("\tmatricula con el valor correspondiente a matrícula asignada. Debe");
        System.out.println("\tmostrar la respuesta por la salida estándar:");

        for(Element formu : forms)
        {
            if(formu.attr("method").equalsIgnoreCase("POST"))
            {

                try {

                    Document request;
                    request = Jsoup.connect(formu.absUrl("action")).data("asignatura", "practica1").header("matricula", "2017-0388").post();

                    System.out.println("\n*********************************************************************************");
                    System.out.println("\n" + request.body());
                    System.out.println("\n*********************************************************************************");

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
