package lab03;

import lab03.core.*;
import lab03.exp.*;
import lab03.func.Funcion;
import lab03.instr.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab03 {

    private static Map<String, Integer> TS = new HashMap<>();
    private static Map<String, Funcion> FUNCIONES = new HashMap<>();

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

        // AST
        List<Instruccion> instrucciones = parser.instrucciones;
        toDOT(instrucciones);
        Return resultado = procesar(instrucciones);

        if (resultado instanceof ReturnVoid) {
            System.out.println("TODO OK");
        } else {
            System.err.println("Sentencia no valida: " + resultado);
        }
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

    public static Return procesar(List<Instruccion> instrucciones) {

        Return resultado = new ReturnVoid();
        for (Instruccion instr : instrucciones) {
            if (instr instanceof Definicion) {
                procesarDefinicion((Definicion) instr);
            } else if (instr instanceof Asignacion) {
                procesarAsignacion((Asignacion) instr);
            } else if (instr instanceof Imprimir) {
                procesarImprimir((Imprimir) instr);
            } else if (instr instanceof Break) {
                return procesarBreak((Break) instr);
            } else if (instr instanceof Continue) {
                return procesarContinue((Continue) instr);
            } else if (instr instanceof Retornar) {
                return procesarRetornar((Retornar) instr);
            } else if(instr instanceof LlamadaFuncion) {
                resultado = procesarFuncion(((LlamadaFuncion) instr).expFuncion);
            }
            else if (instr instanceof If) {
                Return resultadoIf = procesarIf((If) instr);

                if (resultadoIf instanceof ReturnContinue
                    || resultadoIf instanceof ReturnBreak) {
                    return resultadoIf;
                }
            } else if (instr instanceof While) {
                resultado = procesarWhile((While) instr);
            }

            else {
                System.err.println("No debio entrar por aqui.");
            }
        }

        return resultado;
    }

    public static Return procesarIf(If instrIf) {
        boolean exp = procesarExpresionBoolean(instrIf.expBoolean);

        if (exp) {
            return procesar(instrIf.instruccionesIfTrue);
        } else {
            return procesar(instrIf.instruccionesIfFalse);
        }
    }

    public static Return procesarWhile(While instrWhile) {

        while(procesarExpresionBoolean(instrWhile.expBoolean)) {
            Return resultadoWhile = procesar(instrWhile.instrucciones);

            if (resultadoWhile instanceof ReturnBreak) {
                break;
            } else if (resultadoWhile instanceof ReturnContinue) {
                continue;
            } else {
                return resultadoWhile;
            }
        }
        return new ReturnVoid();
    }

    public static ReturnBreak procesarBreak(Break instrBreak) {
        return new ReturnBreak();
    }

    public static ReturnContinue procesarContinue(Continue instrContinue) {
        return new ReturnContinue();
    }

    public static ReturnValor procesarRetornar(Retornar instRetorno) {
        int valor = procesarExpresion(instRetorno.exp);
        return new ReturnValor(valor);
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
        } else if (exp instanceof ExpFuncion) {
            Return resultado = procesarFuncion((ExpFuncion) exp);
            if (resultado instanceof ReturnValor) {
                return ((ReturnValor) resultado).valor;
            } else {
                return -1; // TODO error
            }
        }
        else {
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

    public static boolean procesarExpresionBoolean(ExpBoolean expBool) {

        int exp1 = procesarExpresion(expBool.exp1);
        int exp2 = procesarExpresion(expBool.exp2);

        switch(expBool.op) {
            case GT: return exp1 > exp2;
            case LT: return exp1 < exp2;
            default: return false; // TODO validar errores
        }
    }

    public static Return procesarFuncion(ExpFuncion expFunc) {

        Funcion funcion = FUNCIONES.get(expFunc.id);
        // TODO valid si la funcion existe

        int cont = 0;
        for(String param : funcion.params) {
            int valor = procesarExpresion(expFunc.valores.get(cont));
            TS.put(param, valor);
            cont++;
        }

        return procesar(funcion.instrucciones);
    }

















}
