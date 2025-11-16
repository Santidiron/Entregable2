# Reporte de Estado de Áreas - Issue #10

## Descripción

Este módulo implementa un sistema completo de reportes de estado de áreas con interfaz gráfica Swing, que permite visualizar información sobre presupuestos de áreas y empleados.

## Archivos Implementados

### 1. ReporteEstadoAreas.java
Ventana principal que muestra:
- **Tabla de Áreas**: Muestra todas las áreas con su presupuesto anual, presupuesto usado y porcentaje usado
  - Ordenadas por porcentaje en orden decreciente
  - Coloreadas según el porcentaje de presupuesto usado:
    - 🔴 Rojo: más del 90% usado
    - 🟡 Amarillo: entre 70% y 90% usado  
    - 🟢 Verde: menos del 70% usado

- **Tabla de Empleados**: Al seleccionar un área, muestra sus empleados
  - Columnas: Legajo, Nombre, Apellido, Salario, Manager
  - Ordenados alfabéticamente por nombre
  - Salarios coloreados con gradiente de negro a azul según rango

### 2. EmpleadoDetailsDialog.java
Diálogo modal que muestra al hacer clic en un empleado:
- **Información Personal**: Legajo, Nombre, Apellido, Cédula, Celular
- **Información Laboral**: Salario Mensual, Antigüedad, Área, Manager
- **Botón "Ver CV Completo"**: Abre el archivo CV con la aplicación predeterminada del sistema

## Cómo Usar

### Compilación
```bash
cd /home/runner/work/Entregable2/Entregable2
javac *.java issues/10/*.java
```

### Ejecución
```bash
java -cp .:issues/10 ReporteEstadoAreas
```

La aplicación incluye datos de prueba que se cargan automáticamente.

### Uso de la Interfaz
1. **Ver Áreas**: Al iniciar, se muestra la tabla de áreas ordenada por porcentaje de presupuesto
2. **Seleccionar Área**: Haga clic en cualquier fila de la tabla de áreas para ver sus empleados
3. **Ver Detalles de Empleado**: Haga clic en cualquier empleado para ver su información completa
4. **Ver CV**: En el diálogo de detalles, haga clic en "Ver CV Completo" para abrir el archivo

## Integración con el Sistema

### Modelo de Datos
El módulo se integra perfectamente con las clases del modelo existente:
- `Area.java`: Contiene información de áreas y lista de empleados
- `Empleado.java`: Contiene información de empleados
- `Manager.java`: Contiene información de managers

### Cálculos Automáticos
- **Presupuesto Usado**: Se calcula automáticamente sumando los salarios mensuales de todos los empleados del área multiplicados por 12
- **Porcentaje Usado**: Se calcula como `(presupuesto usado / presupuesto anual) * 100`
- **Actualización en Tiempo Real**: Los cálculos se realizan cada vez que se cargan los datos

## Características Técnicas

### Componentes Swing Utilizados
- `JFrame`: Ventana principal
- `JTable`: Tablas para áreas y empleados
- `JSplitPane`: División vertical entre áreas y empleados
- `JDialog`: Ventana modal para detalles de empleado
- `DefaultTableModel`: Modelo de datos para las tablas
- `DefaultTableCellRenderer`: Renderizador personalizado para coloreado

### Patrones de Diseño
- **MVC**: Separación entre modelo (clases de dominio) y vista (UI Swing)
- **Observer**: Uso de ListSelectionListener para manejar eventos de selección
- **Strategy**: Renderizadores personalizados para diferentes estrategias de visualización

### Validaciones
- Verificación de datos nulos en arrays de empleados
- Manejo de errores al abrir archivos CV
- Validación de existencia de archivos antes de abrirlos

## Ejemplo de Datos de Prueba

El método `main` en `ReporteEstadoAreas.java` incluye datos de prueba con:
- 3 áreas (Tecnología, Ventas, Recursos Humanos)
- 12 empleados distribuidos en las áreas
- 3 managers
- Diferentes rangos salariales para demostrar el coloreado

## Mockup Visual

![Mockup del Sistema](https://github.com/user-attachments/assets/c202eabf-9fb4-4ee1-9463-a70cec36e2d0)

## Requisitos Cumplidos

✅ Tabla de áreas con columnas requeridas  
✅ Ordenamiento por porcentaje decreciente  
✅ Coloreado de filas según porcentaje  
✅ Click en área muestra empleados  
✅ Tabla de empleados con columnas requeridas  
✅ Ordenamiento alfabético de empleados  
✅ Coloreado de salarios  
✅ Click en empleado muestra detalles  
✅ Diálogo con información completa  
✅ Botón para ver CV  
✅ Cálculo de presupuesto usado  
✅ Cálculo de porcentaje  
✅ Actualización en tiempo real  

## Notas

- La aplicación requiere Java 8 o superior con soporte para Swing
- Para ejecutar en entornos sin display (headless), configure `java.awt.headless=true` o use Xvfb
- Los archivos CV deben existir en el sistema de archivos para poder abrirse
