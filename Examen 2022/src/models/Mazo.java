package models;

import exceptions.MazoException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Mazo {
    private Map<Cromo, Integer> cromos;

    public Mazo() {
        cromos = new HashMap<>();
    }

    public Map<Cromo, Integer> getCromos() {
        return cromos;
    }

    public void setCromos(Map<Cromo, Integer> cromos) {
        this.cromos = cromos;
    }

    public void addCromo(Cromo c) {
        cromos.compute(c, (k, v) -> (v == null) ? 1 : v + 1);
    }

    public void intercambiarCromo(Cromo miCromo, Cromo cromoNuevo) throws MazoException {
        if (!cromos.containsKey(miCromo)) {
            throw new MazoException("No tienes " + miCromo.getNombre());
        }
        cromos.computeIfPresent(miCromo, (c, v) -> v > 1 ? v - 1 : null);
        addCromo(cromoNuevo);
    }



    public void mezclarMazo(Mazo otroMazo) {
        otroMazo.getCromos().forEach((cromo, integerB) ->
                this.getCromos().merge(cromo, integerB, Integer::sum));
    }

    public int contarCromosDistintos(){
        return cromos.size();
    }

    public List<Cromo> cromosQueSonDeUnEquipo(String nombreDelEquipo){
        return cromos.keySet().stream()
                .filter(cromo -> {
                    if (cromo instanceof Jugador j){
                        return j.getEquipo().equalsIgnoreCase(nombreDelEquipo);
                    }
                    else {
                        return cromo.getNombre().equalsIgnoreCase(nombreDelEquipo);
                    }
                }).toList();
    }
}
