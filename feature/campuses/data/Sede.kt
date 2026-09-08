package ar.edu.isvdr.frontend.feature.campuses.data

data class Sede(
    val id: String,
    val nombre: String,
    val ciudad: String,
    val provincia: String,
    val direccion: String,
    val telefono: String,
    val horarios: String,
    val carrerasIds: List<String>
)