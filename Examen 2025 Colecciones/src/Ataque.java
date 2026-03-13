public class Ataque implements Comparable<Ataque> {
    private final int KI_NECESARIO;
    private final int PERFECCION_DEL_ATAQUE;
    private final int DAÑO;

    private String nombre;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Ataque ataque)) return false;

        return PERFECCION_DEL_ATAQUE == ataque.PERFECCION_DEL_ATAQUE && DAÑO == ataque.DAÑO && nombre.equals(ataque.nombre);
    }

    @Override
    public int hashCode() {
        int result = PERFECCION_DEL_ATAQUE;
        result = 31 * result + DAÑO;
        result = 31 * result + nombre.hashCode();
        return result;
    }

    public Ataque(String nombre, int kiNecesario, int perfeccionDelAtaque, int daño) throws DBException {
        this.nombre = nombre;
        if (kiNecesario <= 0) throw new DBException("El ki no es valido");
        this.KI_NECESARIO = kiNecesario;
        if (perfeccionDelAtaque < 1 || perfeccionDelAtaque > 3) throw new DBException("La perfección no es valida");
        this.PERFECCION_DEL_ATAQUE = perfeccionDelAtaque;
        if (daño <= 0) throw new DBException("EL daño no es válido");
        this.DAÑO = daño;
    }

    public int getKI_NECESARIO() {
        return KI_NECESARIO;
    }

    public int getPERFECCION_DEL_ATAQUE() {
        return PERFECCION_DEL_ATAQUE;
    }


    public int getDAÑO() {
        return DAÑO;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int compareTo(Ataque o) {
        return this.DAÑO - o.DAÑO;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ataque{");
        sb.append("KI_NECESARIO=").append(KI_NECESARIO);
        sb.append(", PERFECCION_DEL_ATAQUE=").append(PERFECCION_DEL_ATAQUE);
        sb.append(", DAÑO=").append(DAÑO);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
