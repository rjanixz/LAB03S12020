package lab03;

import lab03.func.Funcion;
import lab03.instr.*;
import lab03.ts.Ambito;
import lab03.ts.Elemento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Lab07 {

    public static Map<String, Funcion> FUNCIONES = new HashMap<>();
    public static Map<String, Ambito> TABLA_DE_SIMBOLOS = new HashMap<>();

    private static int VAR_POS = 0;


    public static void main(String[] args) {

        Gramatica parser = null;
        try {
            parser = new Gramatica(new BufferedReader(new FileReader("./entrada_ambitos.txt")));
            parser.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Funciones
        FUNCIONES = parser.funciones;
        System.out.println("Se encontraron " + FUNCIONES.size() + " funciones");
        for(Funcion func : FUNCIONES.values()) {
            System.out.println(func.toString());
        }

        // Instrucciones globales
        List<Instruccion> instrucciones = parser.instrucciones;
        Ambito ambitoGlobal = crearAmbito("global", "Global", null);

        VAR_POS = 0; // LAS posiciones de globales siempre
        procesar(instrucciones, ambitoGlobal, true);

        // funciones
        procesarFunciones(ambitoGlobal);

        System.out.println("[" + TABLA_DE_SIMBOLOS
                .values()
                .stream()
                .map(e -> e.toString())
                .collect(Collectors.joining(","))  + "]");

        // comienzo a traducir
        TraductorLab08.traducir(instrucciones);
    }

    private static int siguientePOS() {
        return VAR_POS++;
    }

    private static Ambito crearAmbito(String id, String rol, String padreId) {
        Ambito ambito = new Ambito(id, rol, padreId, 0, false);
        TABLA_DE_SIMBOLOS.put(id, ambito);

        return ambito;
    }

    private static Ambito crearAmbitoFuncion(String id, String rol, String padreId, int numParams) {
        Ambito ambito = new Ambito(id, rol, padreId, numParams, true);
        TABLA_DE_SIMBOLOS.put(id, ambito);

        return ambito;
    }


    public static void procesar(List<Instruccion> instrucciones, Ambito ambito, boolean esGlobal) {

        for (Instruccion instr : instrucciones) {
            if (instr instanceof Imprimir) {
                // TODO
            } else if (instr instanceof Definicion) {
                // me sirve para crear los elementos
                Definicion definicion = (Definicion) instr;
                Elemento variable = new Elemento(
                        definicion.id,
                        esGlobal ? "variable global" : "variable local",
                        "int",
                        siguientePOS());

                try {
                    ambito.agregarElemento(variable);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                    // TODO agregar a su reporte de errores
                }

            } else if (instr instanceof If) {
                If instrIf = (If)instr;
                Ambito ambitoIf = crearAmbito("if-" + Math.random(), "If", ambito.id);

                procesar(instrIf.instruccionesIfTrue, ambitoIf, false);

                Ambito ambitoElse = crearAmbito("else-" + Math.random(), "Else", ambito.id);
                procesar(instrIf.instruccionesIfFalse, ambitoElse, false);

            } else if (instr instanceof While) {
                While instrWhile = (While) instr;
                Ambito ambitoWhile = crearAmbito("while-" + Math.random(), "While", ambito.id);
                procesar(instrWhile.instrucciones, ambitoWhile, false);
            }
        }
    }


    public static void procesarFunciones(Ambito ambito) {
        for (Funcion funcion : FUNCIONES.values()) {

            // Reseteamos las posiciones
            VAR_POS = 2; // THIS Y RETURN
            Ambito ambitoFuncion = crearAmbitoFuncion(
                    funcion.id,
                    "Función",
                    ambito.id,
                    funcion.params.size());

            // procesar los parámetros
            for (String param : funcion.params) {
                Elemento elementoParametro = new Elemento(param, "parámetro", "int", siguientePOS());

                try {
                    ambitoFuncion.agregarElemento(elementoParametro);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                    // TODO agregar al reporte de errores
                }
            }

            // procesar innstrucciones
            procesar(funcion.instrucciones, ambitoFuncion, false);
            ambitoFuncion.posFinal = VAR_POS;
        }
    }
}
