package ar.edu.isvdr.frontend.feature.news.data

import ar.edu.isvdr.frontend.feature.news.model.NewsEvent
import ar.edu.isvdr.frontend.feature.news.model.NewsType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio de noticias y eventos.
 * Envuelve las llamadas Retrofit en Result<T>, siguiendo el mismo patrón
 * que CareerRepository.
 */
class NewsEventRepository(
    private val api: NewsEventApiService
) {

    suspend fun getPublicaciones(
        tipo: NewsType? = null,
        buscar: String? = null,
        destacada: Boolean? = null,
        fechaDesde: String? = null,
        fechaHasta: String? = null
    ): Result<List<NewsEvent>> = withContext(Dispatchers.IO) {
        runCatching {
            api.getPublicaciones(
                tipo = tipo?.name,
                buscar = buscar?.takeIf { it.isNotBlank() },
                destacada = destacada?.toString(),
                fechaDesde = fechaDesde,
                fechaHasta = fechaHasta
            )
        }
    }

    suspend fun getPublicacionById(id: String): Result<NewsEvent> = withContext(Dispatchers.IO) {
        runCatching { api.getPublicacionById(id) }
    }

    suspend fun getDestacadas(): Result<List<NewsEvent>> =
        getPublicaciones(destacada = true)
}