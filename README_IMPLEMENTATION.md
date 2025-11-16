# Sistema de Gestión de Movimientos de Empleados

## Issue #11: Reporte de Movimientos y Exportación CSV

Este proyecto implementa una ventana de reporte de movimientos de empleados entre áreas con capacidades de filtrado y exportación a CSV.

## Archivos Principales

- **ReporteMovimientos.java**: Ventana GUI principal con tabla, filtros y exportación
- **Movimiento.java**: Clase de dominio para movimientos de empleados
- **Area.java**: Clase de dominio para áreas
- **Empleado.java**: Clase de dominio para empleados
- **Manager.java**: Clase de dominio para managers

## Archivos de Prueba

- **TestReporteMovimientos.java**: Tests de lógica de filtros y formateo
- **TestCSVExport.java**: Test de exportación real a archivo CSV
- **TestCSVEscaping.java**: Test de manejo de caracteres especiales en CSV

## Compilación

```bash
# Compilar todas las clases
javac *.java

# O compilar solo lo necesario
javac Area.java Manager.java Empleado.java Movimiento.java ReporteMovimientos.java
```

## Ejecución

### Ejecutar la aplicación GUI con datos de prueba

```bash
java ReporteMovimientos
```

Esto abrirá una ventana con datos de ejemplo que incluye:
- 5 movimientos de empleados
- 3 áreas (Desarrollo, Testing, Recursos Humanos)
- 3 empleados (Juan Pérez, María García, Carlos López)

### Ejecutar tests

```bash
# Test de lógica general
java TestReporteMovimientos

# Test de exportación CSV
java TestCSVExport

# Test de caracteres especiales en CSV
java TestCSVEscaping
```

## Funcionalidades Implementadas

### 1. Tabla de Movimientos
- ✅ Columnas: Mes, Área Origen, Área Destino, Empleado, Fecha
- ✅ Ordenamiento por mes (más reciente primero)
- ✅ Scroll para muchos registros
- ✅ Tabla no editable

### 2. Sistema de Filtros
- ✅ **Filtro por Mes**: ComboBox con valores 1-12 y opción "Todos"
- ✅ **Filtro por Área**: ComboBox con lista de áreas y opción "Todas" (filtra por origen O destino)
- ✅ **Filtro por Empleado**: Campo de texto con búsqueda insensible a mayúsculas
- ✅ **Botón "Aplicar Filtros"**: Aplica los criterios seleccionados
- ✅ **Botón "Limpiar Filtros"**: Resetea todos los filtros

### 3. Exportación a CSV
- ✅ **Botón "Exportar a CSV"**: Abre JFileChooser para guardar archivo
- ✅ **Formato**: Primera línea encabezados, siguientes líneas datos
- ✅ **Separador**: Coma (,)
- ✅ **Codificación**: UTF-8
- ✅ **Escape de caracteres**: Maneja comas y comillas en los datos
- ✅ **Validaciones**: Verifica datos antes de exportar
- ✅ **Manejo de errores**: Muestra mensajes claros de error
- ✅ **Confirmación**: Mensaje de éxito con ruta del archivo

### 4. Validaciones y Manejo de Errores
- ✅ Verificación de datos antes de exportar
- ✅ Manejo de errores de escritura
- ✅ Manejo de valores nulos (muestra "N/A")
- ✅ Mensajes informativos al usuario

## Uso de la Aplicación

### Aplicar Filtros

1. **Filtrar por mes**:
   - Seleccione un mes del 1 al 12 en el combo "Mes"
   - Haga clic en "Aplicar Filtros"

2. **Filtrar por área**:
   - Seleccione un área del combo "Área"
   - Haga clic en "Aplicar Filtros"
   - Nota: Filtra movimientos donde el área es origen O destino

3. **Buscar por empleado**:
   - Escriba parte del nombre o apellido en el campo "Empleado"
   - Haga clic en "Aplicar Filtros"
   - La búsqueda no distingue mayúsculas/minúsculas

4. **Combinar filtros**:
   - Configure múltiples filtros
   - Haga clic en "Aplicar Filtros"
   - Solo se mostrarán registros que cumplan TODOS los criterios

5. **Limpiar filtros**:
   - Haga clic en "Limpiar Filtros"
   - Se restauran los valores por defecto y se muestran todos los movimientos

### Exportar a CSV

1. Aplique los filtros deseados (o deje ver todos los datos)
2. Haga clic en "Exportar a CSV"
3. En el diálogo de archivo:
   - Navegue a la carpeta donde desea guardar
   - Ingrese el nombre del archivo (se agregará .csv automáticamente si no lo incluye)
   - Haga clic en "Guardar"
4. Se mostrará un mensaje de confirmación con la ruta del archivo guardado

### Formato del CSV

El archivo CSV generado tiene el siguiente formato:

```csv
Mes,Area Origen,Area Destino,Empleado,Fecha
12,Desarrollo,Testing,Juan Pérez,2024-12-15
11,Testing,Recursos Humanos,María García,2024-11-20
10,Desarrollo,Recursos Humanos,Carlos López,2024-10-05
```

- Campos con comas se encierran entre comillas
- Comillas dentro de los campos se duplican
- Compatible con Excel, Google Sheets, y otras herramientas

## Integración en tu Sistema

Para integrar esta ventana en tu sistema existente:

```java
// 1. Obtener datos de tu sistema
List<Movimiento> movimientos = obtenerMovimientosDelSistema();
List<Area> areas = obtenerAreasDelSistema();

// 2. Crear y mostrar la ventana
ReporteMovimientos ventana = new ReporteMovimientos(movimientos, areas);
ventana.setVisible(true);
```

## Requisitos

- Java 17 o superior
- Swing (incluido en JDK)

## Estructura del Proyecto

```
.
├── Area.java                      # Clase de dominio
├── Empleado.java                  # Clase de dominio
├── Manager.java                   # Clase de dominio
├── Movimiento.java               # Clase de dominio (copiada de issues/9/)
├── Sistema.java                  # Vacío (para expansión futura)
├── ReporteMovimientos.java       # GUI principal ⭐
├── TestReporteMovimientos.java   # Tests unitarios
├── TestCSVExport.java            # Test de exportación
├── TestCSVEscaping.java          # Test de caracteres especiales
├── REPORTE_MOVIMIENTOS_DOC.md    # Documentación detallada
├── README_IMPLEMENTATION.md      # Este archivo
└── .gitignore                    # Excluye .class files
```

## Documentación Adicional

Consulte `REPORTE_MOVIMIENTOS_DOC.md` para:
- Descripción detallada de cada funcionalidad
- Mockup visual de la interfaz
- Ejemplos de uso de filtros
- Detalles técnicos de implementación

## Autores

- Diego Rocabado
- Santiago Dirón

## Verificación de Calidad

✅ **Compilación**: Sin errores ni warnings críticos
✅ **Tests**: 3 suites de tests completas y pasando
✅ **CodeQL**: 0 vulnerabilidades de seguridad
✅ **Funcionalidad**: Todas las características del Issue #11 implementadas
✅ **Validaciones**: Manejo robusto de errores y casos edge
✅ **Documentación**: Completa y actualizada
