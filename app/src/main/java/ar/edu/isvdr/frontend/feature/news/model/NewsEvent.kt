package ar.edu.isvdr.frontend.feature.news.model

/**
 * Tipo de publicación devuelto por backend.
 * Para NOTICIA la fecha propia es [NewsEvent.createdAt].
 * Para EVENTO la fecha específica es [NewsEvent.fechaEvento].
 */
enum class NewsType {
    NOTICIA,
    EVENTO;

    companion object {
        fun fromApiValue(value: String?): NewsType? =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
    }
}

/**
 * Autor embebido en la respuesta de /publicaciones.
 */
data class Author(
    val id: String,
    val nombre: String,
    val apellido: String
) {
    val nombreCompleto: String
        get() = "$nombre $apellido".trim()
}

/**
 * Representa una publicación (noticia o evento) tal como la entrega
 * GET /publicaciones y GET /publicaciones/{id}.
 *
 * Contrato confirmado con backend (swagger, 2026-09):
 * id, titulo, slug, resumen, contenido, tipo, imagenUrl, fechaEvento,
 * destacada, autorId, autor{id,nombre,apellido}, createdAt, updatedAt.
 */
data class NewsEvent(
    val id: String,
    val titulo: String,
    val slug: String,
    val resumen: String,
    val contenido: String,
    val tipo: NewsType,
    val imagenUrl: String?,
    val fechaEvento: String?,
    val destacada: Boolean,
    val autorId: String,
    val autor: Author?,
    val createdAt: String,
    val updatedAt: String
) {
    /** Fecha "propia" de la publicación según las reglas de negocio del backend. */
    val fechaPrincipal: String
        get() = if (tipo == NewsType.EVENTO && fechaEvento != null) fechaEvento else createdAt

    val esEvento: Boolean get() = tipo == NewsType.EVENTO
    val esNoticia: Boolean get() = tipo == NewsType.NOTICIA
}