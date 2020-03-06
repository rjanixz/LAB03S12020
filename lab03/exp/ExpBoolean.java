package lab03.exp;

public class ExpBoolean {

    public enum Operador {
        GT,
        LT
    }

    public Expresion exp1;
    public Expresion exp2;
    public Operador op;

    public ExpBoolean(Expresion exp1, Expresion exp2, Operador op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }
}
