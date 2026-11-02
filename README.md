# Módulo de Nómina

Aplicación desarrollada en Java cuyo propósito es calcular la nómina mensual de los empleados y guardar los resultados en una base de datos local. El módulo se enfoca exclusivamente en dos responsabilidades: calcular nóminas y almacenar los cálculos realizados.

## Objetivo del proyecto

Calcular el salario bruto y neto de cada empleado de acuerdo con su tipo de vinculación laboral, aplicando las bonificaciones, beneficios y deducciones definidas para cada caso. Una vez validado el resultado, el sistema permite guardar la nómina calculada.

## Funcionalidades principales

* Obtener la información del empleado y los datos contractuales necesarios.
* Identificar el tipo de empleado.
* Ingresar las horas trabajadas o las ventas, cuando corresponda.
* Calcular el salario bruto.
* Aplicar bonificaciones y beneficios según las reglas de negocio.
* Calcular las deducciones correspondientes.
* Obtener y mostrar el salario neto.
* Validar los datos ingresados y el resultado del cálculo.
* Guardar la nómina calculada en una base de datos local.

## Reglas de cálculo

El sistema contempla cuatro tipos de empleados:

* **Asalariado:** Recibe un salario fijo mensual. Si tiene más de cinco años de antigüedad, recibe un bono del 10% de su salario.
* **Por horas:** Recibe el pago de acuerdo con su tarifa por hora y las horas trabajadas. Las horas que superen las 40 horas semanales se pagan a 1.5 veces la tarifa normal. No recibe bonus.
* **Por comisión:** Recibe un salario base más una comisión sobre las ventas. Si las ventas superan los $20.000.000, recibe un bono adicional del 3% sobre las ventas.
* **Temporal:** Recibe un salario fijo mensual según su contrato por tiempo definido. No recibe bonos ni beneficios adicionales.

### Deducciones y beneficios

* **Seguridad social y pensión:** Deducción del 4% del salario bruto.
* **Bono de alimentación:** Beneficio mensual de $1.000.000 para empleados permanentes (asalariados y por comisión), cubierto por la empresa.
* **Fondo de ahorro:** Los empleados por horas con más de un año de antigüedad pueden acceder si aceptan voluntariamente. El aporte corresponde al 2% del salario.

## Tecnologías y criterios de desarrollo

* **Lenguaje:** Java.
* **Persistencia:** Base de datos local integrada.
* **Paradigma:** Programación orientada a objetos.
* **Diseño:** Arquitectura por capas y principios SOLID.
* **Calidad:** Código limpio, refactorización y pruebas unitarias.
* **Pruebas:** Enfoque TDD para la lógica del dominio.
* **Control de versiones:** Git y GitHub.
* **Organización del trabajo:** Metodología Kanban.

## Alcance

### Incluido
* Cálculo mensual e individual de nómina.
* Aplicación de reglas salariales según el tipo de empleado.
* Validación de horas y ventas no negativas.
* Validación para impedir guardar una nómina con salario neto negativo.
* Visualización del desglose del cálculo.
* Almacenamiento local de los resultados.

### Fuera del alcance
* Crear, modificar o eliminar empleados y contratos.
* Administración de usuarios, roles o permisos.
* Control de asistencia y registro de ventas.
* Ejecución de pagos o transferencias bancarias.
* Generación de comprobantes PDF.
* Reportes históricos o consolidados.
* Integraciones con bancos, DIAN u otras entidades.
* Administración de otros módulos empresariales.

## Estructura general del proceso

1. Obtener los datos necesarios del empleado y su contrato.
2. Ingresar las horas trabajadas o las ventas, según corresponda.
3. Identificar el tipo de empleado.
4. Calcular el salario bruto y los conceptos aplicables.
5. Aplicar las deducciones correspondientes.
6. Calcular el salario neto.
7. Validar el resultado.
8. Mostrar el desglose de la nómina.
9. Guardar el cálculo en la base de datos local.

## Requisitos previos

Para ejecutar el proyecto será necesario contar con:

* Un entorno de desarrollo compatible con Java.
* Un JDK compatible con la versión de Java utilizada por el proyecto.
* El motor o controlador correspondiente a la base de datos local seleccionada.

Las versiones exactas y el procedimiento de instalación deberán establecerse según la configuración definitiva del proyecto.

## Ejecución

El sistema se ejecuta exclusivamente a través de la interfaz de línea de comandos (CLI) o terminal. Cuando se definan por completo las herramientas de construcción del proyecto, se deberán documentar en esta sección los comandos específicos para su compilación y arranque (por ejemplo, los de Maven o Gradle si se llegan a implementar), así como los pasos requeridos para inicializar la base de datos correspondiente.

## Estado del proyecto

Proyecto en desarrollo. Las reglas principales de cálculo están identificadas; algunos parámetros requieren confirmación antes de completar su implementación:

* Porcentaje de comisión sobre ventas.
* Tratamiento contable del fondo de ahorro del 2%.
* Configuración definitiva de la base de datos y de la comunicación para obtener los datos contractuales.

## Equipo y contribuciones

El proyecto utiliza GitHub para el control de versiones y Kanban para organizar.
