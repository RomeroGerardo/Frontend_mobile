package ar.edu.isvdr.frontend.feature.gallery.model

data class Album(
    val id: String,
    val titulo: String,
    val slug: String? = null,
    val descripcion: String? = null,
    val fecha: String? = null,
    val activo: Boolean = true,
    val portada: AlbumImagen? = null,
    val imagenes: List<AlbumImagen> = emptyList(),
    val cantidadImagenes: Int = 0
)

data class AlbumImagen(
    val id: String? = null,
    val url: String,
    val width: Int? = null,
    val height: Int? = null,
    val formato: String? = null
)
