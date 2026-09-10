package ar.edu.isvdr.frontend.feature.campuses.model

data class Sede(
    val id: String,
    val nombre: String,
    val ciudad: String,
    val provincia: String,
    val direccion: String,
    val telefono: String,
    val email: String? = null,
    val horarios: String? = null,
    val activa: Boolean? = true,
    val carreras: List<SedeCarreraItem>? = null,
    val carrerasIds: List<String>? = emptyList()
)

data class SedeCarreraItem(
    val carrera: SedeCarreraInfo? = null
)

data class SedeCarreraInfo(
    val id: String,
    val nombre: String,
    val duracionAnios: Int? = null,
    val modalidad: String? = null
)