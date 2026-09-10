package ar.edu.isvdr.frontend.feature.careers.model

/**
 * Modelo de datos que representa una Carrera.
 * 
 * Actualizado según el contrato de la API.
 */
data class Career(
    val id: String,
    val nombre: String,
    val slug: String,
    val descripcion: String?,
    val duracionAnios: Int?,
    val tituloOtorgado: String?,
    val modalidad: String,
    val activa: Boolean,
    val imagenUrl: String? = null,
    val createdAt: String,
    val updatedAt: String
)
