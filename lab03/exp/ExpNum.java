package lab03.exp;

public class ExpNum extends Expresion {

    public int val;

    public ExpNum(int val) {
        this.val = val;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"ExpNum\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp1 = "nodo" + ++cont;
        builder.append(nodoOp1).append(" [label=\""+ val + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp1).append(";\n");


        return cont;
    }
}
