# Visual Guide - ReporteMovimientos GUI

## Main Window Layout

```
╔══════════════════════════════════════════════════════════════════════════════╗
║  Reporte de Movimientos                                         [_] [□] [X]  ║
╠══════════════════════════════════════════════════════════════════════════════╣
║                                                                               ║
║  ┌─ Filtros ────────────────────────────────────────────────────────────┐   ║
║  │                                                                        │   ║
║  │  Mes:  ┌──────────┐   Área:  ┌──────────────┐   Empleado: ┌───────┐ │   ║
║  │        │ Todos  ▼ │          │ Todas      ▼ │             │       │ │   ║
║  │        └──────────┘          └──────────────┘             └───────┘ │   ║
║  │                                                                        │   ║
║  │                        ┌────────────────┐  ┌────────────────┐        │   ║
║  │                        │ Aplicar Filtros│  │ Limpiar Filtros│        │   ║
║  │                        └────────────────┘  └────────────────┘        │   ║
║  │                                                                        │   ║
║  └────────────────────────────────────────────────────────────────────────┘   ║
║                                                                               ║
║  ╔════════════════════════════════════════════════════════════════════════╗  ║
║  ║ Mes │ Área Origen        │ Área Destino       │ Empleado        │ Fe..║  ║
║  ╠═════╪════════════════════╪════════════════════╪═════════════════╪════╣  ║
║  ║ 12  │ Desarrollo         │ Testing            │ Juan Pérez      │ 20..║  ║
║  ║ 12  │ Recursos Humanos   │ Desarrollo         │ María García    │ 20..║  ║
║  ║ 11  │ Testing            │ Recursos Humanos   │ María García    │ 20..║  ║
║  ║ 10  │ Desarrollo         │ Recursos Humanos   │ Carlos López    │ 20..║  ║
║  ║  9  │ Testing            │ Desarrollo         │ Juan Pérez      │ 20..║  ║
║  ║  8  │ Recursos Humanos   │ Testing            │ Carlos López    │ 20..║  ║
║  ║  7  │ Desarrollo         │ Recursos Humanos   │ Juan Pérez      │ 20..║  ║
║  ║     │                    │                    │                 │     ║ ▲║
║  ║     │                    │                    │                 │     ║ █║
║  ║     │                    │                    │                 │     ║ ▼║
║  ╚════════════════════════════════════════════════════════════════════════╝  ║
║                                                                               ║
║                                                  ┌──────────────────┐         ║
║                                                  │ Exportar a CSV   │         ║
║                                                  └──────────────────┘         ║
║                                                                               ║
╚══════════════════════════════════════════════════════════════════════════════╝
```

## Filter Panel Detail

```
┌─ Filtros ──────────────────────────────────────────────────────────────────┐
│                                                                             │
│  Label:  [Control]           Label:  [Control]           Label: [Control]  │
│  ─────   ─────────           ─────   ─────────           ────── ─────────  │
│                                                                             │
│  Mes:    [Todos     ▼]       Área:   [Todas         ▼]  Empleado: [     ] │
│          [1            ]             [Desarrollo        ]         [     ] │
│          [2            ]             [Testing           ]         [     ] │
│          [3            ]             [Recursos Humanos  ]                  │
│          [...]                       [...]                                 │
│          [12           ]                                                   │
│                                                                             │
│                              [Aplicar Filtros]  [Limpiar Filtros]          │
│                              ─────────────────  ──────────────────          │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Export Dialog (JFileChooser)

```
╔═══════════════════════════════════════════════════════════════════════════╗
║  Guardar archivo                                             [_] [□] [X]  ║
╠═══════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║  Buscar en:  [Documentos                                           ▼]     ║
║                                                                            ║
║  ╔════════════════════════════════════════════════════════════════════╗   ║
║  ║ 📁 Desktop                                                         ║   ║
║  ║ 📁 Documentos                                                      ║   ║
║  ║ 📁 Descargas                                                       ║   ║
║  ║ 📄 archivo1.csv                                                    ║   ║
║  ║ 📄 archivo2.csv                                                    ║   ║
║  ╚════════════════════════════════════════════════════════════════════╝   ║
║                                                                            ║
║  Nombre del archivo:  [movimientos.csv                             ]      ║
║                                                                            ║
║  Tipo:  [Todos los archivos                                        ▼]     ║
║                                                                            ║
║                                        [Guardar]      [Cancelar]          ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

## Success Message Dialog

```
╔═══════════════════════════════════════════════════════════════════════════╗
║  Éxito                                                       [_] [□] [X]  ║
╠═══════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║    ℹ️                                                                      ║
║                                                                            ║
║    Archivo exportado exitosamente en:                                     ║
║    /home/usuario/Documentos/movimientos.csv                               ║
║                                                                            ║
║                                                                            ║
║                                    [Aceptar]                               ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

## Error Message Dialog (No Data)

```
╔═══════════════════════════════════════════════════════════════════════════╗
║  Advertencia                                                 [_] [□] [X]  ║
╠═══════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║    ⚠️                                                                      ║
║                                                                            ║
║    No hay datos para exportar.                                            ║
║                                                                            ║
║                                                                            ║
║                                    [Aceptar]                               ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

## Error Message Dialog (Write Error)

```
╔═══════════════════════════════════════════════════════════════════════════╗
║  Error                                                       [_] [□] [X]  ║
╠═══════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║    ❌                                                                      ║
║                                                                            ║
║    Error al escribir el archivo:                                          ║
║    Permission denied: /protected/folder/file.csv                          ║
║                                                                            ║
║                                                                            ║
║                                    [Aceptar]                               ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

## Usage Flow Diagram

```
                           ┌──────────────────┐
                           │   Application    │
                           │     Starts       │
                           └────────┬─────────┘
                                    │
                                    ▼
                  ┌──────────────────────────────────┐
                  │   ReporteMovimientos Window      │
                  │         Opens with Data          │
                  └──────────┬────────────┬──────────┘
                             │            │
                  ┌──────────▼──┐    ┌───▼───────────┐
                  │ View All    │    │  Apply Filters│
                  │ Movements   │    │               │
                  └──────────┬──┘    └───┬───────────┘
                             │            │
                             │            ▼
                             │    ┌───────────────┐
                             │    │ Filter by:    │
                             │    │ - Month       │
                             │    │ - Area        │
                             │    │ - Employee    │
                             │    └───┬───────────┘
                             │        │
                             ▼        ▼
                      ┌──────────────────────┐
                      │  Table Shows Results │
                      │  (Filtered or All)   │
                      └──────────┬───────────┘
                                 │
                                 ▼
                      ┌──────────────────────┐
                      │ Click "Exportar CSV" │
                      └──────────┬───────────┘
                                 │
                                 ▼
                      ┌──────────────────────┐
                      │  JFileChooser Opens  │
                      └──────────┬───────────┘
                                 │
                    ┌────────────┴────────────┐
                    ▼                         ▼
          ┌─────────────────┐      ┌──────────────────┐
          │ User Cancels    │      │ User Saves File  │
          └─────────────────┘      └────────┬─────────┘
                                             │
                                  ┌──────────┴──────────┐
                                  ▼                     ▼
                        ┌──────────────────┐   ┌──────────────┐
                        │  Success Message │   │ Error Message│
                        │  with File Path  │   │  (if fails)  │
                        └──────────────────┘   └──────────────┘
```

## Data Flow Diagram

```
┌─────────────────┐
│ Constructor     │
│ Parameters:     │
│ - List<Mov>     │
│ - List<Area>    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Initialize GUI  │
│ Components      │
└────────┬────────┘
         │
         ▼
┌─────────────────┐       ┌──────────────────┐
│ Load All Data   │────→  │ Sort by Month    │
│ Into Table      │       │ (Descending)     │
└────────┬────────┘       └──────────────────┘
         │
         ├─────────────────┬─────────────────┐
         ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│ User Filters │  │ User Exports │  │ User Clears  │
│ Data         │  │ to CSV       │  │ Filters      │
└──────┬───────┘  └──────┬───────┘  └──────┬───────┘
       │                 │                 │
       ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│ Apply Filter │  │ Validate     │  │ Reset to     │
│ Predicates   │  │ Has Data     │  │ Defaults     │
└──────┬───────┘  └──────┬───────┘  └──────┬───────┘
       │                 │                 │
       ▼                 ▼                 │
┌──────────────┐  ┌──────────────┐        │
│ Update Table │  │ Write CSV    │        │
│ with Results │  │ File (UTF-8) │        │
└──────────────┘  └──────┬───────┘        │
                         │                 │
                         ▼                 │
                  ┌──────────────┐         │
                  │ Show Success │         │
                  │ or Error     │         │
                  └──────────────┘         │
                                          │
         ┌────────────────────────────────┘
         │
         ▼
┌──────────────┐
│ Refresh      │
│ Table with   │
│ All Data     │
└──────────────┘
```

## Key Features Illustrated

### 1. Filter Interaction
- **Independent**: Each filter works alone
- **Combined**: All filters can work together
- **Non-destructive**: Original data never modified

### 2. Export Process
- **Safe**: Validates before writing
- **User-friendly**: Clear messages
- **Robust**: Handles special characters

### 3. Table Display
- **Sorted**: Always by month (desc)
- **Scrollable**: Handles any number of records
- **Read-only**: Prevents accidental edits

This visual guide complements the technical documentation and helps users understand the application flow and interface.
