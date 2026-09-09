package ar.edu.isvdr.frontend.feature.news.data

import ar.edu.isvdr.frontend.core.network.RetrofitClient

/**
 * Punto único de creación del repositorio de noticias/eventos,
 * reutilizando el RetrofitClient compartido (core/network).
 * Reemplazar por inyección de dependencias (Hilt) si el equipo lo adopta más adelante.
 */
object NewsEventModule {

    private val api: NewsEventApiService by lazy {
        RetrofitClient.instance.create(NewsEventApiService::class.java)
    }

    val repository: NewsEventRepository by lazy {
        NewsEventRepository(api)
    }
}