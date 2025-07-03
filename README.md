TODO:
DAOs:
* AdvanceDAO
* FileDAO
* ResearchProjectDAO
Agregar DAOs ActivityFiles y AdvanceFiles

# SPGER
Proyecto Final de la Materia "Principios de Construcción de Software".
SPGER es un sistema pensado para que los involucrados en las Experiencias Educativas "Proyecto Guiado" y "Experiencia Recepcional" de la Licenciatura en Ingeniería de Software de la Universidad Veracruzana puedan realizar sus actividades afines a dichas Experiencias Educativas de una manera más eficiente.
En SPGER existen 5 tipos de Users, y cada tipo de usuario tiene disponible algunas funcionalidades en específico, las cuales se mencionan a continuación:

Estudiante
* E

Profesor
* E

Director
* E

Miembro de Cuerpo Académico
* E

Jefe de Carrera
* E

Para el desarrollo de Renovación LIS se ocuparon las siguientes tecnologías:
* Java 17.
* JavaFX versión 19.0.2.1.
* JasperReports.
* MySQL 8.0.

## Tecnologías requeridas para la instalación
* Java versión 11 o 17.
* JavaFX versión 19.0.2.1.
* JasperReports.
* MySQL versión 8.0 o posterior.

## Instrucciones para la ejecución del sistema
1. Ejecutar el script "CreateSPGERDatabase.sql" en MySQL.
2. En el archivo "src/dependencies/resources/DatabaseAccess.properties", poner la dirección de la base de datos, y el nombre de usuario y contraseña del usuario que usted haya creado para acceder a la base de datos.
3. En el entorno de desarrollo que usted use para ejecutar proyectos desarrollados en Java (ej: NetBeans, IntelliJ, Eclipse, Visual Studio Code), modificar los archivos de configuración necesarios del entorno de desarrollo para referenciar en el proyecto las librerías ubicadas en la carpeta "lib".
4. En ese mismo entorno de desarrollo, referenciar en el proyecto las librerías de JavaFX (deberá descomprimir el .zip de la instalación de JavaFX, y la carpeta generada ponerla en una ruta ajena a alguna ruta del proyecto. NO INTENTE REFERENCIAR LAS LIBRERIAS DE JAVAFX EN LA CARPETA LIB PORQUE EL ENTORNO DE DESARROLLO REQUIERE LEER LOS ARCHIVOS .dll QUE VIENEN INCLUIDOS EN LA CARPETA DESCOMPRIMIDA DE LA INSTALACIÓN DE JAVAFX).
5. En ese mismo entorno de desarrollo, ejecutar el archivo "src/mx/uv/fei/Main.java"
6. ¡Listo!, SPGER está listo para ser usado.

Si los pasos han sido ejecutados correctamente, las siguientes veces que requiera ejecutar SPGER, solamente deberá seguir los pasos 5 y 6.

## Notas extra
Si en la base de datos no hay Users almacenados, SPGER abrirá una ventana para poder registrar un usuario con el rol de "Jefe de Carrera". Este tipo de usuario es necesario para poder registrar los demás tipos de usuario.