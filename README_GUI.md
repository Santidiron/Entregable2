# Sistema de Gestión de Personal - GUI

## Funcionalidad de Datos Precargados (Issue #12)

### Descripción
Se ha implementado la funcionalidad para cargar datos de prueba ficticios en el sistema.

### Cómo Usar

1. **Ejecutar la aplicación GUI:**
   ```bash
   javac SistemaGUI.java
   java SistemaGUI
   ```

2. **Cargar datos precargados:**
   - Seleccionar menú: **Datos → Cargar Datos Precargados**
   - Se mostrará un diálogo de confirmación
   - Si el sistema ya tiene datos, se advertirá al usuario
   - Al confirmar, se cargan todos los datos

### Datos Precargados

#### Áreas (5):
1. **Personal** - Presupuesto: $500,000 - "Gestión de recursos humanos"
2. **RRHH** - Presupuesto: $450,000 - "Reclutamiento y desarrollo"
3. **Seguridad** - Presupuesto: $350,000 - "Seguridad física y digital"
4. **Comunicaciones** - Presupuesto: $400,000 - "Comunicación interna y externa"
5. **Marketing** - Presupuesto: $550,000 - "Estrategias de mercado"

#### Managers (4):
1. **Ana Martínez** - Cédula: 12345678, Antigüedad: 10 años, Celular: 099123456, Área: Personal
2. **Ricardo Morales** - Cédula: 23456789, Antigüedad: 8 años, Celular: 099234567, Área: RRHH
3. **Laura Torales** - Cédula: 34567890, Antigüedad: 12 años, Celular: 099345678, Área: Seguridad
4. **Juan Pablo Zapata** - Cédula: 45678901, Antigüedad: 6 años, Celular: 099456789, Área: Marketing

#### Empleados (12):
Distribuidos en las diferentes áreas con salarios variados:
- **Personal**: 2 empleados (Carlos González, María Rodríguez)
- **RRHH**: 3 empleados (Pedro Sánchez, Lucía Fernández, Diego Martín)
- **Seguridad**: 2 empleados (Roberto López, Andrea García)
- **Comunicaciones**: 2 empleados (Sofía Pérez, Javier Ramírez)
- **Marketing**: 3 empleados (Valentina Torres, Mateo Flores, Isabella Vega)

#### Movimientos Históricos (5):
Se crean 5 movimientos de ejemplo entre diferentes áreas en diferentes meses.

#### Archivos CV:
Se generan automáticamente 12 archivos CV en la carpeta `cvs/` con información de cada empleado.

### Características de la GUI

- **Diálogo de confirmación** antes de cargar datos
- **Advertencia** si el sistema ya contiene datos
- **Mensaje de éxito** mostrando resumen de datos cargados
- **Visualización completa** de todos los datos en el área de texto
- **Opción de limpiar consola** para mejor legibilidad

### Uso Programático

También se puede usar directamente la clase Sistema:

```java
Sistema sistema = new Sistema();
sistema.cargarDatosPrecargados();

// Acceder a los datos
ArrayList<Area> areas = sistema.getAreas();
ArrayList<Manager> managers = sistema.getManagers();
ArrayList<Empleado> empleados = sistema.getEmpleados();
ArrayList<Movimiento> movimientos = sistema.getMovimientos();
```

### Validaciones

- El método `tieneDatos()` verifica si el sistema ya contiene datos
- Los salarios están dentro del presupuesto de cada área
- Todos los empleados tienen archivos CV asociados
- Los movimientos están distribuidos en diferentes meses (1-12)
