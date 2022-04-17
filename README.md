# wine-cellar

A super cool app to keep track of your wine.

Para la realizacion de la práctica hemos usado la extensión Duckly que permite compartir código y programar a la vez.

## Notas importantes

Para la creación de las tablas hemos utilizado JPA y Hibernate. La API de JPA permite utilizar SpEL (Spring Expression Language) que es más potente que una sentencia SQL tradicional. Esto nos ha permitido evitar duplicación de código a la hora de implementar los servicios para Wine, Tipo y Denominación. Esto lo hemos logrado a través de CrudServiceImplementation, una clase genérica que implementa los métodos CRUD.

Por otra parte, hemos utilizado JDBC para los joins cuyo resultado se puede obtener a través de /api/v1/join/wines/tipos y /api/v1/join/wines/denominaciones.

Finalmente, esta aplicación es una versión reducida de la práctica final. Como nuestra práctica final tiene usuarios, hemos creado la tabla "CLIENTS" y la hemos asociado con las otras tablas. Al no tener autenticación, no podemos ver qué usuario está haciendo la petición. Por tanto, hemos asumido que todas las peticiones vienen de el usuario con ID 1. En prácticas futuras modificaremos esto para que el usuario sea dinámico, en función de la autenticación que provea.

## API

La api se compone de tres ramas principales, cada una con 5 métodos y una rama para los joins.

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
GET /api/v1/denominaciones (todos las denominaciones)
GET /api/v1/denominaciones/{id} (devuelve la denominacion seleccionada)
POST /api/v1/denominaciones (crea una nueva denominacion)
PUT /api/v1/denominaciones/{id} (modifica la denominacion seleccionada)
DELETE /api/v1/denominaciones/{id} (borra la denominacion seleccionada)

Por último, tenemos una rama dónde se pueden acceder a los joins, que ofrecen la misma información que se podría obtener con las otras ramas pero de manera más concisa.
GET /api/v1/join/wines/tipos (obtener el tipo de cada vino)
GET /api/v1/join/wines/denominaciones (obtener la denominación de cada vino)

## Nos gustaría que vieses

-   UserCrudRepository: Usamos SpEL para crear una Query reutilizable para disintos Entities. Esta Query filtra una tabla en función del usuario. Heredan de esta clase todos los repositories menos UserRepository.
-   CrudServiceImplementation: Una clase que implementa los métodos CRUD de manera genérica y que evita mucha duplicación de código.
