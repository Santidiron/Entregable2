//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GeminiService {
    
    private static final String MODEL_FLASH_2_0 = "gemini-2.0-flash";
    private static final String MODEL_PRO_2_0 = "gemini-2.0-pro";
    private static final String MODEL_FLASH_1_5 = "gemini-1.5-flash";
    private static final String MODEL_PRO_1_5 = "gemini-1.5-pro";

    private static final String GEMINI_API_BASE = "https://generativelanguage.googleapis.com/v1beta/models/";

    private static final String GEMINI_API_ENDPOINT = GEMINI_API_BASE + MODEL_FLASH_2_0 + ":generateContent";

    private static final int TIMEOUT_MS = 30000;
    private static final int MAX_RETRIES = 3;

    private String apiKey;
    
    public GeminiService() {
        this.apiKey = System.getenv("ERP_API_KEY");

        if (this.apiKey == null || this.apiKey.trim().isEmpty()) {
            this.apiKey = System.getenv("GEMINI_API_KEY");
        }
    }
    
    public String generarReporteInteligente(Empleado empleado, List<Area> areas) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            String mensaje = "API Key no configurada.\n\n" +
                           "Para habilitar la integración con IA de Google Gemini:\n" +
                           "1. Ve a https://aistudio.google.com/app/apikey\n" +
                           "2. Obtén tu API Key gratuita\n" +
                           "3. Edita GeminiService.java y configura DEFAULT_API_KEY\n" +
                           "   O configura la variable de entorno ERP_API_KEY\n\n" +
                           "El sistema continuará funcionando con análisis básico.";
            return generarFallbackReport(empleado, areas, mensaje);
        }
        
        try {
            String prompt = construirPrompt(empleado, areas);
            
            String response = hacerRequestAPI(prompt);
            
            return parsearRespuesta(response);
            
        } catch (SocketTimeoutException e) {
            return generarFallbackReport(empleado, areas, "Timeout: La API de Gemini no respondió a tiempo.");
        } catch (IOException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("429")) {
                return generarFallbackReport(empleado, areas,
                    "⚠️ LÍMITE DE CUOTA EXCEDIDO (Error 429)\n\n" +
                    "Tu API Key gratuita de Gemini ha alcanzado el límite temporal.\n\n" +
                    "Límites de la versión GRATUITA:\n" +
                    "• 15 peticiones por minuto\n" +
                    "• 1,500 peticiones por día\n" +
                    "• Debes esperar ~30 segundos entre peticiones\n\n" +
                    "💡 Soluciones:\n" +
                    "1. Espera unos segundos y vuelve a intentar\n" +
                    "2. Usa el reporte básico (modo fallback actual)\n" +
                    "3. Consulta tu uso en: https://aistudio.google.com/app/apikey\n\n" +
                    "El sistema continúa funcionando normalmente en modo básico.");
            } else if (mensaje != null && mensaje.contains("403")) {
                return generarFallbackReport(empleado, areas,
                    "🔒 API KEY BLOQUEADA O INVÁLIDA (Error 403)\n\n" +
                    "La API Key actual ha sido deshabilitada por seguridad.\n\n" +
                    "Posibles causas:\n" +
                    "• La API Key fue reportada como filtrada\n" +
                    "• La API Key es inválida o ha expirado\n" +
                    "• No tienes permisos para usar esta API\n\n" +
                    "🔑 SOLUCIÓN INMEDIATA:\n" +
                    "1. Ve a: https://aistudio.google.com/app/apikey\n" +
                    "2. CREA UNA NUEVA API KEY (la actual está bloqueada)\n" +
                    "3. Configura la nueva API Key usando uno de estos métodos:\n" +
                    "   • Variable de entorno: ERP_API_KEY o GEMINI_API_KEY\n" +
                    "   • Ejecuta: configurar_api_key.bat (si existe)\n\n" +
                    "⚠️ IMPORTANTE: NO compartas tu API Key en repositorios públicos.\n\n" +
                    "El sistema continúa funcionando en modo básico.");
            }
            return generarFallbackReport(empleado, areas, "Error de conexión: " + e.getMessage());
        } catch (Exception e) {
            return generarFallbackReport(empleado, areas, "Error inesperado: " + e.getMessage());
        }
    }
    
    private String construirPrompt(Empleado empleado, List<Area> areas) throws IOException {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("Eres un consultor experto en recursos humanos. Analiza la siguiente información y proporciona un análisis detallado.\n\n");
        
        prompt.append("INFORMACIÓN DEL EMPLEADO:\n");
        prompt.append("Nombre: ").append(empleado.getNombre()).append(" ").append(empleado.getApellido()).append("\n");
        prompt.append("Legajo: ").append(empleado.getLegajo()).append("\n");
        prompt.append("Cédula: ").append(empleado.getCedula()).append("\n");
        prompt.append("Antigüedad: ").append(empleado.getAntiguedad()).append(" años\n");
        prompt.append("Salario Mensual: $").append(empleado.getSalarioMensual()).append("\n");
        
        if (empleado.getArea() != null) {
            prompt.append("Área actual: ").append(empleado.getArea().getNombre()).append("\n");
        }
        
        prompt.append("\nCONTENIDO DEL CV:\n");
        if (empleado.getPathCV() != null && !empleado.getPathCV().isEmpty()) {
            try {
                String cvContent = leerContenidoCV(empleado.getPathCV());
                prompt.append(cvContent).append("\n");
            } catch (IOException e) {
                prompt.append("(CV no disponible: ").append(e.getMessage()).append(")\n");
            }
        } else {
            prompt.append("(No se ha especificado un CV para este empleado)\n");
        }
        
        prompt.append("\nÁREAS DISPONIBLES PARA ANÁLISIS:\n");
        for (Area area : areas) {
            prompt.append("- ").append(area.getNombre()).append(": ").append(area.getDescripcion()).append("\n");
            prompt.append("  Presupuesto anual: $").append(area.getPresupuestoAnual()).append("\n");
        }
        
        prompt.append("\nPREGUNTA:\n");
        prompt.append("¿Cuáles son las ventajas y desventajas de mover a este empleado a cada una de las áreas mencionadas? ");
        prompt.append("Por favor, proporciona un análisis detallado para cada área, considerando:\n");
        prompt.append("1. Las habilidades y experiencia del empleado según su CV\n");
        prompt.append("2. Las características y necesidades de cada área\n");
        prompt.append("3. El potencial de crecimiento del empleado en cada área\n");
        prompt.append("4. Los beneficios para la organización\n");
        prompt.append("5. Los posibles desafíos de la transición\n");
        
        return prompt.toString();
    }
    
    private String leerContenidoCV(String pathCV) throws IOException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathCV), StandardCharsets.UTF_8);
            return String.join("\n", lines);
        } catch (Exception e) {
            List<String> lines = Files.readAllLines(Paths.get("." + pathCV), StandardCharsets.UTF_8);
            return String.join("\n", lines);
        }
    }
    
    private String hacerRequestAPI(String prompt) throws IOException {
        URL url = new URL(GEMINI_API_ENDPOINT + "?key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(TIMEOUT_MS);
            connection.setReadTimeout(TIMEOUT_MS);
            connection.setDoOutput(true);
            
            String jsonRequest = construirJSONRequest(prompt);
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                String errorMsg = "";
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line);
                    }
                    errorMsg = error.toString();
                } catch (Exception e) {
                    errorMsg = "No se pudo leer el mensaje de error";
                }
                throw new IOException("API returned error code: " + responseCode + " - " + errorMsg);
            }
            
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
            
        } finally {
            connection.disconnect();
        }
    }
    
    private String construirJSONRequest(String prompt) {
        String escapedPrompt = prompt.replace("\\", "\\\\")
                                     .replace("\"", "\\\"")
                                     .replace("\n", "\\n")
                                     .replace("\r", "\\r")
                                     .replace("\t", "\\t");
        
        return "{"
            + "\"contents\":[{"
            + "\"parts\":[{\"text\":\"" + escapedPrompt + "\"}]"
            + "}]"
            + "}";
    }
    
    private String parsearRespuesta(String jsonResponse) {
        try {
            int textIndex = jsonResponse.indexOf("\"text\"");
            if (textIndex == -1) {
                return "Error: No se pudo encontrar el texto en la respuesta de la API.";
            }
            
            int startQuote = jsonResponse.indexOf("\"", textIndex + 7);
            if (startQuote == -1) {
                return "Error: Formato de respuesta inválido.";
            }
            
            int endQuote = startQuote + 1;
            while (endQuote < jsonResponse.length()) {
                if (jsonResponse.charAt(endQuote) == '"' && jsonResponse.charAt(endQuote - 1) != '\\') {
                    break;
                }
                endQuote++;
            }
            
            if (endQuote >= jsonResponse.length()) {
                return "Error: No se pudo parsear la respuesta de la API.";
            }
            
            String text = jsonResponse.substring(startQuote + 1, endQuote);
            
            text = text.replace("\\n", "\n")
                      .replace("\\r", "\r")
                      .replace("\\t", "\t")
                      .replace("\\\"", "\"")
                      .replace("\\\\", "\\");
            
            return text;
            
        } catch (Exception e) {
            return "Error al parsear la respuesta: " + e.getMessage();
        }
    }
    
    private String generarFallbackReport(Empleado empleado, List<Area> areas, String razonFallo) {
        StringBuilder fallback = new StringBuilder();
        
        fallback.append("=== REPORTE BÁSICO (MODO FALLBACK) ===\n\n");
        fallback.append("Motivo: ").append(razonFallo).append("\n\n");
        
        fallback.append("INFORMACIÓN DEL EMPLEADO:\n");
        fallback.append("Nombre: ").append(empleado.getNombre()).append(" ").append(empleado.getApellido()).append("\n");
        fallback.append("Legajo: ").append(empleado.getLegajo()).append("\n");
        fallback.append("Antigüedad: ").append(empleado.getAntiguedad()).append(" años\n");
        fallback.append("Salario: $").append(empleado.getSalarioMensual()).append("\n");
        
        if (empleado.getArea() != null) {
            fallback.append("Área actual: ").append(empleado.getArea().getNombre()).append("\n");
        }
        
        fallback.append("\nÁREAS DISPONIBLES:\n");
        for (Area area : areas) {
            fallback.append("\n").append(area.getNombre()).append(":\n");
            fallback.append("  Descripción: ").append(area.getDescripcion()).append("\n");
            fallback.append("  Presupuesto: $").append(area.getPresupuestoAnual()).append("\n");
            fallback.append("  \n");
            fallback.append("  Análisis básico:\n");
            fallback.append("  - Requiere evaluación manual de la compatibilidad del empleado\n");
            fallback.append("  - Considerar experiencia previa y habilidades según CV\n");
            fallback.append("  - Evaluar necesidades específicas del área\n");
        }
        
        fallback.append("\n\nNOTA: Este es un reporte básico. Para obtener un análisis detallado ");
        fallback.append("impulsado por IA, configure la API de Gemini correctamente y vuelva a intentar.\n");
        
        return fallback.toString();
    }
    
    public boolean isAPIKeyConfigured() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
