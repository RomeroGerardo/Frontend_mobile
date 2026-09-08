package ar.edu.isvdr.frontend.feature.campuses.data

// TODO: Reemplazar por el modelo real de Carrera del módulo de Gerardo
// cuando exponga getCarrerasByIds(). Este modelo es temporal.
data class Carrera(
    val id: String,
    val nombre: String,
    val duracion: String
)

val carrerasMock = listOf(
    Carrera("c1", "Tecnicatura en Programación", "2 años"),
    Carrera("c2", "Ingeniería en Sistemas", "5 años"),
    Carrera("c3", "Tecnicatura en Redes", "2 años")
)
