import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MainAlumnos {

    // Ruta base donde se organizarán todos los logs procesados
    public final static Path RUTA_DESTINO = Path.of("./logs_procesados");

    // Patrón para identificar ficheros de log con el formato: server{N}_app{N}.log
    private static final Pattern patron = Pattern.compile("^(?<nombreServidor>\\p{L}+\\d)_(?<nombreAPP>\\p{L}+[1-2]).(?<extension>\\p{L}{3})$"); //TODO: crear el patrón con la regexp del nombre del archivo

    // Patrón para parsear líneas de log de la app1
    // Formato esperado: yyyy/MM/dd HH:mm:ss - [NIVEL] - Mensaje
    private final static Pattern patronLogApp1 = Pattern.compile("(?<fecha>\\d{4}/\\d{1,2}/\\d{1,2})\\s(?<hora>(\\d{2}:){2}\\d{2})\\s-\\s\\[(?<tipoError>\\p{Upper}+)\\]\\s-\\s(?<motivo>(?:\\p{L}+\\s)*\\p{L}+.)"); //TODO: crear el patrón con la regexp del formato de logs de la app1. Consejo, usa grupos de captura nombrados.

    // Patrón para parsear líneas de log de la app2
    // Formato esperado: [dd-MM-yyyy|HH:mm:ss] <NIVEL> Mensaje
    private final static Pattern patronLogApp2 = Pattern.compile("(?:\\[(?<fecha>(?:\\d{2}-){2}\\d{4}))\\|(?<hora>(?:\\d{2}:){2}\\d{2})\\]\\s\\<(?<tipoError>\\p{Upper}+)\\>\\s(?<motivo>(?:\\p{L}+\\s)*\\p{L}+.)"); //TODO: ídem del anterior pero con el formato de la app2

    public static void main(String[] args) {

        // Generamos el entorno de prueba con los logs desordenados
        GeneradorLogsExamen.execute();

        // Limpiamos la carpeta de destino antes de empezar para evitar
        // mezclar resultados de ejecuciones anteriores
        eliminarDirectorioRecursivo(RUTA_DESTINO);

        Path carpetaRaiz = Path.of("./entorno_examen_logs");
        organizaCaosLogs(carpetaRaiz);
    }

    /**
     * Recorre recursivamente la carpeta raíz en busca de ficheros de log,
     * los mueve a su ubicación organizada y extrae los errores de cada uno.
     *
     * @param carpetaRaiz Ruta del directorio origen donde están los logs mezclados.
     */
    private static void organizaCaosLogs(Path carpetaRaiz) {

        // Lista acumuladora de todos los errores encontrados en todos los ficheros
        List<DetalleError> todosLosErrores = new ArrayList<>();

        try (Stream<Path> punteros = Files.walk(carpetaRaiz, 3)) {

            // Solo procesamos ficheros cuyo nombre coincide con el patrón esperado
            punteros.forEach(p -> {

                Matcher m = patron.matcher(p.getFileName().toString());

                if (m.find()) {

                    String nombreCompleto = m.group();
                    String servidor = m.group("nombreServidor");
                    String app = m.group("nombreAPP");
                    String extension = m.group("extension");


                    Path directorioNuevo = RUTA_DESTINO.resolve(Path.of(servidor)).resolve(Path.of(app));

                    try {
                        if (extension.equalsIgnoreCase("log")) {
                            // Creamos los directorios intermedios si no existen
                            Files.createDirectories(directorioNuevo);

                            String nombreNuevo = "procesado_" + nombreCompleto;


                            // Movemos el fichero a su nueva ubicación organizada
                            Path nuevoPath = Files.move(p, directorioNuevo.resolve(nombreNuevo));

                            // Inspeccionamos el fichero ya movido en busca de líneas de nivel ERROR,
                            // usando el patrón correspondiente según la aplicación

                            if (app.equalsIgnoreCase("app1")) {
                                todosLosErrores.addAll(buscarErrores(nuevoPath, patronLogApp1, servidor, app));
                            } else {
                                todosLosErrores.addAll(buscarErrores(nuevoPath, patronLogApp2, servidor, app));
                            }
                        }
                    } catch (IOException | LogException e) {
                        System.out.printf("%s %n", e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Una vez procesados todos los ficheros, escribimos el reporte global de errores
        escribirErrorAFichero(todosLosErrores);
    }

    /**
     * Lee un fichero de log línea a línea y extrae aquellas cuyo nivel sea ERROR.
     *
     * @param p          Ruta del fichero a analizar
     * @param logPattern Patrón regex correspondiente al formato de la aplicación
     * @param server     ID del servidor de origen
     * @param app        ID de la aplicación de origen
     * @return Lista de errores encontrados en el fichero
     * @throws LogException Si ocurre un error al leer el fichero
     */
    private static List<DetalleError> buscarErrores(Path p, Pattern logPattern, String server, String app) throws LogException {

        try (Stream<String> lineas = Files.lines(p)) {
            // Aplicamos el patrón a cada línea para obtener un Matcher
            return lineas.map(logPattern::matcher)
                    // Descartamos las líneas que no coinciden con el formato esperado
                    .filter(Matcher::find)

                    .filter(m -> m.group("tipoError").equalsIgnoreCase("error"))
                    .map(m -> {
                        String fecha = m.group("fecha");
                        String hora = m.group("hora");
                        String motivo = m.group("motivo");

                        return new DetalleError(server, app, fecha, hora, motivo);
                    }).toList();
        } catch (IOException e) {
            throw new LogException("Error " + e.getMessage());
        }
        // Construimos el objeto DetalleError con los datos extraídos
    }

    /**
     * Serializa la lista de errores a JSON con formato legible
     * y la escribe en un fichero de reporte dentro de la carpeta de destino.
     *
     * @param errores Lista de errores recolectados a escribir.
     */
    private static void escribirErrorAFichero(List<DetalleError> errores) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        int erroresTotal = errores.size();

        String json = gson.toJson(new ReporteErrores(erroresTotal, errores));

        Path ruta = Path.of("./errores.json");

        try {
            Files.writeString(ruta, json, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.printf("%s %n", e.getMessage());
        }
    }

    /**
     * Borra un directorio y todo su contenido de forma recursiva.
     * Los ficheros y subdirectorios se eliminan antes que sus padres
     * para evitar errores al borrar directorios no vacíos.
     *
     * @param ruta Ruta del directorio a eliminar.
     */
    private static void eliminarDirectorioRecursivo(Path ruta) {
        if (Files.exists(ruta)) {
            try (Stream<Path> walk = Files.walk(ruta)) {
                walk.sorted(java.util.Comparator.reverseOrder()) // Borra hijos antes que padres
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                System.err.println("No se pudo borrar: " + p + " -> " + e.getMessage());
                            }
                        });
                System.out.println("Carpeta de destino limpia.");
            } catch (IOException e) {
                System.err.println("Error al intentar limpiar el directorio: " + e.getMessage());
            }
        }
    }
}