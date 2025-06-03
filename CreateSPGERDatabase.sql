CREATE DATABASE SPGER;

USE SPGER;

CREATE TABLE AcademicBodyHeads (
    academicBodyHeadId INT AUTO_INCREMENT,
    PRIMARY KEY(academicBodyHeadId),
    staffNumber INT
);

CREATE TABLE Activities (
    activityId INT AUTO_INCREMENT,
    PRIMARY KEY(activityId),
    researchProjectId INT,
    comment TEXT,
    description TEXT,
    dueDate DATE,
    feedback TEXT,
    startDate DATE,
    status VARCHAR(30),
    title VARCHAR(50)
);

CREATE TABLE ActivitiesFiles (
    activityFileId INT AUTO_INCREMENT,
    PRIMARY KEY(activityFileId),
    activityId INT,
    fileId INT
);

CREATE TABLE Advances (
    advanceId INT AUTO_INCREMENT,
    PRIMARY KEY(advanceId),
    activityId INT,
    comment TEXT,
    date DATETIME,
    feedback TEXT,
    status VARCHAR(30),
    title VARCHAR(50)
);

CREATE TABLE AdvancesFiles (
    advanceFileId INT AUTO_INCREMENT,
    PRIMARY KEY(advanceFileId),
    advanceId INT,
    fileId INT
);

CREATE TABLE Courses (
    nrc INT,
    PRIMARY KEY(nrc),
    scholarPeriodId INT,
    staffNumber INT,
    block INT,
    name VARCHAR(50),
    section INT,
    status VARCHAR(30)
);

CREATE TABLE DegreeBosses (
    degreeBossId INT AUTO_INCREMENT,
    PRIMARY KEY(degreeBossId),
    staffNumber INT
);

CREATE TABLE Directors (
    directorId INT AUTO_INCREMENT,
    PRIMARY KEY(directorId),
    staffNumber INT
);

CREATE TABLE Files (
    fileId INT AUTO_INCREMENT,
    PRIMARY KEY(fileId),
    filePath TEXT
);

CREATE TABLE KGALs (
    kgalId INT AUTO_INCREMENT,
    PRIMARY KEY(kgalId),
    description TEXT
);

CREATE TABLE Professors (
    staffNumber INT,
    PRIMARY KEY(staffNumber),
    userId INT,
    status VARCHAR(30)
);

CREATE TABLE ResearchProjects (
    researchProjectId INT AUTO_INCREMENT,
    PRIMARY KEY(researchProjectId),
    studentId1 VARCHAR(10),
    studentId2 VARCHAR(10),
    directorId1 INT,
    directorId2 INT,
    directorId3 INT,
    kgalId INT,
    description TEXT,
    dueDate DATE,
    expectedResults TEXT,
    requirements TEXT,
    startDate DATE,
    suggestedBibliography TEXT,
    title TEXT,
    validationStatus VARCHAR(15)
);

CREATE TABLE ScholarPeriods (
    scholarPeriodId INT AUTO_INCREMENT,
    PRIMARY KEY(scholarPeriodId),
    endDate DATE,
    startDate DATE
);

CREATE TABLE Students (
    matricle VARCHAR(10),
    PRIMARY KEY(matricle),
    userId INT,
    status VARCHAR(30)
);

CREATE TABLE StudentsCourses (
    studentCourseId INT AUTO_INCREMENT,
    PRIMARY KEY(studentCourseId),
    matricle VARCHAR(10),
    nrc INT
);

CREATE TABLE Users (
    userId INT AUTO_INCREMENT,
    PRIMARY KEY(userId),
    alternateEmail VARCHAR(50),
    emailAddress VARCHAR(50),
    firstSurname VARCHAR(30),
    name VARCHAR(30),
    password VARCHAR(64),
    phoneNumber VARCHAR(15),
    secondSurname VARCHAR(30)
);

#CONSTRAINTS
ALTER TABLE
    AcademicBodyHeads
ADD
    CONSTRAINT FK_staffNumber_AcademicBodyHeads FOREIGN KEY(staffNumber) REFERENCES Professors(staffNumber) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Activities
ADD
    CONSTRAINT FK_researchProjectId_Activities FOREIGN KEY(researchProjectId) REFERENCES ResearchProjects(researchProjectId) ON DELETE CASCADE;

ALTER TABLE
    ActivitiesFiles
ADD
    CONSTRAINT FK_activityId_ActivitiesFiles FOREIGN KEY(activityId) REFERENCES Activities(activityId) ON DELETE CASCADE;

ALTER TABLE
    ActivitiesFiles
ADD
    CONSTRAINT FK_fileId_ActivitiesFiles FOREIGN KEY(fileId) REFERENCES Files(fileId) ON DELETE CASCADE;

ALTER TABLE
    Advances
ADD
    CONSTRAINT FK_activityId_Advances FOREIGN KEY(activityId) REFERENCES Activities(activityId) ON DELETE CASCADE;

ALTER TABLE
    AdvancesFiles
ADD
    CONSTRAINT FK_advanceId_AdvancesFiles FOREIGN KEY(advanceId) REFERENCES Advances(advanceId) ON DELETE CASCADE;

ALTER TABLE
    AdvancesFiles
ADD
    CONSTRAINT FK_fileId_AdvancesFiles FOREIGN KEY(fileId) REFERENCES Files(fileId) ON DELETE CASCADE;

ALTER TABLE
    Courses
ADD
    CONSTRAINT FK_scholarPeriodId_Courses FOREIGN KEY(scholarPeriodId) REFERENCES ScholarPeriods(scholarPeriodId) ON DELETE CASCADE;

ALTER TABLE
    Courses
ADD
    CONSTRAINT FK_staffNumber_Courses FOREIGN KEY(staffNumber) REFERENCES Professors(staffNumber) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    DegreeBosses
ADD
    CONSTRAINT FK_staffNumber_DegreeBosss FOREIGN KEY(staffNumber) REFERENCES Professors(staffNumber) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Directors
ADD
    CONSTRAINT FK_staffNumber_Directors FOREIGN KEY(staffNumber) REFERENCES Professors(staffNumber) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    Professors
ADD
    CONSTRAINT FK_userId_Professors FOREIGN KEY(userId) REFERENCES Users(userId) ON DELETE CASCADE;

ALTER TABLE
    ResearchProjects
ADD
    CONSTRAINT FK_studentId1_ResearchProjects FOREIGN KEY(studentId1) REFERENCES Students(matricle) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    ResearchProjects
ADD
    CONSTRAINT FK_studentId2_ResearchProjects FOREIGN KEY(studentId2) REFERENCES Students(matricle) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    ResearchProjects
ADD
    CONSTRAINT FK_directorId1_ResearchProjects FOREIGN KEY(directorId1) REFERENCES Directors(directorId) ON DELETE CASCADE;

ALTER TABLE
    ResearchProjects
ADD
    CONSTRAINT FK_directorId2_ResearchProjects FOREIGN KEY(directorId2) REFERENCES Directors(directorId) ON DELETE CASCADE;

ALTER TABLE
    ResearchProjects
ADD
    CONSTRAINT FK_directorId3_ResearchProjects FOREIGN KEY(directorId3) REFERENCES Directors(directorId) ON DELETE CASCADE;

ALTER TABLE
    ResearchProjects
ADD
    CONSTRAINT FK_kgalId_ResearchProjects FOREIGN KEY(kgalId) REFERENCES KGALs(kgalId) ON DELETE CASCADE;

ALTER TABLE
    Students
ADD
    CONSTRAINT FK_userId_Students FOREIGN KEY(userId) REFERENCES Users(userId) ON DELETE CASCADE;

ALTER TABLE
    StudentsCourses
ADD
    CONSTRAINT FK_studentId_StudentsCourses FOREIGN KEY(matricle) REFERENCES Students(matricle) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE
    StudentsCourses
ADD
    CONSTRAINT FK_nrc_StudentsCourses FOREIGN KEY(nrc) REFERENCES Courses(nrc) ON UPDATE CASCADE ON DELETE CASCADE;