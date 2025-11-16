# Sistema de Gestión de Empleados - ABM

Este proyecto implementa un sistema completo de gestión de empleados (ABM - Alta, Baja, Modificación) con interfaz gráfica en Java Swing.

## Características Implementadas

### 1. Gestión de Empleados
- **Alta de empleados** con formulario completo
- **Modificación** de datos (celular y salario)
- **Listado** de empleados ordenado por nombre
- **Visualización** de CV almacenados

### 2. Validaciones
- ✅ Cédula única en todo el sistema (empleados y managers)
- ✅ Validación de presupuesto de área (salario anual debe caber en presupuesto)
- ✅ Formato de celular (8-15 dígitos)
- ✅ Archivos CV solo en formato .txt
- ✅ Campos obligatorios: Nombre, Apellido, Cédula, Manager, Área

### 3. Gestión de CV
- Almacenamiento en carpeta `cvs/`
- Formato de nombre: `cedula_nombre_apellido.txt`
- Visualización completa del contenido
- Validación de formato .txt

## Estructura del Proyecto

```
Entregable2/
├── Area.java                    # Clase de dominio para Áreas
├── Manager.java                 # Clase de dominio para Managers
├── Empleado.java                # Clase de dominio para Empleados
├── Sistema.java                 # Lógica de negocio principal
├── GestorCV.java                # Gestión de archivos CV
├── EmpleadoList.java            # Ventana principal con lista de empleados
├── EmpleadoForm.java            # Formulario de alta de empleados
├── EmpleadoEditDialog.java      # Diálogo de edición de empleados
├── CVViewDialog.java            # Visor de CV
├── TestEmpleadoABM.java         # Tests de funcionalidad
├── TestGUI.java                 # Tests de interfaz gráfica
└── cvs/                         # Directorio para archivos CV
```

## Requisitos

- Java 17 o superior
- Sistema operativo con soporte para Java Swing

## Compilación

```bash
javac *.java
```

## Ejecución

### Ejecutar la aplicación completa

```bash
java EmpleadoList
```

Esto iniciará la aplicación con datos de prueba (2 áreas y 2 managers).

### Ejecutar tests

```bash
# Test de funcionalidad del sistema
java TestEmpleadoABM

# Test de interfaz gráfica
java TestGUI
```

## Uso de la Aplicación

### Ventana Principal (EmpleadoList)

La ventana principal muestra una tabla con todos los empleados del sistema:

- **Columnas**: Legajo, Nombre, Apellido, Cédula, Salario, Manager, Área
- **Botones**:
  - `Agregar Empleado`: Abre el formulario para crear un nuevo empleado
  - `Editar`: Permite modificar celular y salario del empleado seleccionado
  - `Ver CV`: Muestra el contenido del CV del empleado seleccionado
  - `Actualizar Lista`: Refresca la tabla con los datos actuales

### Formulario de Alta (EmpleadoForm)

Campos del formulario:

1. **Legajo**: Autogenerado (no editable)
2. **Nombre**: Campo obligatorio
3. **Apellido**: Campo obligatorio
4. **Cédula**: Campo obligatorio, debe ser única
5. **Celular**: Opcional, formato 8-15 dígitos
6. **Currículum (.txt)**: Opcional, solo archivos .txt
7. **Antigüedad**: Número entero, años de experiencia
8. **Salario Mensual**: Número decimal
9. **Manager**: Combo box con managers disponibles (obligatorio)
10. **Área**: Combo box con áreas disponibles (obligatorio)

**Validaciones automáticas**:
- Cédula única (no puede existir en empleados ni managers)
- Presupuesto del área suficiente para el salario anual
- Formato correcto de celular
- Archivo CV en formato .txt

### Edición de Empleados (EmpleadoEditDialog)

Solo permite modificar:
- Celular
- Salario Mensual

La validación de presupuesto se aplica también en la edición.

### Visualización de CV (CVViewDialog)

Muestra:
- Información del empleado (nombre, legajo, cédula)
- Path del archivo CV
- Contenido completo del CV

## Estructura de Datos

### Empleado
- `legajo`: int (autogenerado)
- `nombre`: String
- `apellido`: String
- `cedula`: String (única)
- `celular`: String
- `pathCV`: String
- `antiguedad`: int
- `salarioMensual`: double
- `manager`: Manager
- `area`: Area

### Manager
- `nombre`: String
- `cedula`: String (única)
- `celular`: String
- `antiguedad`: int
- `area`: Area
- `empleados`: Empleado[]

### Area
- `id`: int
- `nombre`: String
- `descripcion`: String
- `presupuestoAnual`: int
- `empleados`: Empleado[]

## Validación de Presupuesto

El sistema valida que el área tenga presupuesto suficiente:

```
Salario Anual = Salario Mensual × 12
Presupuesto Disponible = Presupuesto Anual - Suma(Salarios Anuales de Empleados del Área)
```

Un empleado solo puede ser agregado o modificado si:
```
Presupuesto Disponible >= Salario Anual del Empleado
```

## Gestión de Archivos CV

Los archivos CV se gestionan automáticamente:

1. Al crear un empleado con CV, el archivo se copia a `cvs/`
2. Se renombra con el formato: `cedula_nombre_apellido.txt`
3. El path se almacena en el objeto Empleado
4. El archivo original no se modifica

Ejemplo:
```
Empleado: Pedro González, Cédula: 11223344
Archivo CV: cvs/11223344_Pedro_González.txt
```

## Tests Incluidos

### TestEmpleadoABM.java
Prueba la funcionalidad del sistema:
- Creación de áreas y managers
- Validación de cédula única
- Validación de presupuesto
- Creación de empleados con y sin CV
- Gestión de archivos CV
- Actualización de empleados
- Listado ordenado

### TestGUI.java
Prueba la interfaz gráfica:
- Renderizado de ventanas
- Capturas de pantalla de las ventanas principales

## Autores

- Diego Rocabado
- Santiago Dirón

## Licencia

Este proyecto es parte del curso de Programación 2.
