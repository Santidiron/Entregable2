# Implementación de Persistencia y GestorCV

Este proyecto implementa las clases de persistencia y gestión de CVs según el diagrama UML.

## Clases Implementadas

### 1. Persistencia
Clase para gestionar la serialización y deserialización del sistema.

**Métodos:**
- `guardarDatos(String nombreArchivo, Object objeto)`: Serializa y guarda un objeto en archivo
- `cargarDatos(String nombreArchivo)`: Deserializa y carga un objeto desde archivo
- `existeArchivo(String nombreArchivo)`: Verifica si un archivo existe

**Características:**
- Manejo automático de directorios (crea carpetas si no existen)
- Validación de parámetros
- Manejo de excepciones (IOException, ClassNotFoundException)

### 2. GestorCV
Clase para gestionar archivos de currículums en la carpeta `cvs/`.

**Métodos:**
- `procesarCV(File archivoCV)`: Procesa un archivo CV y extrae datos
- `extraerDatos(String contenido)`: Extrae información del contenido del CV
- `escribirCV(String nombreArchivo, String contenido)`: Escribe un CV en la carpeta cvs
- `leerCV(String nombreArchivo)`: Lee un CV de la carpeta cvs
- `validarArchivo(File archivo)`: Valida formato de archivo CV
- `listarCVs()`: Lista todos los CVs en la carpeta
- `eliminarCV(String nombreArchivo)`: Elimina un CV
- `existeCV(String nombreArchivo)`: Verifica si existe un CV

**Características:**
- Creación automática de carpeta `cvs/` al instanciar la clase
- Validación de archivos (formatos: .txt, .pdf, .doc, .docx)
- Extracción de datos básicos (nombre, apellido, cédula, celular, email)
- Manejo robusto de errores

### 3. Sistema
Clase principal que gestiona todas las entidades del sistema.

**Atributos:**
- `List<Area> areas`: Lista de áreas
- `List<Manager> managers`: Lista de managers
- `List<Empleado> empleados`: Lista de empleados
- `List<Movimiento> movimientos`: Lista de movimientos

**Métodos:**
- `agregarArea(Area area)`: Agrega un área al sistema
- `agregarManager(Manager manager)`: Agrega un manager al sistema
- `registrarEmpleado(Empleado empleado)`: Registra un empleado
- `registrarMovimiento(Movimiento movimiento)`: Registra un movimiento

**Características:**
- Implementa `Serializable` para permitir persistencia
- Inicialización automática de colecciones
- Validación de objetos nulos

## Modificaciones en Clases Existentes

### Clases de Dominio (Serializable)
Se agregó la implementación de `Serializable` en:
- `Area`
- `Manager`
- `Empleado`
- `Movimiento`

Esto permite que todas las entidades del sistema puedan ser serializadas y persistidas.

### Corrección de Errores de Compilación
Se corrigieron errores de sintaxis en:
- `Area.java`: Error en método `getNombre()` y `getDescripcion()`
- `Empleado.java`: Error en método `getLegajo()` y `setArea()`
- `Manager.java`: Modificador de acceso incorrecto (private → public)

## Estructura de Archivos

```
Entregable2/
├── Area.java              # Clase de dominio
├── Manager.java           # Clase de dominio
├── Empleado.java          # Clase de dominio
├── Movimiento.java        # Clase de dominio
├── Sistema.java           # Clase principal del sistema
├── Persistencia.java      # Gestión de serialización
├── GestorCV.java          # Gestión de archivos CV
├── DemoCompleto.java      # Demo completa del sistema
├── TestPersistenciaGestorCV.java  # Tests unitarios
├── cvs/                   # Carpeta para archivos CV (creada automáticamente)
└── sistema_completo.dat   # Archivo de persistencia del sistema
```

## Uso

### Ejemplo de Persistencia

```java
// Crear instancia de Persistencia
Persistencia persistencia = new Persistencia();

// Crear y configurar sistema
Sistema sistema = new Sistema();
sistema.agregarArea(new Area(1, "IT", "Tecnología", 100000, null));

// Guardar sistema
persistencia.guardarDatos("sistema.dat", sistema);

// Cargar sistema
Sistema sistemaRecuperado = (Sistema) persistencia.cargarDatos("sistema.dat");
```

### Ejemplo de GestorCV

```java
// Crear instancia de GestorCV (crea carpeta cvs/ automáticamente)
GestorCV gestorCV = new GestorCV();

// Escribir un CV
String contenidoCV = "Nombre: Juan\nApellido: Perez\nCedula: 12345678\n";
gestorCV.escribirCV("juan_perez.txt", contenidoCV);

// Leer un CV
String contenido = gestorCV.leerCV("juan_perez.txt");

// Extraer datos
Map<String, String> datos = gestorCV.extraerDatos(contenido);
System.out.println("Nombre: " + datos.get("nombre"));

// Listar todos los CVs
List<String> cvs = gestorCV.listarCVs();
```

## Pruebas

Para ejecutar los tests:

```bash
# Compilar
javac TestPersistenciaGestorCV.java

# Ejecutar tests
java TestPersistenciaGestorCV
```

Para ejecutar la demo completa:

```bash
# Compilar
javac DemoCompleto.java

# Ejecutar demo
java DemoCompleto
```

## Validaciones Implementadas

### Persistencia
- Validación de nombres de archivo no nulos ni vacíos
- Validación de objetos a serializar no nulos
- Creación automática de directorios padre
- Manejo de FileNotFoundException cuando se intenta cargar archivo inexistente

### GestorCV
- Validación de archivos no nulos
- Validación de existencia de archivos
- Validación de formatos de archivo (.txt, .pdf, .doc, .docx)
- Validación de nombres de archivo y contenido
- Creación automática de carpeta cvs/
- Manejo de IOException para operaciones de E/S

## Manejo de Errores

Todas las clases implementan un manejo robusto de errores:
- Excepciones checked: `IOException`, `ClassNotFoundException`, `FileNotFoundException`
- Excepciones unchecked: `IllegalArgumentException` para parámetros inválidos
- Mensajes de error descriptivos

## Cumplimiento del UML

La implementación sigue fielmente el diagrama UML proporcionado:
- ✅ Persistencia con métodos guardarDatos, cargarDatos, existeArchivo
- ✅ GestorCV con métodos procesarCV, extraerDatos
- ✅ Sistema con listas de entidades y métodos de gestión
- ✅ Todas las clases de dominio implementan Serializable
- ✅ Creación automática de carpeta cvs/

## Autores
- Diego Rocabado
- Santiago Dirón
