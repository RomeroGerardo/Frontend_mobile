package ar.edu.isvdr.frontend.feature.careers.model

/**
 * Datos requeridos por el backend para registrar una preinscripción pública (POST /api/preinscripciones).
 */
data class PreinscripcionRequest(
    val nombre: String,
    val apellido: String,
    val documento: String,
    val fechaNacimiento: String, // Formato: YYYY-MM-DD
    val nacionalidad: String,
    val direccion: String,
    val localidad: String,
    val provincia: String,
    val telefono: String,
    val email: String,
    val carreraId: String
)

data class PreinscripcionResponse(
    val id: String? = null,
    val nombre: String? = null,
    val apellido: String? = null,
    val documento: String? = null,
    val email: String? = null,
    val carreraId: String? = null,
    val createdAt: String? = null
)
