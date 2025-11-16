# Reporte de Movimientos - Documentación

## Descripción
Ventana de reporte de movimientos con filtros y exportación a CSV implementada según los requisitos del Issue #11.

## Funcionalidades Implementadas

### 1. Tabla de Movimientos
- **Columnas**: Mes, Área Origen, Área Destino, Empleado (Nombre Completo), Fecha
- Muestra todos los movimientos registrados en el sistema
- Ordenados por mes (más reciente primero)
- Tabla no editable con scroll

### 2. Filtros
Ubicados en un panel superior con los siguientes controles:

- **Filtro por Mes**: 
  - ComboBox con opciones: "Todos", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
  
- **Filtro por Área**: 
  - ComboBox con opciones: "Todas" + lista dinámica de áreas del sistema
  - Filtra por área origen O área destino
  
- **Filtro por Empleado**: 
  - Campo de texto para buscar por nombre
  - Búsqueda insensible a mayúsculas/minúsculas
  - Busca coincidencias parciales en nombre + apellido
  
- **Botón "Aplicar Filtros"**: 
  - Aplica los filtros seleccionados
  - Actualiza la tabla con los resultados filtrados
  - Mantiene el ordenamiento por mes descendente
  
- **Botón "Limpiar Filtros"**: 
  - Restaura todos los filtros a sus valores por defecto
  - Muestra todos los movimientos nuevamente

### 3. Exportación a CSV

- **Botón "Exportar a CSV"**: 
  - Ubicado en la parte inferior derecha de la ventana
  - Abre JFileChooser para seleccionar ubicación y nombre del archivo
  - Nombre por defecto: "movimientos.csv"
  - Agrega automáticamente extensión .csv si no está presente
  
- **Formato del archivo CSV**:
  ```
  Mes,Area Origen,Area Destino,Empleado,Fecha
  12,Desarrollo,Testing,Juan Pérez,2024-12-15
  11,Testing,Recursos Humanos,María García,2024-11-20
  ...
  ```
  
- **Características**:
  - Primera línea: encabezados (Mes,Area Origen,Area Destino,Empleado,Fecha)
  - Separador: coma (,)
  - Codificación: UTF-8
  - Manejo de campos con comas (se encierran entre comillas)
  - Exporta solo los datos filtrados visibles en la tabla

### 4. Validaciones

- **Antes de exportar**: Verifica que haya datos para exportar
- **Manejo de errores**: Captura y muestra errores de escritura de archivo
- **Valores nulos**: Maneja correctamente áreas y empleados nulos (muestra "N/A")
- **Mensajes**:
  - Advertencia si no hay datos para exportar
  - Confirmación con ruta del archivo al exportar exitosamente
  - Error descriptivo si falla la escritura del archivo

## Uso

### Desde código Java:
```java
List<Area> areas = // obtener áreas del sistema
List<Movimiento> movimientos = // obtener movimientos del sistema

ReporteMovimientos ventana = new ReporteMovimientos(movimientos, areas);
ventana.setVisible(true);
```

### Ejecutar con datos de prueba:
```bash
java -cp .:issues/9 ReporteMovimientos
```

### Ejecutar tests:
```bash
java -cp .:issues/9 TestReporteMovimientos
```

## Estructura de la Ventana

```
┌─────────────────────────────────────────────────────────────────┐
│  Reporte de Movimientos                                    [_][□][X] │
├─────────────────────────────────────────────────────────────────┤
│ ┌─ Filtros ─────────────────────────────────────────────────┐   │
│ │ Mes: [Todos ▼] Área: [Todas ▼] Empleado: [________]      │   │
│ │                    [Aplicar Filtros] [Limpiar Filtros]    │   │
│ └───────────────────────────────────────────────────────────┘   │
│                                                                   │
│ ┌─────────────────────────────────────────────────────────────┐ │
│ │ Mes │ Área Origen      │ Área Destino     │ Empleado      │...│ │
│ ├─────┼──────────────────┼──────────────────┼───────────────┤   │
│ │ 12  │ Desarrollo       │ Testing          │ Juan Pérez    │...│ │
│ │ 12  │ Recursos Humanos │ Desarrollo       │ María García  │...│ │
│ │ 11  │ Testing          │ Recursos Humanos │ María García  │...│ │
│ │ 10  │ Desarrollo       │ Recursos Humanos │ Carlos López  │...│ │
│ │  9  │ Testing          │ Desarrollo       │ Juan Pérez    │...│ │
│ │     │                  │                  │               │   │ │
│ └─────────────────────────────────────────────────────────────┘ │
│                                                                   │
│                                          [Exportar a CSV]         │
└───────────────────────────────────────────────────────────────────┘
```

## Archivos Creados

1. **ReporteMovimientos.java**: Clase principal con toda la funcionalidad GUI
2. **TestReporteMovimientos.java**: Clase de prueba para verificar la funcionalidad
3. **Movimiento.java**: Copiado de issues/9/ al directorio principal

## Dependencias

- Java Swing (javax.swing.*)
- Java AWT (java.awt.*)
- Java IO (java.io.*)
- Classes: Area, Empleado, Movimiento

## Notas Técnicas

- La ventana es de 900x600 píxeles y se centra en la pantalla
- Utiliza DefaultTableModel para la tabla
- Los filtros se aplican mediante predicados sobre la lista completa
- El ordenamiento se mantiene después de aplicar filtros
- El método main incluye datos de prueba completos para demostración
