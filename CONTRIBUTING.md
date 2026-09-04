# Guía de trabajo del equipo

## Regla principal

`main` debe permanecer estable. Cada tarjeta de Trello se trabaja en una rama propia y se integra mediante Pull Request.

## Crear una rama

Desde una copia actualizada del repositorio:

```bash
git switch main
git pull origin main
git switch -c feature/iX-nombre-corto
```

Ejemplos:

```bash
git switch -c feature/i1-base-naomi
git switch -c feature/i2-carreras-gerardo
git switch -c feature/i3-sedes-martin
git switch -c feature/i4-noticias-maxi
git switch -c feature/i5-institucional-gabriel
```

## Para cada tarjeta

1. Mover la tarjeta a `En progreso`.
2. Crear la rama desde `main`.
3. Trabajar solamente dentro de la carpeta asignada.
4. Probar que compile con `./gradlew.bat :app:assembleDebug`.
5. Hacer commits pequeños y claros, por ejemplo `feat(i3): crear listado de sedes`.
6. Subir la rama: `git push -u origin nombre-de-la-rama`.
7. Abrir un Pull Request hacia `main`.
8. Mover la tarjeta a `En revisión` y luego a `Listo` cuando se apruebe.

## Límites entre features

- `feature/base`: tema, navegación y componentes compartidos.
- `feature/careers`: listado, búsqueda, filtros y detalle de carreras.
- `feature/campuses`: listado, búsqueda, detalle y carreras asociadas a sedes.
- `feature/news`: noticias, eventos, filtros y detalle de publicaciones.
- `feature/institutional`: institucional, misión, historia, vida institucional, fechas y contacto.

Si una tarea necesita modificar código compartido, avisar antes en el Pull Request para evitar conflictos.

