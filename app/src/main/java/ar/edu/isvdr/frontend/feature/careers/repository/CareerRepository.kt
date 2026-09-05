package ar.edu.isvdr.frontend.feature.careers.repository

import ar.edu.isvdr.frontend.core.network.RetrofitClient
import ar.edu.isvdr.frontend.feature.careers.model.Career
import ar.edu.isvdr.frontend.feature.careers.network.CareerApiService

class CareerRepository(
    private val apiService: CareerApiService = RetrofitClient.instance.create(CareerApiService::class.java)
) {
    suspend fun getCareers(
        buscar: String? = null,
        modalidad: String? = null,
        sede: String? = null
    ): Result<List<Career>> {
        return try {
            val response = apiService.getCareers(buscar, modalidad, sede)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCareerById(id: String): Result<Career> {
        return try {
            val response = apiService.getCareerById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
