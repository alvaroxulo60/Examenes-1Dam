package models;

public class Escudo extends Cromo{

    private String nombreEquipo;
    private int añoDeFundacion;
    private int numeroDeJugadores;

    public Escudo(int id, String nombreEquipo,int añoDeFundacion, int numeroDeJugadores) {
        super(id,nombreEquipo);
        this.añoDeFundacion = añoDeFundacion;
        this.numeroDeJugadores = numeroDeJugadores;
    }
}
