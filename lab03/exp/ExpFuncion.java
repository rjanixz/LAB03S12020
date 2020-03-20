package lab03.exp;

import java.util.List;

public class ExpFuncion extends Expresion {

    public String id;
    public List<Expresion> valores;

    public ExpFuncion(String id, List<Expresion> valores) {
        this.id = id;
        this.valores = valores;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        return 0;
    }
}
