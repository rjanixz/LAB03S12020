package lab03.ts;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Ambito {

    public String id;
    public String rol;
    public String padreId;

    public Set<Elemento> elementos;

    public int posFinal; // es el tamanio

    public int numParams;

    boolean esFuncion;

    public Ambito(String id, String rol, String padreId, int numParams, boolean esFuncion) {
        this.id = id;
        this.rol = rol;
        this.padreId = padreId;
        this.numParams = numParams;
        this.elementos = new HashSet<>();
        this.esFuncion = esFuncion;
    }

    public void agregarElemento(Elemento elemento) throws Exception {

        boolean existe = elementos.stream().anyMatch(e -> e.id.equalsIgnoreCase(elemento.id));

        if (existe) {
            throw new Exception("La variable " + elemento.id + " ya existe en este ámbtio");
        }

        elementos.add(elemento);
    }

    public Elemento obtenerElemento(String id) throws Exception {

        Optional<Elemento> optionalElemento = elementos.stream().filter(e -> e.id.equalsIgnoreCase(id)).findFirst();

        if(optionalElemento.isPresent()) {
            return optionalElemento.get();
        }
        throw new Exception("Variable " + id + "No definida");

    }


    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"rol\":\"" + rol + "\"" +
                ", \"padreId\":\"" + padreId + "\"" +
                ", \"elementos\": [" + elementos.stream().map(e -> e.toString()).collect(Collectors.joining(",")) + "]" +
                (esFuncion ? ", \"tamanio\":" + posFinal : "") +
                (esFuncion ?", \"numParams\":" + numParams : "") +
                '}';
    }
}
