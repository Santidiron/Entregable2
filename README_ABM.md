# ABM Áreas y Movimientos - Documentación

## Descripción
Este proyecto implementa un sistema de gestión (ABM - Alta/Baja/Modificación) para Áreas y Movimientos de Empleados con interfaz gráfica en Java Swing.

## Requisitos
- Java 17 o superior
- Compilador javac

## Compilación
```bash
javac *.java
```

## Ejecución
```bash
java Main
```

## Componentes Principales

### 1. Sistema.java
Clase principal que gestiona toda la lógica de negocio:
- **Gestión de Áreas**: agregar, eliminar, modificar, buscar
- **Gestión de Empleados**: agregar empleados al sistema
- **Gestión de Movimientos**: registrar movimientos entre áreas
- **Validaciones**: 
  - Nombres de área únicos
  - Presupuesto mayor a 0
  - No permitir baja de áreas con empleados
  - Validación de presupuesto en movimientos

### 2. ABMAreasWindow.java
Ventana principal de la aplicación que permite:
- **Ver áreas**: Tabla ordenada por nombre con columnas ID, Nombre, Descripción, Presupuesto
- **Alta de áreas**: Formulario con validación de datos
- **Baja de áreas**: Solo permitido si el área no tiene empleados
- **Modificación de áreas**: Solo permite modificar la descripción
- **Abrir diálogo de movimientos**: Acceso a la funcionalidad de movimiento de empleados

### 3. MovimientoDialog.java
Diálogo modal para mover empleados entre áreas:
- **Selección de empleado**: ComboBox con lista de empleados
- **Área origen**: Campo de solo lectura que muestra el área actual
- **Área destino**: ComboBox con áreas disponibles
- **Validación de presupuesto**: Verifica que el área destino tenga presupuesto suficiente
- **Actualización automática**: Actualiza presupuestos y registra el movimiento

### 4. Main.java
Punto de entrada de la aplicación que:
- Inicializa el sistema
- Carga datos de ejemplo
- Lanza la interfaz gráfica

## Validaciones Implementadas

### Áreas
1. **Nombre único**: No se permite crear áreas con nombres duplicados (case-insensitive)
2. **Presupuesto > 0**: El presupuesto anual debe ser mayor a cero
3. **Baja condicional**: Solo se pueden eliminar áreas sin empleados asignados
4. **Modificación limitada**: Solo se puede modificar la descripción del área

### Movimientos
1. **Presupuesto suficiente**: El área destino debe tener presupuesto >= salario anual del empleado
2. **Actualización de presupuestos**: 
   - Área origen: se incrementa por el salario anual del empleado
   - Área destino: se reduce por el salario anual del empleado
3. **Registro del movimiento**: Se guarda con el mes actual
4. **Actualización del empleado**: Se actualiza la referencia al área

## Datos de Prueba
La aplicación incluye datos de ejemplo:
- 4 áreas: Ventas, Recursos Humanos, Tecnología, Marketing
- 3 empleados distribuidos en diferentes áreas

## Testing
Se incluye `TestABM.java` con 11 tests unitarios que validan:
- Creación de áreas con validaciones
- Modificación de áreas
- Eliminación de áreas
- Movimiento de empleados
- Validaciones de presupuesto
- Registro de movimientos

Para ejecutar los tests:
```bash
javac TestABM.java
java TestABM
```

## Estructura de Clases

### Clases de Modelo
- **Area**: Representa un área con ID, nombre, descripción, presupuesto y empleados
- **Empleado**: Representa un empleado con legajo, datos personales, salario, manager y área
- **Manager**: Representa un gerente con sus datos y empleados a cargo
- **Movimiento**: Representa un movimiento de empleado entre áreas

### Clases de Interfaz
- **ABMAreasWindow**: Ventana principal del sistema
- **MovimientoDialog**: Diálogo para movimientos de empleados

### Clases de Control
- **Sistema**: Lógica de negocio y validaciones
- **Main**: Punto de entrada de la aplicación

## Características Destacadas
✓ Interfaz gráfica intuitiva con Java Swing
✓ Validaciones completas según requerimientos
✓ Manejo de errores con mensajes informativos
✓ Tabla ordenada alfabéticamente
✓ IDs autogenerados
✓ Presupuestos actualizados automáticamente
✓ Registro de movimientos con mes actual
