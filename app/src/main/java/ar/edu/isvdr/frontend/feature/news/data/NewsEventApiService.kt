package ar.edu.isvdr.frontend.feature.news.data

import ar.edu.isvdr.frontend.feature.news.model.NewsEvent
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Cliente Retrofit del recurso /publicaciones.
 * Mobile es solo lectura: se consumen únicamente los dos GET.
 * POST / PATCH / DELETE quedan para el backoffice web.
 */
interface NewsEventApiService {

    /**
     * Listado de publicaciones, ordenado por createdAt descendente.
     * Todos los filtros son opcionales y combinables.
     *
     * @param tipo "NOTICIA" o "EVENTO"
     * @param buscar búsqueda parcial en titulo, resumen y contenido
     * @param destacada "true" o "false"
     * @param fechaDesde ISO 8601 (ej. 2026-01-01T00:00:00.000Z)
     * @param fechaHasta ISO 8601 (ej. 2026-12-31T23:59:59.000Z)
     */
    @GET("publicaciones")
    suspend fun getPublicaciones(
        @Query("tipo") tipo: String? = null,
        @Query("buscar") buscar: String? = null,
        @Query("destacada") destacada: String? = null,
        @Query("fechaDesde") fechaDesde: String? = null,
        @Query("fechaHasta") fechaHasta: String? = null
    ): List<NewsEvent>

    @GET("publicaciones/{id}")
    suspend fun getPublicacionById(
        @Path("id") id: String
    ): NewsEvent
}