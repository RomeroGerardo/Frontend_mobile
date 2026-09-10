package ar.edu.isvdr.frontend.feature.institutional.repository

import ar.edu.isvdr.frontend.feature.institutional.model.ContactoInfo
import ar.edu.isvdr.frontend.feature.institutional.model.FechaImportante
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalInfo
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalMockData
import ar.edu.isvdr.frontend.feature.institutional.model.MisionVision
import ar.edu.isvdr.frontend.feature.institutional.model.SeccionInstitucional

/**
 * Repositorio de datos para el módulo Institucional (I5 - Gabriel).
 * Provee la información institucional inicial y está preparado para conectarse a la API.
 */
class InstitutionalRepository {

    suspend fun getInstitutionalInfo(): Result<InstitutionalInfo> {
        return try {
            Result.success(InstitutionalMockData.infoGeneral)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMisionVision(): Result<MisionVision> {
        return try {
            Result.success(InstitutionalMockData.misionVision)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFechasImportantes(): Result<List<FechaImportante>> {
        return try {
            Result.success(InstitutionalMockData.fechasImportantes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getContacto(): Result<ContactoInfo> {
        return try {
            Result.success(InstitutionalMockData.contacto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSecciones(): Result<List<SeccionInstitucional>> {
        return try {
            Result.success(InstitutionalMockData.secciones)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getValoresDetallados(): Result<List<ar.edu.isvdr.frontend.feature.institutional.model.ValorDetalle>> {
        return try {
            Result.success(InstitutionalMockData.valoresDetallados)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHitosHistoricos(): Result<List<ar.edu.isvdr.frontend.feature.institutional.model.HitoHistorico>> {
        return try {
            Result.success(InstitutionalMockData.hitosHistoricos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getActividadesVidaInstitucional(): Result<List<ar.edu.isvdr.frontend.feature.institutional.model.ActividadVidaInstitucional>> {
        return try {
            Result.success(InstitutionalMockData.actividadesVidaInstitucional)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAreasContacto(): Result<List<ar.edu.isvdr.frontend.feature.institutional.model.AreaContacto>> {
        return try {
            Result.success(InstitutionalMockData.areasContacto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPreguntasFrecuentes(): Result<List<ar.edu.isvdr.frontend.feature.institutional.model.PreguntaFrecuente>> {
        return try {
            Result.success(InstitutionalMockData.preguntasFrecuentes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private val contactoApi: ar.edu.isvdr.frontend.feature.institutional.network.ContactoApiService by lazy {
        ar.edu.isvdr.frontend.core.network.RetrofitClient.instance.create(
            ar.edu.isvdr.frontend.feature.institutional.network.ContactoApiService::class.java
        )
    }

    suspend fun enviarMensajeContacto(
        request: ar.edu.isvdr.frontend.feature.institutional.network.MensajeContactoRequest
    ): Result<ar.edu.isvdr.frontend.feature.institutional.network.MensajeContactoResponse> {
        return try {
            val response = contactoApi.enviarMensaje(request)
            Result.success(response)
        } catch (e: Exception) {
            val msg = parseErrorMessage(e)
            Result.failure(Exception(msg))
        }
    }

    private fun parseErrorMessage(exception: Exception): String {
        if (exception !is retrofit2.HttpException) {
            return exception.localizedMessage ?: "Error de conexión"
        }
        val statusCode = exception.code()
        val errorBody = exception.response()?.errorBody()?.string() ?: return "Error HTTP $statusCode"
        return try {
            val json = org.json.JSONObject(errorBody)
            val mainMessage = json.optString("message", "Error al procesar la solicitud")
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

