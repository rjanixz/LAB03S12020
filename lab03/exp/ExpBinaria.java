package lab03.exp;

public class ExpBinaria extends Expresion {

    public enum Operador {
        SUMA,
        RESTA,
        POR,
        DIV
    }

    public Expresion op1;
    public Expresion op2;
    public Operador operador;

    public ExpBinaria(Expresion op1, Expresion op2, Operador operador) {
        this.op1 = op1;
        this.op2 = op2;
        this.operador = operador;
    }
}











