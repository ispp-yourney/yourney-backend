# Repositorio de desarrollo backend para la aplicación Yourney
## Estado de indicadores de integración continua
### Travis CI
 **Estado de Travis CI en rama master:** [![Build Status](https://travis-ci.org/ispp-yourney/yourney-backend.svg?branch=master)](https://travis-ci.org/ispp-yourney/yourney-backend)
 **Estado de Travis CI en rama develop:** [![Build Status](https://travis-ci.org/ispp-yourney/yourney-backend.svg?branch=develop)](https://travis-ci.org/ispp-yourney/yourney-backend)

### Codacy
**Calidad de código:** [![Codacy Badge](https://app.codacy.com/project/badge/Grade/d0e08d33bb0f40dcac3dd907761943f6)](https://www.codacy.com/gh/ispp-yourney/yourney-backend/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ispp-yourney/yourney-backend&amp;utm_campaign=Badge_Grade)

## Introducción
Desde Yourney, ofrecemos   la   posibilidad   de   compartir   rutas   turísticas   entre   usuarios   de   todo   el   mundo   y promocionarlas por parte de establecimientos e instituciones, con el fin de enriquecer la experiencia que obtengan en sus viajes nuestros usuarios, y de aportar valor a las organizaciones que colaboren con nuestra organización.

Para obtener más información acerca del proyecto, invitamos a revisar nuestra landing page, en la que podrá obtener información acerca de nuestro proyecto, objetivos, e integrantes.

## Estructura del código
En este repositorio, se encuentra el código fuente de la parte de backend correspondiente al proyecto Yourney. 
Las tecnologías que emplea son:

* SpringBoot como framework de desarrollo.
* PostgreSQL como base de datos del proyecto.

En el siguiente diagrama, puede encontrar una simplificación de la estructura de la aplicación.
![Diagrama de componentes de la aplicación](https://user-images.githubusercontent.com/55277082/111430780-26c09280-86fb-11eb-8611-aa4cb67376ad.PNG)

Cada vez que un usuario realiza una operación de push al repositorio, sea la rama que sea, se lanza el procedimiento de integración continua, mientras que en las ramas develop y master son las únicas en las que se realiza la automatización del despliegue, y únicamente sobre develop el análisis de la calidad del código.

## Configuración inicial
Para hacer funcionar este componente, será necesario Java, en su versión 8, junto con una instalación de la base de datos PostgreSQL en una versión reciente (probado con 9.4 como mínimo), con nombre ```yourney_db``` , y usuario y contraseña ```postgres``` . 

Puede ejecutar los test con el siguiente comando ejecutado desde la raíz del proyecto.
```
mvn test -B
```
