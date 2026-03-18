package app;

import model.Empleado;
import model.Nomina;
import model.TDepartamento;

import java.time.YearMonth;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Principal {
    private List<Empleado> empleados;

    public Principal() {
        empleados = new ArrayList<>();
    }

    private List<Empleado> getEmpleados() {
        return empleados;
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.inicializarEmpleados();

        // Este empleado obviamente existe
        principal.buscarEmpleado(principal.empleados.getFirst().getDni());

        // Este empleado no existe
        principal.buscarEmpleado("12345678A");
        System.out.println();

        System.out.println("#############");
        System.out.println("Número de empleados por departamento:");
        principal.mostrarNumeroEmpleadosPorDepartamento();
        System.out.println();

        System.out.println("#############");
        System.out.printf("Porcentaje de retención para los trabajadores que llevan menos de 1 año trabajando: %.2f%%%n",
                principal.porcentajeRetencionTrabajadoresNuevos());
        System.out.println();

        System.out.println("#############");
        principal.empleadosQueMasCobran();
        System.out.println();

        System.out.println("#############");
        System.out.println("Coste total en salarios");
        principal.costeTotalSalarios(YearMonth.of(2024, 12));
        System.out.println();

        System.out.println("#############");
        System.out.println("Todos los empleados ordenados por antigüedad");
        principal.mostrarTodosEmpleadosOrdenados();
        System.out.println();


    }

    /**
     * Metodo para buscar e imprimir los datos de un empleado que tenga el dni pasado por parámetros
     * @param dni
     */
    private void buscarEmpleado(String dni) {
        //Creamos un flujo para filtrar los empleados por el dni y luego con el findFirst intentamos encontrar el primero.
        Empleado empleadoConDni = empleados.stream().filter(empleado -> empleado.getDni().equalsIgnoreCase(dni))
                .findFirst().orElse(null);


        if (empleadoConDni == null) {
            System.out.println("No se encuentra un empleado con ese dni.");
        } else {
            System.out.println(empleadoConDni);
        }
    }

    /**
     * Metodo para imprimir la cantidad de empleados que hay por cada departamento
     */
    private void mostrarNumeroEmpleadosPorDepartamento() {
        StringBuilder sb = new StringBuilder();

        // Hacemos un bucle para recorrer cada departamento
        for (TDepartamento departamento : TDepartamento.values()) {
            //Por cada departamento buscamos en los empleados los que pertenecen a ese departamento y luego contamos los empleados que hay en el flujo resultante después del filter
            sb.append(departamento).append(": ").append(empleados.stream().filter(e -> e.getDepartamento() == departamento).count()).append(System.lineSeparator());
        }
        System.out.println(sb);
    }

    /**
     * Metodo para calcular la media del porcentaje de retencion de los trabajadores que empezaron a trabajar hace menos de un año
     * @return La media del porcentaje de retención
     */
    private double porcentajeRetencionTrabajadoresNuevos() {
        // Calculamos la fecha de hace un año
        YearMonth fechaLimiteDeNominas = YearMonth.now().minusMonths(12);

        //Calculamos la media teniendo en cuenta los empleados que empezaron a trabajar hace menos de un año
        //Luego cogemos la última nómina de cada empleado y calculamos la media haciendo un map del porcentaje de retención

        double media = empleados.stream().filter(e -> e.fechaPrimeraNomina().isAfter(fechaLimiteDeNominas))
                .map(Empleado::getUltimaNomina)
                .mapToDouble(Nomina::getPorcentajeRetencion).average().orElse(-1);

        if (media == -1) {
            System.out.println("No se ha podido calcular la media");
        }
        return media;
    }

    /**
     * Metodo para imprimir el o los empleados que más han cobrado en la última nómina
     */
    private void empleadosQueMasCobran() {
        //Calculamos el salario neto más alto de las últimas nóminas para luego hacer comprobaciones
        double salarioNetoMasAlto = empleados.stream().map(Empleado::getUltimaNomina)
                .mapToDouble(Nomina::getSalarioNeto).max().orElse(-1);

        // Hacemos una nueva lista para filtrar los empleados que tengan ese salario neto en la última nómina
        List<Empleado> empleadosQueMasCobran = empleados.stream().filter(e -> e.getUltimaNomina().getSalarioNeto() == salarioNetoMasAlto).toList();


        //Por cada uno lo añadimos a un StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("Empleados que mas cobran ").append("%.2f".formatted(salarioNetoMasAlto)).append(":").append(System.lineSeparator());
        for (Empleado e : empleadosQueMasCobran){
            sb.append(e).append(System.lineSeparator());
        }

        System.out.println(sb);
    }

    /**
     *Metodo para dada una fecha específica calcular cuanto le costó a la empresa pagar las nóminas de los empleados
     * @param fecha
     */
    private void costeTotalSalarios(YearMonth fecha) {

        // Utilizamos un flatmap para conseguir todas las nóminas de los empleados
        double costeTotal = empleados.stream().flatMap(e -> e.getNominas().stream())
                //Filtramos que la fecha sea la misma pasada por parámetros
                .filter(n -> n.getMes().equals(fecha))
                //Lo convertimos a un map de doble de los salarios base y lo sumamos
                .mapToDouble(Nomina::getSalarioBase).sum();

        System.out.printf("El coste total para la fecha %s es: %.2f",fecha,costeTotal);
    }

    /**
     * Metodo para ordenar la lista de empleados por antigüedad en la empresa y si la fecha es la misma por apellidos alfabéticamente
     */
    private void mostrarTodosEmpleadosOrdenados() {
        //Creamos una lista la cual va a guardar la lista de los empleados ordenados
           List<Empleado> empleadosOrdenados = empleados.stream().sorted( (e1,e2) -> {
               //Si la fecha de e1 va antes de e2 devuelve -1 y si es al contrario 1
                if (e1.fechaPrimeraNomina().isBefore(e2.fechaPrimeraNomina())){
                    return -1;
                }
                if (e1.fechaPrimeraNomina().isAfter(e2.fechaPrimeraNomina())){
                    return 1;
                }
                //Después comparamos por el apellido en caso de que las fechas sean iguales
                return e1.getApellido().compareToIgnoreCase(e2.getApellido());
            }).toList();

           StringBuilder sb  = new StringBuilder();
            //Por cada empleado lo añadimos a un StringBuilder
           for (Empleado e : empleadosOrdenados){
               sb.append(e).append(". Lleva en la empresa: ").append(e.fechaPrimeraNomina()).append(System.lineSeparator());
           }
        System.out.println(sb);
    }

    public void inicializarEmpleados() {
        Random random = new Random();

        // Configuración para generar 5 empleados por cada departamento
        for (TDepartamento dept : TDepartamento.values()) {
            // Los departamentos tendrán entre 3 y 7 empleados
            int numEmpleados = random.nextInt(5) + 3;
            for (int i = 1; i <= numEmpleados; i++) {
                String nombre = "Empleado " + i + "_" + dept.name().charAt(0);
                String apellido = "Apellido " + (random.nextInt(10) + i);
                String dni = (random.nextInt(90000000) + 10000000) + "X";

                Empleado emp = new Empleado(nombre, apellido, dni, dept);

                // Generamos entre 5 y 30 nóminas para cada uno
                // Algunos empezarán hace 30 meses, otros hace solo 5
                int numNominas = random.nextInt(26) + 5;
                YearMonth mesInicio = YearMonth.now().minusMonths(numNominas);

                for (int m = 0; m < numNominas; m++) {
                    YearMonth mesNomina = mesInicio.plusMonths(m);
                    // Salarios entre 1500 y 3500
                    double salarioBase = 1500 + (random.nextDouble() * 2000);
                    // Retenciones entre 10% y 22%
                    double retencion = 10 + (random.nextDouble() * 12);

                    emp.getNominas().add(new Nomina(mesNomina, salarioBase, retencion));
                }
                empleados.add(emp);
            }
        }
    }
}
