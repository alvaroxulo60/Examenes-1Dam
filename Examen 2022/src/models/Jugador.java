package models;

public class Jugador extends Cromo{

    private String nombre;
    private String equipo;
    private double altura;

    public Jugador(int id, String nombre, String equipo, double altura) {
        super(id,nombre);
        this.equipo =  equipo;
        this.altura = altura;

    }

    public String getEquipo() {
        return equipo;
    }
}
