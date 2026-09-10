package ar.edu.isvdr.frontend.feature.careers.repository

import ar.edu.isvdr.frontend.core.network.RetrofitClient
import ar.edu.isvdr.frontend.feature.careers.model.Career
import ar.edu.isvdr.frontend.feature.careers.model.PreinscripcionRequest
import ar.edu.isvdr.frontend.feature.careers.model.PreinscripcionResponse
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

    suspend fun preinscribirse(request: PreinscripcionRequest): Result<PreinscripcionResponse> {
        return try {
            val response = apiService.preinscribirse(request)
            Result.success(response)
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = parseErrorMessage(errorBody, e.code())
            Result.failure(Exception(message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseErrorMessage(errorBody: String?, statusCode: Int): String {
        if (errorBody.isNullOrBlank()) return "Error en el servidor (HTTP $statusCode)"
        return try {
            val json = org.json.JSONObject(errorBody)
            val mainMessage = json.optString("message", "Error en los datos enviados")
            val detailsArray = json.optJSONArray("details")
            if (detailsArray != null && detailsArray.length() > 0) {
                val detailMessages = mutableListOf<String>()
                for (i in 0 until detailsArray.length()) {
                    val item = detailsArray.getJSONObject(i)
                    val field = item.optString("field", "")
                    val msg = item.optString("message", "")
                    if (field.isNotEmpty()) {
                        detailMessages.add("$field: $msg")
                    } else if (msg.isNotEmpty()) {
                        detailMessages.add(msg)
                    }
                }
                "$mainMessage (${detailMessages.joinToString(", ")})"
            } else {
                mainMessage
            }
        } catch (_: Exception) {
            "Error HTTP $statusCode"
        }
    }
}
