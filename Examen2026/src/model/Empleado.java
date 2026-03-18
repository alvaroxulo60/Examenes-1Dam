package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Empleado {
    private String nombre;
    private String apellido;
    private String dni;
    private TDepartamento departamento;
    private List<Nomina> nominas;

    public Empleado(String nombre, String apellido, String dni, TDepartamento departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.departamento = departamento;
        this.nominas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public TDepartamento getDepartamento() {
        return departamento;
    }

    public List<Nomina> getNominas() {
        return nominas;
    }

    @Override
    public String toString() {
        return "%s %s (%s) - Departamento: %s".formatted(nombre, apellido, dni, departamento);
    }

    /**
     * Metodo que devuelve la fecha de la primera nómina
     * @return La fecha de la primera nómina
     */
    public YearMonth fechaPrimeraNomina() {
        //Hacemos un flujo de nóminas para luego compararlas por los meses, coger la más antigua y de esa cogemos el mes
        return nominas.stream().min(Comparator.comparing(Nomina::getMes)).orElse(null).getMes();
    }

    /**
     * Metodo que devuelve la nómina más reciente del trabajador
     * @return La nómina más reciente
     */
    public Nomina getUltimaNomina() {
        // Mediante el flujo comparamos las nóminas por la fecha y cogemos la más reciente
        return nominas.stream().max(Comparator.comparing(Nomina::getMes)).orElse(null);
    }




}
