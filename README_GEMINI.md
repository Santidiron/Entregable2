# GeminiService y Reporte Inteligente - Implementación

## Descripción General

Esta implementación agrega integración con la API de Google Gemini para generar reportes inteligentes sobre la transición de empleados entre áreas de la empresa.

## Componentes Implementados

### 1. GeminiService.java

Servicio que gestiona la comunicación con la API de Gemini para generar análisis inteligentes.

**Características principales:**
- Lee la API_KEY desde la variable de entorno `GEMINI_API_KEY`
- Realiza peticiones HTTP POST a la API de Gemini
- Endpoint: `https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent`
- Construye prompts detallados con información del empleado, áreas y CV
- Parsea respuestas JSON sin librerías externas
- Manejo robusto de errores y timeouts (30 segundos)
- Sistema de fallback cuando la API no está disponible o configurada

**Métodos principales:**
- `generarReporteInteligente(Empleado, List<Area>)`: Genera el reporte
- `isAPIKeyConfigured()`: Verifica si la API key está configurada
- `construirPrompt()`: Construye el prompt para Gemini
- `hacerRequestAPI()`: Realiza la petición HTTP
- `parsearRespuesta()`: Parsea la respuesta JSON
- `generarFallbackReport()`: Genera reporte básico cuando la API falla

### 2. ReporteInteligenteWindow.java

Ventana Swing para la interfaz gráfica del sistema de reportes.

**Características principales:**
- ComboBox para selección de empleado
- Panel de información básica del empleado seleccionado
- Lista de áreas con checkboxes para selección múltiple
- Botón "Generar Reporte con IA" (🤖)
- Indicador de carga (⏱) durante el procesamiento
- Área de texto para mostrar el resultado del análisis
- Botón "Guardar Reporte" (💾) para exportar a archivo de texto
- Procesamiento en background usando SwingWorker para no congelar la UI
- Advertencias visuales cuando la API key no está configurada

### 3. TestGeminiService.java

Clase de pruebas para validar la funcionalidad del servicio.

**Pruebas implementadas:**
- Inicialización del servicio
- Verificación de configuración de API key
- Generación de reportes en modo fallback
- Manejo de empleados con y sin CV
- Selección de múltiples áreas para análisis

## Uso

### Configuración de la API Key

```bash
export GEMINI_API_KEY="tu_api_key_aqui"
```

### Ejecutar la ventana de reportes

```java
// Crear datos de prueba
List<Empleado> empleados = new ArrayList<>();
List<Area> areas = new ArrayList<>();

// Agregar empleados y áreas...

// Lanzar ventana
ReporteInteligenteWindow window = new ReporteInteligenteWindow(empleados, areas);
window.setVisible(true);
```

### Ejecutar pruebas

```bash
javac TestGeminiService.java
java TestGeminiService
```

## Modo Fallback

Cuando la API de Gemini no está disponible o no está configurada, el sistema automáticamente genera un reporte básico con:
- Información del empleado
- Lista de áreas disponibles
- Análisis básico que sugiere evaluación manual
- Mensaje claro sobre el motivo del fallback

## Características de Seguridad

- Timeout de 30 segundos para evitar bloqueos
- Manejo robusto de excepciones
- Validación de datos de entrada
- Escape correcto de caracteres especiales en JSON
- No expone credenciales en logs o mensajes de error

## Formato del Prompt

El sistema construye un prompt detallado que incluye:
1. Información del empleado (nombre, legajo, antigüedad, salario, etc.)
2. Contenido completo del CV del empleado
3. Descripción de cada área disponible con su presupuesto
4. Pregunta específica sobre ventajas y desventajas de cada transición
5. Criterios de análisis (habilidades, experiencia, potencial, beneficios, desafíos)

## Archivos de Prueba

Se incluyen CVs de ejemplo en `test_data/`:
- `cv1.txt`: CV de desarrollador senior
- `cv2.txt`: CV de especialista en marketing

## Interfaz de Usuario

![UI Screenshot](https://github.com/user-attachments/assets/2dfbc7ff-5b4e-47aa-ab52-5b418f690fe7)

La interfaz incluye:
- Diseño intuitivo con paneles organizados
- Feedback visual durante el procesamiento
- Guardado de reportes con JFileChooser
- Mensajes de error claros y útiles

## Requisitos

- Java 11+ (usa HttpURLConnection)
- Swing para la interfaz gráfica
- Variable de entorno GEMINI_API_KEY (opcional, funciona sin ella en modo fallback)

## Compilación

```bash
javac GeminiService.java ReporteInteligenteWindow.java
```

## Notas

- El sistema usa únicamente bibliotecas estándar de Java
- No requiere dependencias externas
- Funciona completamente offline en modo fallback
- El parsing JSON es simple pero suficiente para la API de Gemini
