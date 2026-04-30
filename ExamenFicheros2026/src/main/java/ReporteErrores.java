import java.util.List;

public record ReporteErrores(int total_errores_encontrados, List<DetalleError> detalles) {}