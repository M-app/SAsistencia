# Asistencia Estudiantil Android
Esta aplicación permite llevar el registro de asistencia de los alumnos diariamente, para luego cada día construir un reporte de todos los alumnos faltantes y notificar al número de teléfono registrado mediante un mensaje SMS sobre su inansistencia. 


## Proceso para Maestros
1. Crear una cuenta en la aplicación
2. Iniciar Sesión en la aplicación
3. Seleccionar el grado al que se desea registrar la asistencia
4. Seleccionar alumnos inasistentes
5. Enviar el reporte

## Proceso para Administradores
1. Iniciar Sesión en la aplicación
2. Ingresar a la pestaña reportes
3. Ver los faltantes de todo el establecimiento
4. Presionar el botón "Notificar"

## Tecnologías Utilizadas

### Lenguaje
Esta aplicación está desarrollada en **JAVA**

### Almacenamiento
Ya que la aplicación será ejecutada por varios maestros y administradores al mismo tiempo, se está utilizando la base de datos en tiempo real de **FIREBASE**

### Autenticación
Para la creación de cuentas y validación del inicio de sesión, así como también para guardar la sesión se utiliza **FIREBASE AUTH**

### Arquitectura de la Aplicación
Se ha utilizado una arquitectura **MVP** + **Clean** + **Repository**

## Agregar Nuevas Características
Ya que se almacena la asistencia de los estudiantes cada día de una manera estructurada y fácil de leer, se implementara el almacenamiento de reportes de disciplina y el registro de las notas acumuladas en cada uno de los cursos asignados al grado del estudiante. Estas características son a futuro. 

## Capturas de la Aplicación
### Crear una Cuenta
![Registro](/imagenes/registro.jpg)
### Inicio de Sesión
![Iniciar Sesión](/imagenes/iniciarSesion.jpg)
### Listado de Grados
![Listado de Grados](/imagenes/variosGrados.jpg)
### Listado de Alumnos
![Alumnos](/imagenes/listaAlumnos.jpg)
### Agregar Alumnos
![Nuevo Alumno](/imagenes/agregarAlumnos.jpg)
### Quitar Alumnos
![Quitar Alumnos](/imagenes/eliminarAlumnos.jpg)
### Modificar Alumnos
![Modificar Alumnos](/imagenes/modificarAlumnos.jpg)
### Crear Reportes de Assitencia
![Reportes](/imagenes/crearReporteAsistencia.jpg)
### Tomar Asistencia
* Solo se deben marcar las inasistencias
* Las inasistencias de cada grado quedan guardadas
* Se puede consultar el reporte de asistencia de cualquier fecha

![Asistencia](/imagenes/tomarAsistencia.jpg)
### Ver Reporte de Asistencia
* Los ver que reportes ya están listos y quien los ha creado

![Reportes](/imagenes/verReportes.jpg)
![Varios Reportes](/imagenes/variosReportesMismoTiempo.jpg)

### Ver Faltas de Alumnos
![Faltas Alumnos](/imagenes/verFaltasAlumnos.jpg)

### Ver Faltas de Todos los Grados

![Faltas Grado](/imagenes/verFaltantesGrado.jpg)
![Faltas Grado](/imagenes/verFaltantesVariosGrados.jpg)

### Notificar Faltas A Encargados o Padres de Familia
![Notificar](/imagenes/enviarSMS.jpg)

