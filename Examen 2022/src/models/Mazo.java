package models;

import exceptions.MazoException;

import java.util.*;

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

    public int contarCromosDistintos() {
        return cromos.size();
    }

    public List<Cromo> cromosQueSonDeUnEquipo(String nombreDelEquipo) {
        return cromos.keySet().stream()
                .filter(cromo -> {
                    if (cromo instanceof Jugador j) {
                        return j.getEquipo().equalsIgnoreCase(nombreDelEquipo);
                    } else {
                        return cromo.getNombre().equalsIgnoreCase(nombreDelEquipo);
                    }
                }).toList();
    }

    public double alturaMediaDeUnEquipo(String equipo) throws MazoException {
        return getCromos().keySet().stream().filter(c -> c instanceof Jugador)
                .map(cromo -> (Jugador) cromo).filter(j -> j.getEquipo().equalsIgnoreCase(equipo))
                .mapToDouble(Jugador::getAltura).average().orElseThrow(() -> new MazoException("No se puede calcular la media"));
    }

    public List<Cromo> cromosSinImportarElOrden() {
        return getCromos().keySet().stream().toList();
    }

    public List<Cromo> cromosOrdenados() {
        return getCromos().keySet().stream().sorted(
                (c1, c2) -> {
                    if ((c1) instanceof Escudo && c2 instanceof Jugador) {
                        return -1;
                    }
                    if (c1 instanceof Jugador && c2 instanceof Escudo) {
                        return 1;
                    }

                    String n1 = (c1 instanceof Escudo) ? c1.getNombre() : ((Jugador) c1).getNombre();
                    String n2 = (c2 instanceof Escudo) ? c2.getNombre() : ((Jugador) c2).getNombre();

                    return n1.compareToIgnoreCase(n2);
                }
        ).toList();
    }

    public List<String> equipoEntero() throws MazoException {
        List<Jugador> jugadores = new ArrayList<>(getCromos().keySet().stream().filter(c -> c instanceof Jugador).map(c -> (Jugador) c).toList());
        List<Escudo> escudos = new ArrayList<>(getCromos().keySet().stream().filter(c -> c instanceof Escudo).map(c -> (Escudo) c).toList());
        List<String> equipo = new ArrayList<>();

       for (Escudo e: escudos){
           Long numJugadores = jugadores.stream().filter(j-> j.getEquipo().equalsIgnoreCase(e.getNombre())).count();

           if (numJugadores == e.getNumeroDeJugadores()){
               equipo.add(e.getNombre());
               equipo.addAll(jugadores.stream().filter(j->j.getEquipo().equalsIgnoreCase(e.getNombre()))
                       .map(Jugador::getNombre).toList());
           }
       }


        if (equipo.isEmpty()) {
            throw new MazoException("bjbdjbfasdjb");
        }
        return equipo;
    }
}
