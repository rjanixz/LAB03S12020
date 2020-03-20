package lab03.ts;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Ambito {

    public String id;
    public String rol;
    public String padreId;

    public Set<Elemento> elementos;

    public int tamanio;

    public int numParams;

    public Ambito(String id, String rol, String padreId, int numParams) {
        this.id = id;
        this.rol = rol;
        this.padreId = padreId;
        this.numParams = numParams;
        this.elementos = new HashSet<>();
        this.tamanio = 0;
    }

    public void agregarElemento(Elemento elemento) throws Exception {

        boolean existe = elementos.stream().anyMatch(e -> e.id.equalsIgnoreCase(elemento.id));

        if (existe) {
            throw new Exception("La variable " + elemento.id + " ya existe en este ámbtio");
        }

        elementos.add(elemento);
        tamanio++;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"rol\":\"" + rol + "\"" +
                ", \"padreId\":\"" + padreId + "\"" +
                ", \"elementos\": [" + elementos.stream().map(e -> e.toString()).collect(Collectors.joining(",")) + "]" +
                ", \"tamanio\":" + tamanio +
                ", \"numParams\":" + numParams +
                '}';
    }
}
