# Módulo de Nómina

Módulo desarrollado en Java que simula el componente de liquidación salarial dentro de un sistema ERP. Su propósito es procesar la información contractual de los empleados (proveniente del área de Recursos Humanos), calcular la nómina mensual aplicando las reglas de negocio correspondientes y presentar el desglose de los resultados.

El alcance de este repositorio se centra exclusivamente en la lógica de liquidación salarial, la validación de invariantes del negocio y la gestión en memoria de los cálculos realizados.

## Stack Tecnológico y Criterios de Diseño

- **Lenguaje Principal:** Java (JDK 21 o superior, uso de *Records*, *Sealed Interfaces* y *Pattern Matching*).
- **Arquitectura:** Arquitectura Onion (diseño por capas desacopladas: `domain`, `application`, `infrastructure`).
- **Calidad de Software:** Aplicación de principios SOLID, Código Limpio (Clean Code), manejo de objetos de valor inmutables y pruebas unitarias.

## Funcionalidades Clave

- **Cálculo Polimórfico:** Procesamiento diferenciado de salarios según el tipo de vinculación del trabajador (Asalariado, Por Horas, Por Comisión y Temporal).
- **Gestión de Beneficios y Deducciones:** Aplicación automática de retenciones obligatorias (Salud, Pensión y ARL) y asignación de beneficios adicionales (bono de alimentación, bonos por antigüedad/ventas y fondo de ahorro).
- **Validación de Reglas de Negocio:** Control estricto de datos de entrada y resultados para impedir inconsistencias financieras (bloqueo de salarios netos negativos, horas trabajadas negativas o montos de ventas menores a cero).

## Estructura del Proyecto

- `src/main/java/`: Código fuente dividido en las capas `domain`, `application` e `infrastructure`.
- `src/test/java/`: Suite de pruebas unitarias del dominio y casos de uso.
- `docs/`: Documentación detallada del proyecto.

## Documentación Adicional

Para mantener este archivo conciso, el detalle técnico, arquitectónico y metodológico se encuentra alojado en el directorio [`/docs`](./docs), donde podrás consultar:

- **Especificación de Requisitos (SRS):** Detalle de las reglas de negocio y casos de uso.
- **Metodología de Trabajo:** Organización del equipo (CIPAS), flujo de ramas y tablero Kanban.

## Requisitos Previos y Ejecución

Antes de compilar y ejecutar el proyecto, asegúrate de contar con:

- **Java Development Kit (JDK):** Versión 21 o superior instalada y configurada en las variables de entorno.
- **Entorno de Desarrollo (IDE):** IntelliJ IDEA, Eclipse o VS Code con soporte para proyectos Java/Maven/Gradle.

### Pasos para ejecutar por consola

1. Clonar el repositorio en tu máquina local:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
2. Abrir el proyecto en tu IDE de preferencia y esperar la indexación de dependencias.
3. Ejecutar la clase principal ubicada en `src/main/java/infrastructure/Main.java` para iniciar la interfaz interactiva por consola.
