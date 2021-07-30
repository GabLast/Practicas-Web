package edu.pucmm.eict;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Listado de estudiantes
        HttpResponse listadoEstudiantes = Unirest.get("http://localhost:7000/api/estudiante/")
                .header("accept", "application/json")
                .asObject(new GenericType<List<Estudiante>>() {
                });
        System.out.println("\nListado de Estudiantes");
        System.out.println("HTTP Status: " + listadoEstudiantes.getStatus());
        System.out.println("HTTP Message: " + listadoEstudiantes.getBody().toString());


        //Consultar Estudiantes
        System.out.println("\nConsultar Estudiante");
        System.out.println("Matricula del estudiante: ");
        Scanner in = new Scanner(System.in);
        String matricula = in.nextLine();


        HttpResponse consultaEstudiante = Unirest.get("http://localhost:7000/api/estudiante/" + matricula)
                .header("accept", "application/json")
                .asObject(Estudiante.class);

        System.out.println("HTTP Status: " + consultaEstudiante.getStatus());
        System.out.println("HTTP Message: " + consultaEstudiante.getBody().toString());

        //Crear un nuevo estudiante
        Estudiante estu = new Estudiante(1111, "nuevo", "ISC");
        JSONObject estudiante = new JSONObject();
        estudiante.put("matricula", estu.getMatricula());
        estudiante.put("nombre", estu.getNombre());
        estudiante.put("carrera", estu.getCarrera());

        HttpResponse addEstudiante = Unirest.post("http://localhost:7000/api/estudiante/")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(estudiante)
                .asJson();
        System.out.println("\nCrear Estudiante");
        System.out.println("HTTP Status: " + addEstudiante.getStatus());
        System.out.println("HTTP Message: " + addEstudiante.getBody().toString());

        //Eliminar Estudiante
        HttpResponse deleteEstudiante = Unirest.delete("http://localhost:7000/api/estudiante/" + estu.getMatricula())
                .header("accept", "application/json")
                .asObject(Boolean.class);
        System.out.println("\nBorrar Estudiante");
        System.out.println("HTTP Status: " + deleteEstudiante.getStatus());
        System.out.println("HTTP Message: " + deleteEstudiante.getBody().toString());
    }

    static class Estudiante {
        private int matricula;
        private String nombre;
        private String carrera;

        public int getMatricula() {
            return matricula;
        }

        public void setMatricula(int matricula) {
            this.matricula = matricula;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCarrera() {
            return carrera;
        }

        public Estudiante(int matricula, String nombre, String carrera) {
            this.matricula = matricula;
            this.nombre = nombre;
            this.carrera = carrera;
        }

        public void setCarrera(String carrera) {
            this.carrera = carrera;
        }

        @Override
        public String toString() {
            return "Estudiante{" +
                    "matricula=" + matricula +
                    ", nombre='" + nombre + '\'' +
                    ", carrera='" + carrera + '\'' +
                    '}';
        }
    }
}
