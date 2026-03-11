package models;

public abstract class Cromo {

    private String idUnico;
    private String nombre;

    public Cromo(int id,String nombre) {
        idUnico = String.valueOf(id);
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Cromo cromo)) return false;

        return idUnico.equals(cromo.idUnico);
    }

    @Override
    public int hashCode() {
        return idUnico.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cromo{");
        sb.append("idUnico='").append(idUnico).append('\'');
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
