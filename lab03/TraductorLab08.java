package lab03;

import lab03.exp.ExpId;
import lab03.exp.ExpNum;
import lab03.exp.Expresion;
import lab03.func.Funcion;
import lab03.instr.Asignacion;
import lab03.instr.Definicion;
import lab03.instr.Imprimir;
import lab03.instr.Instruccion;
import lab03.ts.Ambito;
import lab03.ts.Elemento;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.List;

public class TraductorLab08 {

    private static int contadorTemporales = 0;
    private static int contadorEtiquetas = 0;

    private static StringBuilder BUILDER = new StringBuilder();

    private TraductorLab08() {
        //
    }

    private static String siguienteTemporal() {
        return "t" + contadorTemporales++;
    }

    private static String siguienteEtiqueta() {
        return "L" + contadorEtiquetas++;
    }

    public static void traducir(List<Instruccion> instrucciones) {

        // funcion inicial
        BUILDER.append("def init() {\n");

        BUILDER.append("H = 0;\n");
        BUILDER.append("P = 0;\n");


        Ambito ambitoGlobal = Lab07.TABLA_DE_SIMBOLOS.get("global");
        traducirInstruccionesGlobales(instrucciones, ambitoGlobal);


        BUILDER.append("call main(); //llamada al main\n");

        BUILDER.append("}\n");

        // traducir el resto de funciones
        for(Funcion funcion : Lab07.FUNCIONES.values()) {
            traducirFuncion(funcion);
        }

        System.out.println(BUILDER.toString());

    }

    private static void traducirInstruccionesGlobales(List<Instruccion> instrucciones, Ambito ambito) {
        for (Instruccion instr : instrucciones) {
            if (instr instanceof Definicion) {
                Definicion definicion = (Definicion)instr;

                Elemento elemento = null;
                // obtener posición
                try {
                    elemento = ambito.obtenerElemento(definicion.id);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                }

                String tempPos = siguienteTemporal();
                BUILDER.append(tempPos).append(" = ").append(elemento.pos).append("; // posicion de la variable global: " + definicion.id).append("\n");

                // el valor es 0 por defecto

                BUILDER.append("Heap[").append(tempPos).append("] = 0; // valor por defecto\n");
                BUILDER.append("H= H +1;\n");


            } else if (instr instanceof Asignacion) {
                Asignacion asingnacion = (Asignacion) instr;

                Elemento elemento = null;
                // obtener posición
                try {
                    elemento = ambito.obtenerElemento(asingnacion.id);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                }

                String tempPos = siguienteTemporal();
                BUILDER.append(tempPos).append(" = ").append(elemento.pos).append("; // posicion de la variable global: " + asingnacion.id).append("\n");

                // calcular el valor
                String tempValor = traducirExpresion(asingnacion.exp, ambito);

                // asigno
                BUILDER.append("Heap[").append(tempPos).append("] = ").append(tempValor).append("; // valor inicial\n");


            } else if (instr instanceof Imprimir) {

            } else {
                // TODO
                System.err.println("Aún no implementado: " + instr.toString());
            }
        }
    }

    private static void traducirFuncion(Funcion funcion) {

        Ambito ambitoFuncion = Lab07.TABLA_DE_SIMBOLOS.get(funcion.id);

        BUILDER.append("\n");

        BUILDER.append("def ").append(funcion.id).append("() {\n");

        // paso de parámetros
        String tempValThis = siguienteTemporal();
        BUILDER.append(tempValThis).append("= Stack[0]; // pos de this, siempre está en 0\n");



        traducirInstruccionesLocales(funcion.instrucciones, ambitoFuncion, tempValThis);



        BUILDER.append("}\n");
        BUILDER.append("\n");
    }


    private static void traducirInstruccionesLocales(List<Instruccion> instrucciones, Ambito ambito, String tempValThis) {
        for (Instruccion instr : instrucciones) {
            if (instr instanceof Definicion) {

                Definicion definicion = (Definicion)instr;

                Elemento elemento = null;
                // obtener posición
                try {
                    elemento = ambito.obtenerElemento(definicion.id);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                }

                String tempPos = siguienteTemporal();
                BUILDER.append(tempPos).append(" = ").append(tempValThis).append(" + ").append(elemento.pos).append("; // posicion de la variable local: " + definicion.id).append("\n");

                // el valor es 0 por defecto
                BUILDER.append("Stack[").append(tempPos).append("] = 0; // valor por defecto\n");


            } else if (instr instanceof Asignacion) {
                Asignacion asingnacion = (Asignacion) instr;

                Elemento elemento = null;
                // obtener posición
                try {
                    elemento = ambito.obtenerElemento(asingnacion.id);
                } catch (Exception e) {
                    System.err.println("ERROR SEMANTICO: " + e.getMessage());
                }

                String tempPos = siguienteTemporal();
                BUILDER.append(tempPos).append(" = ").append(tempValThis).append(" + ").append(elemento.pos).append("; // posicion de la variable local: " + asingnacion.id).append("\n");

                // calcular el valor
                String tempValor = traducirExpresion(asingnacion.exp, ambito);

                // asigno
                BUILDER.append("Stack[").append(tempPos).append("] = ").append(tempValor).append("; // valor de asignacion\n");
            } else if (instr instanceof Imprimir) {

            } else {
                // TODO
                System.err.println("Aún no implementado: " + instr.toString());
            }
        }
    }


    private static String traducirExpresion(Expresion expr, Ambito ambito) {
        if(expr instanceof ExpNum) {
            return  "" + ((ExpNum) expr).val;
        } else if (expr instanceof ExpId) {
            return ""; // TODO
        }
        else {
            // TODO
            System.err.println("Aún no implementado: " + expr.toString());
            return ""; // TODO
        }
    }
}
