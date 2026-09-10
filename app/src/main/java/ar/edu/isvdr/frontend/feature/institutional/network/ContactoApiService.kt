package ar.edu.isvdr.frontend.feature.institutional.network

import retrofit2.http.Body
import retrofit2.http.POST

data class MensajeContactoRequest(
    val nombre: String,
    val email: String,
    val telefono: String? = null,
    val asunto: String? = null,
    val mensaje: String
)

data class MensajeContactoResponse(
    val id: String? = null,
    val nombre: String? = null,
    val email: String? = null,
    val asunto: String? = null,
    val mensaje: String? = null,
    val createdAt: String? = null
)

interface ContactoApiService {
    @POST("contacto")
    suspend fun enviarMensaje(@Body request: MensajeContactoRequest): MensajeContactoResponse
}
