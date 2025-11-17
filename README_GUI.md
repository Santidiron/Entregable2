# Sistema de Gestión de Empleados - GUI

## Autores
- Diego Rocabado
- Santiago Dirón

## Componentes GUI Implementados

### 1. VentanaBienvenida (Splash Screen)
Ventana de bienvenida que se muestra al iniciar la aplicación.

**Características:**
- Logo de la aplicación (emoji 📊)
- Nombres de los autores
- Transición automática después de 3 segundos a VentanaPrincipal
- Barra de progreso animada
- Diseño moderno con esquema de colores azul

**Cómo ejecutar:**
```bash
javac *.java
java VentanaBienvenida
```

### 2. VentanaPrincipal (Menú Principal)
Ventana principal de la aplicación con todas las funcionalidades.

**Características:**
- **Barra de Menú:**
  - **Archivo:** Guardar, Cargar, Salir
  - **Gestión:** Áreas, Managers, Empleados
  - **Movimientos:** Registrar Movimiento
  - **Reportes:** Estado de Áreas, Movimientos, Reporte Inteligente
  - **Datos:** Cargar Datos Precargados
- Panel central dinámico para contenido
- Barra de estado en la parte inferior
- Mensaje de bienvenida por defecto

**Cómo ejecutar:**
```bash
javac *.java
java VentanaPrincipal
```

### 3. PanelBase (Clase Base Abstracta)
Clase abstracta para todos los paneles de la aplicación.

**Características:**
- Extiende JPanel
- Integración con la clase Sistema
- Método abstracto `inicializarComponentes()`
- Método virtual `actualizarDatos()`
- Diseño consistente con BorderLayout

### 4. Sistema
Clase principal que gestiona todas las entidades del sistema.

**Características:**
- Gestión de Áreas (agregar, obtener, eliminar)
- Gestión de Managers (agregar, obtener, eliminar)
- Gestión de Empleados (agregar, obtener, eliminar)
- Método para cargar datos precargados

## Estructura del Proyecto

```
Entregable2/
├── Area.java              # Clase de dominio
├── Manager.java           # Clase de dominio
├── Empleado.java          # Clase de dominio
├── Sistema.java           # Clase de gestión
├── PanelBase.java         # Clase base para paneles
├── VentanaBienvenida.java # Splash screen
├── VentanaPrincipal.java  # Ventana principal
└── README_GUI.md          # Este archivo
```

## Compilación

Para compilar todos los archivos:
```bash
javac *.java
```

## Ejecución

Para ejecutar el sistema completo (iniciando con el splash screen):
```bash
java VentanaBienvenida
```

Para ejecutar solo la ventana principal:
```bash
java VentanaPrincipal
```

## Notas de Implementación

1. Las opciones del menú muestran mensajes informativos indicando que la funcionalidad será implementada próximamente.
2. La ventana principal incluye métodos `cambiarPanelCentral()` y `actualizarEstado()` para facilitar la integración futura.
3. El diseño respeta las relaciones del diagrama UML.
4. Todas las clases incluyen los comentarios con los nombres de los autores.
