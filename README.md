# 🚀 Proyecto-POO Java con Maven y JavaFX

Este proyecto usa **Maven** como herramienta de construcción y gestión de dependencias, y **JavaFX** para la interfaz gráfica.

## 📋 Requisitos Previos

- **JDK 24** o superior ([Descargar](https://adoptium.net/))
- **Maven 3.6+** ([Descargar](https://maven.apache.org/download.cgi))
- **Git** ([Descargar](https://git-scm.com/))

## 🏗️ Estructura del Proyecto

```
Proyecto-POO/
├── src/
│   └── main/
│       ├── java/          # Código fuente Java
│       └── resources/     # Recursos ( CSS, archivos de datos)
│ 
│ 
│
├── target/               # Generado automáticamente (no tracked)
├── pom.xml               # Configuración de Maven
└── README.md             # Este archivo
```

---

## ⚡ Comandos Esenciales

### Compilar el proyecto
```bash
mvn clean compile
```

### Ejecutar la aplicación
```bash
mvn javafx:run
```

### Empaquetar como JAR ejecutable
```bash
mvn clean package
```

### Ejecutar pruebas unitarias
```bash
mvn test
```

---

## 🔧 Configuración del Entorno de Desarrollo

### VS Code
1. Instalar las extensiones:
   - **Extension Pack for Java**
   - **Maven for Java**
2. VS Code detectará automáticamente la estructura Maven

---

## 📦 Dependencias Principales

El proyecto usa:
- **JavaFX 20.0.2** para la interfaz gráfica
- **Maven** para gestión de dependencias y construcción

---

## 🚀 Ejecución desde IDE

### VS Code
1. Abrir el archivo principal
2. Configurar archivos .vscode para poder correrlo desde vs code
3. Darle a Run And Debug para correrlo

---

## 🔄 Flujo de Trabajo Recomendado

1. **Antes de comenzar:**
   ```bash
   git pull origin main
   ```

2. **Crear una rama para nueva funcionalidad:**
   ```bash
   git checkout -b nombre-rama
   ```

3. **Después de hacer cambios:**
   ```bash
   git add .
   git commit -m "Descripción de cambios"
   git push origin nombre-rama
   ```

4. **Probar los cambios:**
   ```bash
   mvn clean compile
   mvn javafx:run
   ```

---

## ❌ Solución de Problema Común

### Error: "JavaFX runtime components are missing"
Deben de asegurarse de que el perfil de Maven esté correctamente configurado y que las dependencias de JavaFX estén en el `pom.xml`.

---

## 🤝 Contribución al Proyecto

1. Sincronizar repositorio local con los últimos cambios
2. Crear una rama para funcionalidad
3. Realizar cambios y prueba que funcionen
4. Hacer commit y push de la rama en la que se trabajó
5. Crear un Pull Request en GitHub


---

**🎉 ¡SUERTE CODEANDO UVGENIO! 🎉**