package model;

import java.time.YearMonth;

public class Nomina {
    private YearMonth mes;
    private double salarioBase;
    private double porcentajeRetencion;

    public Nomina(YearMonth mes, double salarioBase, double porcentajeRetencion) {
        this.mes = mes;
        this.salarioBase = salarioBase;
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public YearMonth getMes() {
        return mes;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    /**
     * Metodo para calcular el salario neto de la nómina
     * @return el salario neto
     */

    public double getSalarioNeto() {
        return salarioBase * (1 - (porcentajeRetencion / 100));
    }

}
