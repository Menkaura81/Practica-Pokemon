#  PRACTICA POKEMON

## Introducción

Esta aplicación ha sido desarrollada para cumplir con las especificaciones de la Tarea Online 3 de la asignatura Programación Multimedia y Dispositivos Móviles del grado superior en Desarrollo de Aplicaciones Multiplataforma. En ella se puede consultar la Pokedex que incluye los primeros 150 de Pokemon. Se pueden capturar los deseados y añadirlos a la lista personal del usuario. Dicha lista es persistente. Junto a esto, en Ajustes, el usuario puede configurar diversos aspectos de la aplicacióm


## Tecnologías utilizadas

En la aplicación se hacen uso de las siguientes tecnologias:

- API http Pokeapi. Gracias a esta API se puede recuperar la lista de los 150 pokemon y sus caracteristicas.

- Retrofit. Mediante el uso de esta libreria interactuamos con la API web de Pokemon.

- Firebase. De entre lo muchos módulos de Firebase, se implementan en la aplicación, la autenticación de usuario y la base de datos Firestore para la persistencia de los Pokemon capturados.

- RecyclerView. Para la muestra de las listas de Pokemon capturados y la Pokedex, se utiliza RecyclerView para poder realizar dicha tarea de forma rapida y eficiente.

- Picasso. Para la descarga y muestra de imagenes de las URL obtenidas de la API Pokemon


## Instrucciones de uso

Si se dispone de Android Studio y Git instalado en el ordenador, el repositiorio puede ser clonado en Android Estudio. El proyecto incluye todas las dependencias necesarias, por lo cual no es necesario descargar nada a parte. 

Para clonar el repositorio, pinchar en "File/New/Project from Version Control" y pegar la URL del repositorio (https://github.com/Menkaura81/Practica-Pokemon.git)

## Conclusiones del desarrollador

El desarrollo de esta tarea ha sido complejo. No tanto por el uso de Retrofit o Firebase, que estan muy bien documentados, si no por la comunicacion entre los distintos fragmentos para actualizar las listas de Pokemon, es lo que mas trabajo me ha costado implementar. Lo cierto es que Firebase es tremendamente sencillo de implementar y añade muchísimas funcionalidades a nuestra aplicación. 

El proyecto esta codificado en Java, quizá cuando termine el curso lo rehaga en Kotlin para mejorar en el desarrollo de aplicaciones Android, pero entre el trabajo y las 5 asignaturas de segundo que llevo adelante no me lo puedo permitir ahora mismo si quiero seguir el ritmo del curso.

## Capturas de pantalla 
