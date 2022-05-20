# wine-cellar

A super cool app to keep track of your wine.

Para la realizacion de la práctica hemos usado la extensión Duckly y Microsoft LiveShare que permite compartir código y programar a la vez. Por lo que, aunque los commits son principalmente de jgcardelus, mocalero ha participado en todo el desarrollo de la aplicación.

## Get started

Hola, ¿eres nuevo? aquí tienes una guía de como empezar.

Primero, descarga este repositorio en tu ordenador y ejecuta

```
    mvn spring-boot:run
```

Por defecto se iniciará el servido en el puerto 8080. Si te metes en localhost:8080, te saltará la página de autenticación. Puedes hacer dos cosas:

1. Iniciar sesión con usuario: user1 y contraseña: 123 y ver los datos de prueba que hemos metido
2. Crea un usuario nuevo y mira como sería la aplicación de cero, para eso haz click en "¿No tienes una cuenta?"

Una vez has iniciado sesión llegarás a la página de inventario. Esta es la página principal y desde ahí puedes ver todos los vinos. Puedes filtrar los vinos usando la barra de búsqueda o el botón de filtrar (a la derecha de la barra de búsqueda), que abrirá una página de filtros. Si haces click en el FAB de "+", puedes añadir nuevos vinos.

Pulsando en un vino, se abrirá una página de detalle de ese vino. Pulsando en las celdas, debajo del título, puedes modificar los datos rápidamente. Si quiere modificar todas las propiedades de ese vino, puedes pulsar en "Editar". Por otra parte, si pulsas en el botón flotante azul, puedes o añadir el vino a la lista de la compra o registrar que has cogido una botella.

De vuelta en la página inicial, puedes ir a "Ajustes". En ajustes, puedes modificar los tipos y orígenes de los vinos. También puedes cambiar la contraseña de tu usuario, el correo y cerrar la sesión.

En la página de "Reponer" aparecen los vinos que tienen menos de 5 unidades, para que puedas ver rápidamente que vinos comprar.

Por último, en la página de "Compras" puedes ver los vinos que has añadido a la lista de la compra y cuántas unidades hay que comprar. Pulsando en cualquier elemento de la lista te llevará a una página donde puedes modificar las unidades a comprar o quitar el vino de la lista de la compra.

## API

La api se compone de cuatro ramas principales, cada una con 5 métodos, una rama para los usuarios y una rama para los joins. La API usa autenticación Basic. El usario y la contraseña tienen que estar puestos en la cabecera de Autorización en Base64 precedidos de la palabra Basic.

La primera rama permite operar con los vinos:
GET /api/v1/wines (todos los vinos)

GET /api/v1/wines/{id} (devuelve el vino seleccionado)

POST /api/v1/wines (crea un nuevo vino)

PUT /api/v1/wines/{id} (modifica el vino seleccionado)

DELETE /api/v1/wine/{id} (borra el vino seleccionado)

La segunda rama permite operar con los tipos de vino:
GET /api/v1/tipos (todos los tipos)

GET /api/v1/tipos/{id} (devuelve el tipo seleccionado)

POST /api/v1/tipos (crea un nuevo tipo)

GET /api/v1/tipos/{id} (un tipo)

PUT /api/v1/tipos/{id} (modifica el tipo seleccionado)

DELETE /api/v1/tipos/{id} (borra el tipo seleccionado)

La tercera rama permite operar con las denominaciones de origen:

GET /api/v1/origins (todos las denominaciones)

GET /api/v1/origins/{id} (devuelve la denominacion seleccionada)

POST /api/v1/origins (crea una nueva denominacion)

PUT /api/v1/origins/{id} (modifica la denominacion seleccionada)

DELETE /api/v1/origins/{id} (borra la denominacion seleccionada)

La cuarta rama permite añadir vinos a una lista de la compra.

GET /api/v1/purchases (todas las compras)

GET /api/v1/purchases/{id} (devuelve la compra seleccionada)

POST /api/v1/purchases (crea una nueva compra)

PUT /api/v1/purchases/{id} (modifica la compra seleccionada)

DELETE /api/v1/purchases/{id} (borra la compra seleccionada)

La rama de los usuarios permite obtener la información del ususario que está registrado, registrar nuevos usuarios y actualizar usuarios.

GET /api/v1/users (el usuario que está autenticado)

POST /api/v1/users (crea un nuevo usuario)

PUT /api/v1/users (modifica un usuario ya creado)

Por último, tenemos una rama dónde se pueden acceder a los joins, que ofrecen la misma información que se podría obtener con las otras ramas pero de manera más concisa.

GET /api/v1/extended/wines/ (permite obtener la información de los vinos junto con su tipo y su origen)

GET /api/v1/extended/purchases/ (obtener la compra junto con el nombre del vino, e información extra del vino)

## ¿Cómo está organizado el proyecto?

El proyecto sigue la estrucutra Spring Boot convencional. Las carpetas son "config", "model", "repository", "joins", "services", "controllers".

-   config: Contiene la configuración de Spring Security
-   model: Modelos en Java de las tablas de la base de datos
-   repository: Operaciones con las bases de datos
-   joins: Modelos extendidos de las tablas de la base de datos (se utilizan en los services y controllers)
-   services: Operaciones más complejas con las bases de datos
-   controllers: Responden a las peticiones HTTP

Todos los métodos de los controllers tienen tests E2E para verificar su funcionamiento.

## Seguridad

La seguridad de la página web la hemos hecho a través de Spring Security. Todas las páginas y endpoints requieren autenticación excepto los archivos estáticos (js, css, png, etc.) y la autenticación, autorización y registro de usuarios.
