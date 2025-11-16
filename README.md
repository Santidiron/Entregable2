# Sistema de Gestión de Managers

## Descripción
Sistema ABM (Alta, Baja, Modificación) para gestión de Managers desarrollado en Java con interfaz gráfica Swing.

## Autores
- Diego Rocabado
- Santiago Dirón

## Características Implementadas

### 1. Lista de Managers
- Tabla con columnas: Nombre, Cédula, Antigüedad, Celular, Cantidad Empleados a Cargo
- Ordenamiento automático por antigüedad (descendente)

### 2. Alta de Manager
Campos requeridos:
- **Nombre**: Campo de texto (requerido)
- **Cédula**: Campo de texto (requerido, único, formato validado)
- **Antigüedad**: Número entero en años (debe ser > 0)
- **Celular**: Campo de texto (formato validado)
- **Área**: Selección mediante ComboBox

Validaciones:
- Cédula única en todo el sistema (managers y empleados)
- Formato de cédula: `X.XXX.XXX-X` o `XXXXXXXX`
- Formato de celular: `09X XXX XXX` o `09XXXXXXX`
- Todos los campos son obligatorios

### 3. Baja de Manager
- Solo permite eliminar managers SIN empleados a cargo
- Muestra mensaje de error si el manager tiene empleados
- Solicita confirmación antes de eliminar

### 4. Modificación de Manager
- Solo permite modificar el campo **Celular**
- Muestra cantidad de empleados a cargo (solo lectura)
- Campos deshabilitados: Nombre, Cédula, Antigüedad

## Cómo Ejecutar

### Compilar
```bash
javac *.java
```

### Ejecutar la aplicación GUI
```bash
java GestionManagersGUI
```

### Ejecutar tests
```bash
java TestManagerABM
```

## Estructura del Código

- **Sistema.java**: Lógica de negocio y validaciones
- **GestionManagersGUI.java**: Interfaz gráfica Swing
- **Manager.java**: Modelo de datos para Manager
- **Empleado.java**: Modelo de datos para Empleado
- **Area.java**: Modelo de datos para Area
- **TestManagerABM.java**: Suite de pruebas

## Validaciones Implementadas

1. **Formato de Cédula**: Valida formato uruguayo (X.XXX.XXX-X o sin puntos)
2. **Cédula Única**: Verifica que no exista en managers ni empleados
3. **Formato de Celular**: Valida formato uruguayo (09X XXX XXX)
4. **Antigüedad**: Debe ser un número entero mayor a 0
5. **Empleados a Cargo**: Previene eliminación de managers con empleados

## Screenshots

### Ventana Principal
Muestra la lista de managers ordenados por antigüedad.

### Diálogo Alta
Formulario para agregar un nuevo manager con todos los campos y validaciones.

### Diálogo Modificación
Formulario que solo permite modificar el celular del manager.

## Issues Resueltos
- Issue #6: Desarrollar ABM para Managers
- Issue #9: Duplicate of #6
