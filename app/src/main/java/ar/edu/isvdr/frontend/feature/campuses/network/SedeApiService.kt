package ar.edu.isvdr.frontend.feature.campuses.network

import ar.edu.isvdr.frontend.feature.campuses.model.Sede
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SedeApiService {

    @GET("sedes")
    suspend fun getSedes(
        @Query("buscar") buscar: String? = null
    ): List<Sede>

    @GET("sedes/{id}")
    suspend fun getSedeById(
        @Path("id") id: String
    ): Sede
}
