package ar.edu.isvdr.frontend.feature.careers.model

/**
 * Modelo de datos que representa una Carrera.
 *
 * TODO: Confirmar con el equipo de backend los campos exactos de la API.
 * Por ahora se incluyen campos representativos para listado, detalle y
 * la URL para el botón de preinscripción.
 */
data class Career(
    val id: String,
    val name: String,
    val description: String,
    val modality: String,
    val duration: String,
    val preinscriptionUrl: String? = null,
    val imageUrl: String? = null
)
