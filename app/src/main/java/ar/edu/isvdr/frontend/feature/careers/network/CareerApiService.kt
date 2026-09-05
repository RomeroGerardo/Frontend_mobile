package ar.edu.isvdr.frontend.feature.careers.network

import ar.edu.isvdr.frontend.feature.careers.model.Career
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CareerApiService {
    
    @GET("carreras")
    suspend fun getCareers(
        @Query("buscar") buscar: String? = null,
        @Query("modalidad") modalidad: String? = null,
        @Query("sede") sede: String? = null
    ): List<Career>

    @GET("carreras/{id}")
    suspend fun getCareerById(
        @Path("id") id: String
    ): Career
}
