package models;

public class Jugador extends Cromo{

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

    public double getAltura() {
        return altura;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Jugador{");
        sb.append("equipo='").append(equipo).append('\'');
        sb.append(", altura=").append(altura);
        sb.append('}');
        return sb.toString();
    }
}
