package models;

public class Jugador extends Cromo{

    private String nombre;
    private String equipo;
    private int altura;

    public Jugador(int id, String nombre, String equipo, int altura) {
        super(id);
        this.nombre = nombre;
        this.equipo =  equipo;
        this.altura = altura;

    }
}
