package models;

public class Escudo extends Cromo{


    private int añoDeFundacion;
    private int numeroDeJugadores;

    public Escudo(int id, String nombreEquipo,int añoDeFundacion, int numeroDeJugadores) {
        super(id,nombreEquipo);
        this.añoDeFundacion = añoDeFundacion;
        this.numeroDeJugadores = numeroDeJugadores;
    }

    public int getNumeroDeJugadores() {
        return numeroDeJugadores;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Escudo{");
        sb.append("añoDeFundacion=").append(añoDeFundacion);
        sb.append(", numeroDeJugadores=").append(numeroDeJugadores);
        sb.append('}');
        return sb.toString();
    }
}
