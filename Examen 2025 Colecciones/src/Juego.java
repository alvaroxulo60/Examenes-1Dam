import java.util.*;

public class Juego {

    private Set<Personaje> personajes;

    public Juego() {
        personajes = new HashSet<>();
    }

    public static void main(String[] args) {
        try {
            Juego juego = new Juego();
            // Crear personajes
            crearPersonajes(juego);

            // Llamada al método que muestra los personajes con más ataques
            System.out.println("Personaje(s) que conocen más ataques:");
            juego.personajeConMasAtaques();
            System.out.println();
            System.out.println("Personaje(s) con el ataque más poderoso:");
            //juego.personajeConAtaqueMasPoderoso();
            System.out.println();
            System.out.println("Ataques ordenados por nombre");
            juego.todosLosAtaquesOrdenadosNombre();
            System.out.println();
            System.out.println("Ataques ordenados por daño de mayor a menor");
            juego.todosLosAtaquesOrdenadosDamage();

            Personaje gohan = juego.buscarPersonaje("gohan", TRaza.SAIYAN);
            Personaje android18 = juego.buscarPersonaje("androide nº18", TRaza.ANDROIDE);

            System.out.println();
            System.out.println("El mejor ataque disponible de gohan es:");
            Ataque at = juego.ataqueMasDañino(gohan, android18);
            System.out.println(at);

            System.out.println();
            System.out.println("Gohan ataca a " + android18.getNombre());
            juego.atacar(gohan, android18, "Special Beam Cannon");

            /*System.out.println();
            System.out.println(android18.getNombre() + " intenta atacar a Gohan");
            juego.atacar(android18, gohan, "energy blast");*/

            System.out.println();
            System.out.println("Todos los ataques restantes de todos los jugadores:");
            juego.todosLosAtaquesOrdenadosNombre();

            System.out.println();
            System.out.println("Eliminamos los ataques de nivel 1.");
            juego.eliminarAtaquesInferioresANivel(2);
            System.out.println("Todos los ataques restantes de todos los jugadores:");
            juego.todosLosAtaquesOrdenadosNombre();

            System.out.println();
            System.out.println("Todos los personajes mostrador por raza:");
            Map<TRaza, List<Personaje>> mapa = juego.devuelveMapaRazas();
            mapa.entrySet().forEach(e -> {
                System.out.printf("Personajes de la raza %s:\n", e.getKey());
                e.getValue().forEach(p -> System.out.printf("\t%s\n", p.getNombre()));
            });


        } catch (DBException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void crearPersonajes(Juego juego) throws DBException {
        Personaje goku = new Personaje("Goku", TRaza.SAIYAN, 100, 100, 100, 10);
        Personaje vegeta = new Personaje("Vegeta", TRaza.SAIYAN, 90, 20, 90, 25);
        Personaje gohan = new Personaje("Gohan", TRaza.SAIYAN, 80, 15, 80, 70);
        Personaje piccolo = new Personaje("Piccolo", TRaza.NAMEKIANO, 80, 10, 50, 45);
        Personaje android18 = new Personaje("Androide nº18", TRaza.ANDROIDE, 90, 1, 60, 40);

        // Asignar ataques a Goku (3 ataques)
        Ataque kamehameha1 = new Ataque("Kamehameha", 10, 1, 20);
        Ataque kamehameha2 = new Ataque("Kamehameha", 15, 2, 30);
        Ataque kamehameha3 = new Ataque("Kamehameha", 20, 3, 40);
        goku.addAtaque(kamehameha1);
        goku.addAtaque(kamehameha2);
        goku.addAtaque(kamehameha3);

        // Asignar ataques a Vegeta (4 ataques)
        Ataque finalFlash1 = new Ataque("Final Flash", 12, 1, 25);
        Ataque galickGun = new Ataque("Galick Gun", 8, 1, 18);
        Ataque bigBangAttack = new Ataque("Big Bang Attack", 15, 2, 35);
        Ataque finalFlash2 = new Ataque("Final Flash", 12, 2, 28);
        vegeta.addAtaque(finalFlash1);
        vegeta.addAtaque(galickGun);
        vegeta.addAtaque(bigBangAttack);
        vegeta.addAtaque(finalFlash2);

        // Asignar ataques a Gohan (5 ataques)
        Ataque masenko1 = new Ataque("Masenko", 9, 1, 19);
        Ataque masenko2 = new Ataque("Masenko", 11, 2, 27);
        Ataque masenko3 = new Ataque("Masenko", 13, 3, 33);
        Ataque specialBeamCannon = new Ataque("Special Beam Cannon", 15, 2, 40);
        Ataque finalAttack = new Ataque("Final Attack", 20, 3, 50);
        gohan.addAtaque(masenko1);
        gohan.addAtaque(masenko2);
        //gohan.addAtaque(masenko3);
        gohan.addAtaque(specialBeamCannon);
        gohan.addAtaque(finalAttack);

        Ataque makankosappo1 = new Ataque("Makankosappo", 10, 1, 22);
        Ataque makankosappo2 = new Ataque("Makankosappo", 15, 2, 30);
        Ataque makankosappo3 = new Ataque("Makankosappo", 20, 3, 38);
        piccolo.addAtaque(makankosappo1);
        piccolo.addAtaque(makankosappo2);
        piccolo.addAtaque(makankosappo3);

        Ataque energyBlast = new Ataque("Energy Blast", 12, 1, 28);
        Ataque powerPunch = new Ataque("Power Punch", 8, 1, 18);
        android18.addAtaque(energyBlast);
        android18.addAtaque(powerPunch);

        // Agregar los personajes al juego
        juego.agregarPersonaje(goku);
        juego.agregarPersonaje(vegeta);
        juego.agregarPersonaje(gohan);
        juego.agregarPersonaje(piccolo);
        juego.agregarPersonaje(android18);
    }


    public Personaje buscarPersonaje(String nombre, TRaza raza) throws DBException {
        return personajes.stream().filter(p -> p.getNombre().equalsIgnoreCase(nombre) && p.getRaza().equals(raza))
                .findFirst().orElseThrow(() -> new DBException("No se ha encontrado ese personaje"));
    }


    public void agregarPersonaje(Personaje personaje) throws DBException {
        if (!personajes.add(personaje)) {
            throw new DBException("Ese personaje ya esta en el juego");
        }
    }

    public void personajeConMasAtaques() throws DBException {
        if (personajes.isEmpty()) {
            throw new DBException("No hay personajes en el juego");
        }

        int numeroMaximoDeAtaques = personajes.stream()
                .mapToInt(Personaje::getNumeroAtaques)
                .max().orElseThrow(() -> new DBException("No se ha podido calcular el máximo de ataques"));

        personajes.stream().filter(p -> p.getNumeroAtaques() == numeroMaximoDeAtaques).forEach(System.out::println);
    }

    public void todosLosAtaquesOrdenadosNombre() {
        personajes.stream().flatMap(p -> p.getAtaques().stream())
                .sorted((a1, a2) -> a1.getNombre().compareToIgnoreCase(a2.getNombre())).forEach(System.out::println);
    }

    public void todosLosAtaquesOrdenadosDamage() {
        personajes.stream().flatMap(p -> p.getAtaques().stream())
                .sorted().forEach(System.out::println);
    }

    public Ataque ataqueMasDañino(Personaje p1, Personaje p2) throws DBException {
        if (p1.isMuerto() && p1.getKi() < 0) {
            throw new DBException("Este personaje esta muerto o no tiene el ki suficiente para atacar");
        }
        return p1.getAtaques().stream().filter(a -> a.getKI_NECESARIO() <= p1.getKi())
                .filter(a -> a.getDAÑO() > 0)
                .max(Ataque::compareTo).orElse(null);
    }

    public void atacar(Personaje p1, Personaje p2, String ataque) throws DBException {
        if (p1.isMuerto()) {
            throw new DBException("No puedes atacar con este personaje");
        }
        if (p2.isMuerto()) {
            throw new DBException("No puedes atacar a un personaje muerto");
        }

        int dañoMax = p1.getAtaques().stream().filter(a-> a.getNombre().equalsIgnoreCase(ataque)).mapToInt(Ataque::getDAÑO).max().orElse(0);

        Ataque ataque1 = p1.getAtaques().stream().filter(a-> a.getNombre().equalsIgnoreCase(ataque))
                .filter(a-> a.getDAÑO() == dañoMax)
                .findFirst().orElseThrow(() -> new DBException("No se encuentra el ataque"));


        if (ataque1.getKI_NECESARIO() <=p1.getKi()){
            p1.restarKi(ataque1.getKI_NECESARIO());
            p2.recibirDaño(ataque1.getDAÑO());
            p1.eliminarAtaque(ataque1);
            if (p2.isMuerto()){
                System.out.println(p2.getNombre() + " esta muerto");
            }else {
                System.out.println("A "+ p2.getNombre()+ " le quedan "+ p2.getVida());
            }
        }
        else {
            throw new DBException("No puede lanzar ese ataque, no tiene el suficiente ki");
        }

    }

    public void eliminarAtaquesInferioresANivel(int nivel) {
        for (Personaje p : personajes) {
            p.getAtaques().removeIf(a -> a.getPERFECCION_DEL_ATAQUE() < nivel);
        }
    }

    public Map<TRaza, List<Personaje>> devuelveMapaRazas() {
        Map<TRaza,List<Personaje>> mapaDeRazas = new HashMap<>();
        for (TRaza raza : TRaza.values()){
            mapaDeRazas.put(raza, personajes.stream().filter(p-> p.getRaza() == raza).toList());
        }
        return mapaDeRazas;
    }

}
