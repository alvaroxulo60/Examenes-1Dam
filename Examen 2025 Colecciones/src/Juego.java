public class Juego {


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
            juego.personajeConAtaqueMasPoderoso();
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

    }


    public void agregarPersonaje(Personaje personaje) throws DBException {

    }

    public void personajeConMasAtaques() throws DBException {

    }

    public void todosLosAtaquesOrdenadosNombre() {

    }

    public void todosLosAtaquesOrdenadosDamage() {

    }

    public Ataque ataqueMasDañino(Personaje p1, Personaje p2) throws DBException{

    }

    public void atacar(Personaje p1, Personaje p2, String ataque) throws DBException {

    }

    public void eliminarAtaquesInferioresANivel(int nivel){

    }

    public Map<TRaza, List<Personaje>> devuelveMapaRazas(){

    }

}
