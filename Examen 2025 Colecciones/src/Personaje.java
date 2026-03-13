import java.util.LinkedList;
import java.util.List;

public class Personaje {
    private final int VIDA_MAX;
    private final int KI_MAX;

    private String nombre;
    private TRaza raza;
    private int vida;
    private int ki;
    private List<Ataque> ataques;


    public Personaje(String nombre, TRaza raza, int VIDA_MAX, int vida, int KI_MAX, int ki) {
        this.nombre = nombre;
        this.raza = raza;
        this.VIDA_MAX = VIDA_MAX;
        this.vida = vida;
        this.KI_MAX = KI_MAX;
        this.ki = ki;
        ataques = new LinkedList<>();
    }

    public void addAtaque(Ataque ataque) {
        ataques.add(ataque);
    }

    public void recibirDaño(int daño) {
        if (daño > vida) {
            vida = 0;
        } else {
            vida -= daño;
        }
    }

    public void restarKi(int modificacionKi) {
        if (ki - modificacionKi <= 0) {
            ki = 0;
        } else {
            ki -= modificacionKi;
        }
    }

    public Ataque buscarMejorAtaque(String nombre) throws DBException {
        return ataques.stream().filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .filter(a -> a.getKI_NECESARIO() <= this.ki).max(Ataque::compareTo).orElseThrow(() -> new DBException("Moises friki 3.5"));
    }

    public void eliminarAtaque(Ataque a) {
        ataques.remove(a);
    }


    public int getVida() {
        return vida;
    }

    public String getNombre() {
        return nombre;
    }

    public int getKi() {
        return ki;
    }

    public TRaza getRaza() {
        return raza;
    }

    public List<Ataque> getAtaques() {
        return ataques;
    }

    public boolean isMuerto() {
        return vida == 0;
    }

    public int getNumeroAtaques() {
        return ataques.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Personaje{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", raza=").append(raza);
        sb.append(", vida=").append(vida);
        sb.append(", ki=").append(ki);
        sb.append(", ataques=").append(ataques);
        sb.append('}');
        return sb.toString();
    }
}
