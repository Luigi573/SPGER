CREATE DATABASE SPGER;

USE SPGER;

CREATE TABLE Actividades(
    IdActividad INT AUTO_INCREMENT,
    PRIMARY KEY(IdActividad),
    IdAnteproyecto INT,
    título NVARCHAR(50),
    descripción TEXT,
    fechaInicio DATE,
    fechaFin DATE,
    estado NVARCHAR(30),
    comentario TEXT,
    retroalimentación TEXT
);

CREATE TABLE Anteproyectos(
    IdAnteproyecto INT AUTO_INCREMENT,
    PRIMARY KEY(IdAnteproyecto),
    Matrícula1 VARCHAR(10),
    Matrícula2 VARCHAR(10),
    IdDirector1 INT,
    IdDirector2 INT,
    IdDirector3 INT,
    IdLGAC INT,
    título TEXT,
    requisitos TEXT,
    descripción TEXT,
    V°B° NVARCHAR(15),
    bibliografíaRecomendada TEXT,
    fechaInicio DATE,
    fechaFin DATE,
    resultadosEsperados TEXT
);

CREATE TABLE Archivos(
    IdArchivo INT AUTO_INCREMENT,
    PRIMARY KEY(IdArchivo),
    ruta TEXT
);

CREATE TABLE ArchivosActividad(
    IdArchivoActividad INT AUTO_INCREMENT,
    PRIMARY KEY(IdArchivoActividad),
    IdArchivo INT,
    IdActividad INT
);

CREATE TABLE Avances(
    IdAvance INT AUTO_INCREMENT,
    PRIMARY KEY(IdAvance),
    IdActividad INT,
    IdArchivo INT,
    título NVARCHAR(50),
    comentario TEXT,
    estado NVARCHAR(30),
    fecha DATETIME,
    retroalimentación TEXT
);

CREATE TABLE CuerposAcademicos(
    IdCuerpoAcademico INT AUTO_INCREMENT,
    PRIMARY KEY(IdCuerpoAcademico),
    IdResponsableCA INT,
    descripción TEXT
);

CREATE TABLE Cursos(
    NRC INT,
    PRIMARY KEY(NRC),
    IdPeriodoEscolar INT,
    NumPersonal INT,
    nombre NVARCHAR(50),
    sección INT,
    bloque INT,
    estado NVARCHAR(30)
);

CREATE TABLE Directores(
    IdDirector INT AUTO_INCREMENT,
    PRIMARY KEY(IdDirector),
    NumPersonal INT
);

CREATE TABLE Estudiantes(
    Matrícula VARCHAR(10),
    PRIMARY KEY(Matrícula),
    IdUsuario INT
);

CREATE TABLE EstudiantesCurso(
    IdEstudianteCurso INT AUTO_INCREMENT,
    PRIMARY KEY(IdEstudianteCurso),
    Matrícula VARCHAR(10),
    NRC INT
);

CREATE TABLE JefesCarrera(
    IdJefeCarrera INT AUTO_INCREMENT,
    PRIMARY KEY(IdJefeCarrera),
    NumPersonal INT
);

CREATE TABLE LGAC(
    IdLGAC INT AUTO_INCREMENT,
    PRIMARY KEY(IdLGAC),
    descripción TEXT
);

CREATE TABLE Profesores(
    NumPersonal INT,
    PRIMARY KEY(NumPersonal),
    IdUsuario INT
);

CREATE TABLE PeriodosEscolares(
    IdPeriodoEscolar INT AUTO_INCREMENT,
    PRIMARY KEY(IdPeriodoEscolar),
    fechaInicio DATE,
    fechaFin DATE
);

CREATE TABLE ResponsablesCA(
    IdResponsableCA INT AUTO_INCREMENT,
    PRIMARY KEY(IdResponsableCA),
    NumPersonal INT
);

CREATE TABLE Usuarios(
    IdUsuario INT AUTO_INCREMENT,
    PRIMARY KEY(IdUsuario),
    nombre NVARCHAR(30),
    apellidoPaterno NVARCHAR(30),
    apellidoMaterno NVARCHAR(30),
    correo NVARCHAR(50),
    contraseña NVARCHAR(64),
    correoAlterno NVARCHAR(50),
    numeroTelefono NVARCHAR(15),
    estado NVARCHAR(30)
);

#CONSTRAINTS
ALTER TABLE
    Actividades
ADD
    CONSTRAINT FK_IdAnteproyecto_Actividades FOREIGN KEY(IdAnteproyecto) REFERENCES Anteproyectos(IdAnteproyecto) ON DELETE CASCADE;

ALTER TABLE
    Anteproyectos
ADD
    CONSTRAINT FK_Matrícula1_Anteproyectos FOREIGN KEY(Matrícula1) REFERENCES Estudiantes(Matrícula) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Anteproyectos
ADD
    CONSTRAINT FK_Matrícula2_Anteproyectos FOREIGN KEY(Matrícula2) REFERENCES Estudiantes(Matrícula) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Anteproyectos
ADD
    CONSTRAINT FK_IdDirector1_Anteproyectos FOREIGN KEY(IdDirector1) REFERENCES Directores(IdDirector) ON DELETE CASCADE;

ALTER TABLE
    Anteproyectos
ADD
    CONSTRAINT FK_IdDirector2_Anteproyectos FOREIGN KEY(IdDirector2) REFERENCES Directores(IdDirector) ON DELETE CASCADE;

ALTER TABLE
    Anteproyectos
ADD
    CONSTRAINT FK_IdDirector3_Anteproyectos FOREIGN KEY(IdDirector3) REFERENCES Directores(IdDirector) ON DELETE CASCADE;

ALTER TABLE
    Anteproyectos
ADD
    CONSTRAINT FK_IdLGAC_Anteproyectos FOREIGN KEY(IdLGAC) REFERENCES LGAC(IdLGAC) ON DELETE CASCADE;

ALTER TABLE
    ArchivosActividad
ADD
    CONSTRAINT FK_IdArchivo_ArchivosActividad FOREIGN KEY(IdArchivo) REFERENCES Archivos(IdArchivo) ON DELETE CASCADE;

ALTER TABLE
    Avances
ADD
    CONSTRAINT FK_IdActividad_Avances FOREIGN KEY(IdActividad) REFERENCES Actividades(IdActividad) ON DELETE CASCADE;

ALTER TABLE
    Avances
ADD
    CONSTRAINT FK_IdArchivo_Avances FOREIGN KEY(IdArchivo) REFERENCES Archivos(IdArchivo) ON DELETE CASCADE;

ALTER TABLE
    CuerposAcademicos
ADD
    CONSTRAINT FK_IdResponsableCA_CuerposAcademicos FOREIGN KEY(IdResponsableCA) REFERENCES ResponsablesCA(IdResponsableCA) ON DELETE CASCADE;

ALTER TABLE
    Cursos
ADD
    CONSTRAINT FK_IdPeriodoEscolar_Cursos FOREIGN KEY(IdPeriodoEscolar) REFERENCES PeriodosEscolares(IdPeriodoEscolar) ON DELETE CASCADE;

ALTER TABLE
    Cursos
ADD
    CONSTRAINT PK_NumPersonal_Cursos FOREIGN KEY(NumPersonal) REFERENCES Profesores(NumPersonal) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Directores
ADD
    CONSTRAINT FK_NumPersonal_Directores FOREIGN KEY(NumPersonal) REFERENCES Profesores(NumPersonal) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Estudiantes
ADD
    CONSTRAINT FK_IdUsuario_Estudiantes FOREIGN KEY(IdUsuario) REFERENCES Usuarios(IdUsuario) ON DELETE CASCADE;

ALTER TABLE
    EstudiantesCurso
ADD
    CONSTRAINT FK_Matrícula_EstudiantesCurso FOREIGN KEY(Matrícula) REFERENCES Estudiantes(Matrícula) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    EstudiantesCurso
ADD
    CONSTRAINT FK_NRC_EstudiantesCurso FOREIGN KEY(NRC) REFERENCES Cursos(NRC) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    JefesCarrera
ADD
    CONSTRAINT FK_NumPersonal_JefesCarrera FOREIGN KEY(NumPersonal) REFERENCES Profesores(NumPersonal) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Profesores
ADD
    CONSTRAINT FK_IdUsuario_Profesores FOREIGN KEY(IdUsuario) REFERENCES Usuarios(IdUsuario) ON DELETE CASCADE;

ALTER TABLE
    ResponsablesCA
ADD
    CONSTRAINT FK_NumPersonal_ResponsablesCA FOREIGN KEY(NumPersonal) REFERENCES Profesores(NumPersonal) ON UPDATE CASCADE ON DELETE CASCADE;