INSERT INTO `Usuarios` VALUES 
(1,'Xavier Arian','Olivares','Sánchez','zS21013906@estudiantes.uv.mx','9bcc29b5c15ebd10b5f50bef90f255bb9891197fd16d9b4fd6238ece98da89ab','prueba@correo','9212313443','Activo'),
(2,'Luis Alonso','Andrade','López','zS21816754@estudiantes.uv.mx','de0cf992e71ca6a96209a8cab7581ff4c506ea5c5216ef2c720c1416054d9e8b','prueba@correo','9212313443','Activo'),
(3,'Samuel','Martínez','Pazos','zS21684532@estudiantes.uv.mx','2a316a4dcd9b00427fcf43b9c8d832da034d2da7e3f8cca3e21a86f9a257e556','prueba@correo','9212313443','Activo'),
(4,'Jesus Manuel','Mújica','Conde','zS21894563@estudiantes.uv.mx','134a0ff7f7102b754e8031a8b0e2fb8e02e98d149e55c79d1b15a9046ecacea2','prueba@correo','9212313443','Activo'),
(5,'Luis Manuel','Casas','Vázquez','zS21849354@estudiantes.uv.mx','1e3d614b95d11b87ecccfbddbdb2b14cdaa386767436feb9b0a960202d7ddd7b','prueba@correo','9212313443','Activo'),
(6,'María De Los Ángeles','Arenas','Valdez','aarenas@uv.mx','7b9cf5a9d0d5bd5bee267820ff427c585be8abfb46939e019b710af9e2cd88d5','prueba@gmail.mx','9212313443','Activo'),
(7,'Juan Carlos','Pérez','Arriaga','elrevo@uv.mx','FcHVP2l$%8','prueba@correo.mp','9212313443','Activo'),
(8,'Jorge Octavio','Ocharán','Hernández','jocharan@uv.mx','374a9f3cb89081c3de4f25e671eb9e0822e3832f2e5723c3bd4db6f9d723838b','prueba@correo.mx','9212313443','Activo'),
(9,'Angelica','Perez','Hernández','aaperez@uv.mx','d3030cfc578fc959af598f0f0f40865a54f772bfbeb195420d740eb92e48ab83','prueba@correo.com','9212313443','Activo'),
(29,'Luis Miguel','Romero','Mendez','luimiroma444@uv.mx',NULL,'luimiroma444@uv.mx','2278489587','Disponible'),
(30,'Guiovanny Manuel','Salazar','Vazquez','churro@uv.mx',NULL,'churro@gmail.mx','2288398276','Disponible'),
(31,'Juan Manuel','Zagal','Guzmán','jumaguza@uv.mx',NULL,'jumaguza@gmail.mx','2238948576','Disponible'),
(32,'Luis Fernando','Montero','Jimenez','lufemoji@uv.mx',NULL,'t@gmail.com','2238945788','Disponible'),
(33,'Humberto','Perez','Salazar','jajaja@uv.mx','a032dfadb8503a5a56a385b6cbaa49f8369d3a23f682482e945f8f63167ecb35','caffeinated349@gmail.com','2233849586','Disponible'),
(34,'Joaquin','Guzman','Fernandez','zS21013908@estudiantes.uv.mx','a8cdce46f843cb837be8a31ccf03581a222fd45942e74e1d516d656f5f8757dc','caffeinated333@gmail.com','5566778899','Disponible'),
(41,' Sandro ','Gituerrez','Fernandez','zS21013909@estudiantes.uv.mx','8e983475219b8e1f4862c4538f72a18790ac44d0ab376288952badc8f3801d4d','caffeinated334@gmail.com','2233445566','Disponible');

INSERT INTO `Estudiantes` VALUES 
('zS21013906',1),
('zS21014907',2),
('zS24513965',3),
('zS21513396',4),
('zS21854878',5),
('zS21839498',29),
('zS23876546',30),
('zS73892039',31),
('zS21014784',32),
('zS23829128',33),
('zS72837646',34),
('zS21013908',41);

INSERT INTO `Profesores` VALUES 
(132432244,6),
(5546334,7),
(123216338,8),
(685176533,9);

INSERT INTO `ResponsablesCA` VALUES (1,132432244);

INSERT INTO `JefesCarrera` VALUES (1,123216338);

INSERT INTO `Directores` VALUES (2,5546334),(3,123216338),(1,132432244);


INSERT INTO `PeriodosEscolares` VALUES 
(1,'2022-02-02','2022-06-06'),
(2,'2022-08-15','2022-12-02'),
(3,'2023-02-07','2022-06-02'),
(4,'2023-08-21','2023-12-08');

INSERT INTO `Cursos` VALUES 
(35344,4,132432244,'Experiencia Recepcional',2,8,'Activo'),
(56423,1,5546334,'Proyecto Guiado',3,7,'Inactivo'),
(74333,3,5546334,'Proyecto Guiado',1,7,'Activo'),
(80324,3,123216338,'Experiencia Recepcional',1,9,'Activo'),
(88809,2,132432244,'Proyecto Guiado',2,8,'Inactivo');

INSERT INTO `EstudiantesCurso` VALUES 
(1,'zS21013906',35344),
(2,'zS21014907',35344),
(3,'zS24513965',35344),
(4,'zS21513396',80324),
(5,'zS21854878',56423);

INSERT INTO `LGAC` VALUES 
(1,'Educación intercultural/Estudios interculturales'),
(2,'Educación ambiental para la sustentabilidad'),
(3,'Actores sociales y disciplinas académicas'),
(4,'Investigación lingüística y didáctica de la traducción');

INSERT INTO `Anteproyectos` VALUES 
(1,'zS21513396',NULL,NULL,NULL,NULL,'¿Cómo ayuda el entretenimiento a la concentración?','Cursar 7mo semestre','Realizar una investigación acerca de los beneficios de las actividades recreativas para la concentración y el rendimiento escolar','Validado',NULL,'2023-05-28','2024-02-05',NULL),
(2,NULL,3,NULL,NULL,NULL,'Beneficios de la programación defensiva','Ser de la carrera de Ingeniería de Software','Explicar buenas prácticas sobre la programación defensiva','Validado',NULL,'2022-07-25','2023-08-28',NULL),
(3,'zS21013906',1,2,NULL,NULL,'Beneficios de dormir bien','Entrevistar a un estudiante de la facultad de ciencias de la salud','Realizar un reporte sobre los beneficios científicos de dormir al menos 8 horas al día','Validado',NULL,'2022-08-28','2023-12-24',NULL),
(7,'zS21014907',3,1,2,1,'SPGER','ef','No','Validado','ef','2023-05-09','2024-05-10','ef'),
(11,NULL,NULL,NULL,NULL,NULL,'Videojuegos y estudios','','','Propuesto','','2023-06-06','2024-06-05',''),
(23,'zS21013906',1,1,1,2,'Prueba','','','Propuesto','','2023-06-07','2023-06-22',''),
(24,NULL,NULL,NULL,NULL,NULL,'IA Con Prolog (Extreme Demon)','','Programar metaheurísticas con Prolog.','Validado','','2023-06-14','2023-06-30','');

INSERT INTO `Actividades` VALUES 
(1,1,'Actividad de prueba estudiante','Esta actividad está cargada en un anteproyecto que solo está asignado a un estudiante','2023-06-01','2023-06-01','Por entregar',NULL,NULL),
(2,2,'Actividad de prueba director','Esta actividad está cargada en un anteproyecto que tiene solo un director','2022-08-28','2022-09-15','Por entregar',NULL,NULL),
(3,3,'Actividad de prueba anteproyecto','Esta actividad está cargada en un anteproyecto que tiene dos directores y un estudiante asignado','2023-09-15','2023-09-18','Por entregar',NULL,NULL),
(8,3,'SPGER final','Entregar proyecto completamente integradillos','2023-05-29','2023-06-01','Por entregar',NULL,'Esperando su entrega'),(9,3,'Entrega ordinaria SPGER','Entregar SPGER completo y revisión de 2 horas presencial','2023-06-01','2023-06-05','Por entregar',NULL,NULL),
(10,3,'Ya deja de dormir 3 horas porfa','A mimir','2023-06-15','2023-06-30','Por entregar',NULL,'ahhhhhhhhhhhhhhhhhhhhhhhhhhhhh ya duermes xdxdx'),(11,3,'Revisar Avances de bibliografía','Revise sus referencias','2023-06-01','2023-06-02','Por entregar',NULL,'Respuestas correctas, tiene 10'),
(16,3,'Checar integración SPGER','','2023-06-01','2023-06-14',NULL,NULL,NULL),
(17,3,'Checar integración SPGER','','2023-06-01','2023-06-14',NULL,NULL,NULL),
(18,3,'Checar integración SPGER','Checar que funcionen los CUs después de una racha masiva de pulls y commits','2023-06-01','2023-06-14',NULL,NULL,NULL);