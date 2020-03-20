package lab03.ts;

public class Elemento {

    public String id;
    public String rol;
    public String tipo;
    public int pos;

    public Elemento(String id, String rol, String tipo, int pos) {
        this.id = id;
        this.rol = rol;
        this.tipo = tipo;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"rol\":\"" + rol + "\"" +
                ", \"tipo\":\"" + tipo + "\"" +
                ", \"pos\":" + pos +
                '}';
    }
}
