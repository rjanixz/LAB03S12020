package lab03;

import lab03.exp.ExpBinaria;
import lab03.exp.ExpId;
import lab03.exp.ExpNum;
import lab03.exp.Expresion;
import lab03.instr.Asignacion;
import lab03.instr.Definicion;
import lab03.instr.Imprimir;
import lab03.instr.Instruccion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab03 {

    private static Map<String, Integer> TS = new HashMap<>();

    public static void main(String[] args) {

        Gramatica parser = null;
        try {
            parser = new Gramatica(new BufferedReader(new FileReader("./entrada.txt")));
            parser.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // AST
        List<Instruccion> instrucciones = parser.instrucciones;
        toDOT(instrucciones);
        instrucciones.forEach(Lab03::procesar);
    }

    public static void toDOT(List<Instruccion> instrucciones) {
        StringBuilder builder = new StringBuilder();
        int cont = 1;
        String root = "nodo" + cont;
        builder.append("digraph lab5 {\n");
        builder.append(root).append(" [label=\"Lab05\"];\n");

        for (Instruccion instr : instrucciones) {
            cont = instr.toDOT(builder, root, cont);
        }
        builder.append("}");

        System.out.println(builder.toString());

    }

    public static void procesar(Instruccion instr) {

        if (instr instanceof Definicion) {
            procesarDefinicion((Definicion)instr);
        } else if (instr instanceof Asignacion) {
            procesarAsignacion((Asignacion)instr);
        } else if (instr instanceof Imprimir) {
            procesarImprimir((Imprimir)instr);
        } else {
            System.err.println ("No debio entrar por aqui.");
        }
    }

    public static void procesarDefinicion(Definicion definicion) {
        // TODO validar si la variable ya existe dar error
        TS.put(definicion.id, 0);
    }

    public static void procesarAsignacion(Asignacion asignacion) {
        // TODO validar si la variable no existe dar error
        int val = procesarExpresion(asignacion.exp);
        TS.put(asignacion.id, val);
    }

    public static void procesarImprimir(Imprimir imprimir){
        int val = procesarExpresion(imprimir.exp);
        System.out.println(">" + val);
    }


    public static int procesarExpresion(Expresion exp)  {
        if (exp instanceof ExpBinaria) {
            return procesarExpresionBinaria((ExpBinaria)exp);
        } else if (exp instanceof ExpId) {
            return procesarExpId((ExpId) exp);
        } else if (exp instanceof ExpNum) {
            return procesarExpNum((ExpNum) exp);
        } else {
            System.err.println ("Error en expresion");
            return -1; // TODO validar errores
        }
    }

    public static int procesarExpresionBinaria(ExpBinaria expb) {
        switch(expb.operador) {
            case SUMA : return procesarExpresion(expb.op1) + procesarExpresion(expb.op2) ;
            case RESTA : return procesarExpresion(expb.op1) - procesarExpresion(expb.op2);
            case POR : return procesarExpresion(expb.op1) * procesarExpresion(expb.op2);
            case DIV : return procesarExpresion(expb.op1) / procesarExpresion(expb.op2) ;
            default: return -1;  // TODO validar errores
        }
    }


    public static int procesarExpId(ExpId expId) {
        // TODO validar que exista la variable
        return TS.get(expId.id);
    }


    public static int procesarExpNum(ExpNum expNum) {
        return expNum.val;
    }

















}
