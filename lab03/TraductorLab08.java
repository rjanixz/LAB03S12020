package lab03;

import lab03.exp.ExpId;
import lab03.exp.ExpNum;
import lab03.exp.Expresion;
import lab03.instr.Asignacion;
import lab03.instr.Definicion;
import lab03.instr.Imprimir;
import lab03.instr.Instruccion;
import lab03.ts.Ambito;

import java.util.List;

public class TraductorLab08 {

    private static int contadorTemporales = 0;
    private static int contadorEtiquetas = 0;

    private TraductorLab08() {
        //
    }

    public static String siguienteTemporal() {
        return "t" + contadorTemporales++;
    }

    public static String siguienteEtiqueta() {
        return "L" + contadorEtiquetas++;
    }

    public static void traducir(List<Instruccion> instrucciones) {

        for (Instruccion instr : instrucciones) {
            if (instr instanceof Definicion) {

            } else if (instr instanceof Asignacion) {

            } else if (instr instanceof Imprimir) {

            } else {
                // TODO
                System.err.println("Aún no implementado: " + instr.toString());
            }
        }
    }


    public static void traducirExpresion(Expresion expr, Ambito ambito, StringBuilder builder) {
        if(expr instanceof ExpNum) {

        } else if (expr instanceof ExpId) {

        }
        else {
            // TODO
            System.err.println("Aún no implementado: " + expr.toString());
        }
    }
}
