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


    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Expresion Binaria\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        cont = op1.toDOT(builder, nodo, cont);

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + operador + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");

        cont = op2.toDOT(builder, nodo, cont);

        return cont;
    }
}











