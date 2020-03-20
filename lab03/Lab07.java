package lab03;

import lab03.core.Return;
import lab03.core.ReturnVoid;
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

    private static Map<String, Funcion> FUNCIONES = new HashMap<>();
    private static Map<String, Ambito> TABLA_DE_SIMBOLOS = new HashMap<>();

    public static void main(String[] args) {

        Gramatica parser = null;
        try {
            parser = new Gramatica(new BufferedReader(new FileReader("./entrada.txt")));
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
        Ambito ambitoGlobal = crearAmbito("global", "Global", null, 0);
        procesar(instrucciones, ambitoGlobal, true);

        // funciones
        procesarFunciones(ambitoGlobal);

        System.out.println("[" + TABLA_DE_SIMBOLOS
                .values()
                .stream()
                .map(e -> e.toString())
                .collect(Collectors.joining(","))  + "]");

    }


    private static Ambito crearAmbito(String id, String rol, String padreId, int numParams) {
        Ambito ambito = new Ambito(id, rol, padreId, numParams);
        TABLA_DE_SIMBOLOS.put(id, ambito);

        return ambito;
    }

    public static void procesar(List<Instruccion> instrucciones, Ambito ambito, boolean esGlobal) {

        int pos = 0;
        for (Instruccion instr : instrucciones) {
            if (instr instanceof Imprimir) {
                // TODO
            } else if (instr instanceof Definicion) {
                // me sirve para crear los elementos
                Definicion definicion = (Definicion) instr;
                Elemento variable = new Elemento(
                        definicion.id,
                        esGlobal ? "variable global" : "variable local",
                        "int", pos++ );

                try {
                    ambito.agregarElemento(variable);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                    // TODO agregar a su reporte de errores
                }

            } else if (instr instanceof If) {
                If instrIf = (If)instr;
                Ambito ambitoIf = crearAmbito("if-" + Math.random(), "If", ambito.id, 0);
                procesar(instrIf.instruccionesIfTrue, ambitoIf, false);

                Ambito ambitoElse = crearAmbito("else-" + Math.random(), "Else", ambito.id, 0);
                procesar(instrIf.instruccionesIfFalse, ambitoElse, false);

            } else if (instr instanceof While) {
                While instrWhile = (While) instr;
                Ambito ambitoWhile = crearAmbito("while-" + Math.random(), "While", ambito.id, 0);
                procesar(instrWhile.instrucciones, ambitoWhile, false);
            }
        }
    }


    public static void procesarFunciones(Ambito ambito) {
        for (Funcion funcion : FUNCIONES.values()) {
            Ambito ambitoFuncion = crearAmbito(
                    funcion.id,
                    "Función",
                    ambito.id,
                    funcion.params.size());

            // procesar los parámetros
            int pos = 0;
            for (String param : funcion.params) {
                Elemento elementoParametro = new Elemento(param, "parámetro", "int",pos++ );

                try {
                    ambitoFuncion.agregarElemento(elementoParametro);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                    // TODO agregar al reporte de errores
                }
            }

            // procesar innstrucciones
            procesar(funcion.instrucciones, ambitoFuncion, false);
        }
    }
}
