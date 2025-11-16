# Requirements Checklist - Issue #2

## 1. Ventana ABM Áreas ✅

### Lista de áreas ordenadas por nombre (único) ✅
- **Implementado en**: `ABMAreasWindow.java` línea 281
- **Método**: `getAreasSortedByName()` en `Sistema.java`
- La tabla muestra las áreas ordenadas alfabéticamente

### Formulario de Alta ✅

#### ID (autogenerado) ✅
- **Implementado en**: `Sistema.java` línea 18, 41
- Variable `nextAreaId` se autoincrementa
- **Prueba**: Test 1 en `TestABM.java`

#### Nombre (único, validar) ✅
- **Implementado en**: `Sistema.java` línea 32, 49-56
- Método `existeAreaConNombre()` verifica unicidad (case-insensitive)
- **Validación en UI**: `ABMAreasWindow.java` línea 138-143
- **Prueba**: Test 2 en `TestABM.java`

#### Descripción ✅
- **Campo en formulario**: `ABMAreasWindow.java` línea 60-70
- Se permite texto multilínea con JTextArea

#### Presupuesto anual ✅
- **Campo en formulario**: `ABMAreasWindow.java` línea 72-80
- **Validación > 0**: `Sistema.java` línea 35-38
- **Prueba**: Test 3 y Test 4 en `TestABM.java`

### Baja: solo permitir para áreas sin empleados ✅
- **Implementado en**: `Sistema.java` línea 64-69, 76-84
- Método `tieneEmpleados()` verifica si hay empleados asignados
- **Validación en UI**: `ABMAreasWindow.java` línea 211-218
- **Prueba**: Test 6 y Test 7 en `TestABM.java`

### Modificación: solo permitir modificar descripción ✅
- **Implementado en**: `Sistema.java` línea 91-94
- Método `modificarArea()` solo cambia descripción
- **UI**: `ABMAreasWindow.java` línea 176-197
- Muestra mensaje al usuario informando la restricción
- **Prueba**: Test 5 en `TestABM.java`

### Tabla con columnas: ID, Nombre, Descripción, Presupuesto ✅
- **Implementado en**: `ABMAreasWindow.java` línea 31
- Columnas definidas: `{"ID", "Nombre", "Descripción", "Presupuesto"}`
- Tabla de solo lectura (línea 33-37)
- **Prueba**: Test 8 en `TestABM.java`

## 2. Diálogo de Movimiento de Empleados ✅

### Selección de empleado (combo box) ✅
- **Implementado en**: `MovimientoDialog.java` línea 39-49
- JComboBox con formato "Legajo - Nombre Apellido"

### Área origen (mostrar área actual del empleado) ✅
- **Implementado en**: `MovimientoDialog.java` línea 51-59
- Campo de solo lectura (línea 58)
- Actualización automática al cambiar empleado (línea 47, 100-111)

### Área destino (combo box con áreas disponibles) ✅
- **Implementado en**: `MovimientoDialog.java` línea 61-73
- JComboBox con formato "ID - Nombre"
- Lista todas las áreas disponibles

### Validación de presupuesto ✅

#### Verificar que área destino tenga presupuesto suficiente ✅
- **Implementado en**: `Sistema.java` línea 119-122
- Compara presupuesto con salario anual del empleado
- **Mensaje en UI**: `MovimientoDialog.java` línea 142-149
- **Prueba**: Test 10 en `TestABM.java`

#### Actualizar presupuestos de ambas áreas ✅
- **Implementado en**: `Sistema.java` línea 125-129
- Área origen: se incrementa por salario anual
- Área destino: se reduce por salario anual
- **Prueba**: Test 9 en `TestABM.java`

### Registrar movimiento con mes actual ✅
- **Implementado en**: `Sistema.java` línea 135-137
- Usa `Calendar.getInstance().get(Calendar.MONTH) + 1`
- Crea objeto `Movimiento` y lo agrega a la lista
- **Prueba**: Test 11 en `TestABM.java`

### Actualizar empleado con nueva área ✅
- **Implementado en**: `Sistema.java` línea 132
- Método `empleado.setArea(areaDestino)`
- **Prueba**: Test 9 en `TestABM.java`

## Validaciones Generales ✅

### Nombres de área únicos ✅
- **Implementado**: `Sistema.java` línea 49-56
- Comparación case-insensitive
- **Prueba**: Test 2

### Presupuesto mayor a 0 ✅
- **Implementado**: `Sistema.java` línea 35-38
- **Prueba**: Test 3

### No permitir baja si tiene empleados ✅
- **Implementado**: `Sistema.java` línea 64-69
- **Prueba**: Test 7

### Validar presupuesto en movimientos ✅
- **Implementado**: `Sistema.java` línea 119-122
- **Prueba**: Test 10

## Testing ✅
- **11 tests automatizados** en `TestABM.java`
- **Todos los tests pasan** ✓
- **Cobertura**: 100% de los requisitos funcionales

## Seguridad ✅
- **CodeQL scan**: 0 vulnerabilidades
- Sin inyección de código
- Validación de entrada en todos los campos

## Documentación ✅
- `README_ABM.md`: Documentación completa del sistema
- Comentarios en código
- Javadoc en métodos principales

---

**Estado**: ✅ TODOS LOS REQUISITOS CUMPLIDOS
**Fecha de implementación**: 2025-11-16
**Autores**: Diego Rocabado, Santiago Dirón
