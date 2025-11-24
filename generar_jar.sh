#!/bin/bash
# Script para generar JAR ejecutable del Sistema de Gestión de Empleados
# Diego Rocabado (305310) y Santiago Dirón (359644)

echo "========================================"
echo "Generando JAR ejecutable..."
echo "========================================"

# Limpiar compilaciones anteriores
echo "1. Limpiando archivos anteriores..."
rm -rf build
rm -f SistemaGestionEmpleados.jar
rm -f *.class

# Crear directorio de build
echo "2. Creando directorio build..."
mkdir -p build

# Compilar todos los archivos Java
echo "3. Compilando archivos Java..."
javac -d build -encoding UTF-8 *.java

if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación"
    exit 1
fi

# Verificar que se compilaron las clases
CLASS_COUNT=$(find build -name "*.class" | wc -l)
echo "   → $CLASS_COUNT clases compiladas"

if [ $CLASS_COUNT -eq 0 ]; then
    echo "❌ No se compilaron clases"
    exit 1
fi

# Crear archivo MANIFEST
echo "4. Creando MANIFEST..."
cat > build/MANIFEST.MF << EOF
Manifest-Version: 1.0
Main-Class: Main
Created-By: Diego Rocabado y Santiago Diron

EOF

# Crear JAR
echo "5. Creando archivo JAR..."
cd build
jar cfm ../SistemaGestionEmpleados.jar MANIFEST.MF *.class

if [ $? -eq 0 ]; then
    cd ..
    JAR_SIZE=$(ls -lh SistemaGestionEmpleados.jar | awk '{print $5}')
    echo ""
    echo "✅ JAR creado exitosamente!"
    echo "   Archivo: SistemaGestionEmpleados.jar"
    echo "   Tamaño: $JAR_SIZE"
    echo ""
    echo "Para ejecutar el JAR:"
    echo "  java -jar SistemaGestionEmpleados.jar"
    echo ""

    # Probar el JAR automáticamente
    echo "Verificando que el JAR es ejecutable..."
    java -jar SistemaGestionEmpleados.jar --help 2>&1 | head -1

    if [ $? -eq 0 ]; then
        echo "✅ JAR verificado correctamente"
    fi
else
    echo "❌ Error al crear el JAR"
    cd ..
    exit 1
fi

