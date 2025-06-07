/*Ejecuta primero esta query y luego las pruebas, cuando termines las pruebas borra estos datos de la base de datos*/

/*Agregar ON UPDATE CASCADE y ON DELETE CASCADE A TOOOOODAS LAS LLAVES FORANEAS*/
/*Para consultar por los metodos get*/

/*
INSERT INTO Users (nombre, firstSurname, secondSurname, emailAddress, password, alternateEmail, phoneNumber, estado)
    VALUES ('Luis Roberto', 'Justo', 'Moreno', 'lurojumo342@gmail.com', NULL, 'lurojumo343@gmail.com', '2288563472', 'Activo');
INSERT INTO Estudiantes (userId, Matrícula) VALUES (1, 'zS10000001');

INSERT INTO Users (nombre, firstSurname, secondSurname, emailAddress, password, alternateEmail, phoneNumber, estado)
    VALUES ('Jorge Alberto', 'Guevara', 'Cerdán', 'jaga234@gmail.com', NULL, 'jaga243@gmail.com', '2283487254', 'No Disponible');
INSERT INTO Professors (userId, staffNumber) VALUES (2, 100000001);

INSERT INTO Users (nombre, firstSurname, secondSurname, emailAddress, password, alternateEmail, phoneNumber, estado)
    VALUES ('Luis Roberto', 'Justo', 'Moreno', 'lurojumo342@gmail.com', NULL, 'lurojumo343@gmail.com', '2288563472', 'Disponible');

INSERT INTO Users (nombre, firstSurname, secondSurname, emailAddress, password, alternateEmail, phoneNumber, estado)
    VALUES ('Miguel Gilberto', 'Chavez', 'Gonzalez', 'migichago998@gmail.com', NULL, 'migichago999@gmail.com', '2281647833', 'Disponible');
INSERT INTO Professors (userId, staffNumber) VALUES (3, 100000002);
INSERT INTO Directors (staffNumber) VALUES (100000002);

INSERT INTO Users (nombre, firstSurname, secondSurname, emailAddress, password, alternateEmail, phoneNumber, estado)
    VALUES ('José René', 'Mendoza', 'Gonzalez', 'joremago112@gmail.com', NULL, 'joremago111@gmail.com', '2288563333', 'Disponible');
INSERT INTO Professors (userId, staffNumber) VALUES (4, 100000003);
INSERT INTO AcademicBodyHeads (staffNumber) VALUES (100000003);

INSERT INTO Users (nombre, firstSurname, secondSurname, emailAddress, password, alternateEmail, phoneNumber, estado)
    VALUES ('Mauricio', 'Ortega', 'Mújica', 'cricoso222@gmail.com', NULL, 'cricoso232@gmail.com', '2284627839', 'No Disponible');
INSERT INTO Professors (userId, staffNumber) VALUES (5, 100000004);
INSERT INTO DegreeBosses (staffNumber) VALUES (100000004);

INSERT INTO PeriodosEscolares (fechaInicio, fechaFin) VALUES ('2018-02-07', '2018-06-02');

INSERT INTO Cursos (NRC, IdPeriodoEscolar, nombre, sección, bloque, staffNumber) 
    VALUES (10001, 1, 'Proyecto Guiado', 2, 7, 100000001);

INSERT INTO EstudiantesCurso (Matrícula, NRC) VALUES ('zS10000001', 10001);

INSERT INTO ResearchProjects (título, requisitos, descripción, V°B°, bibliografíaRecomendada, fechaInicio, fechaFin, resultadosEsperados, nota)
    VALUES ('Anteproyecto de Prueba', 'NA', 'Esto es una prueba', 'No Validado', 'NA', "2000-01-01", "2000-01-02", 'NA', 'NA');
*/





/*
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE Users;
TRUNCATE Estudiantes;
TRUNCATE Professors;
TRUNCATE Directors;
TRUNCATE AcademicBodyHeads;
TRUNCATE DegreeBosses;
SET FOREIGN_KEY_CHECKS = 1;
*/


SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE Actividades;
TRUNCATE ResearchProjects;
TRUNCATE Archivos;
TRUNCATE ArchivosActividad;
TRUNCATE Avances;
TRUNCATE CuerposAcademicos;
TRUNCATE Cursos;
TRUNCATE Directors;
TRUNCATE Estudiantes;
TRUNCATE EstudiantesCurso;
TRUNCATE DegreeBosses;
TRUNCATE LGAC;
TRUNCATE PeriodosEscolares;
TRUNCATE Professors;
TRUNCATE AcademicBodyHeads;
TRUNCATE Users;
SET FOREIGN_KEY_CHECKS = 1;



