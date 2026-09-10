package ar.edu.isvdr.frontend.feature.campuses.repository

import ar.edu.isvdr.frontend.core.network.RetrofitClient
import ar.edu.isvdr.frontend.feature.campuses.model.Carrera
import ar.edu.isvdr.frontend.feature.campuses.model.Sede
import ar.edu.isvdr.frontend.feature.campuses.model.carrerasMock
import ar.edu.isvdr.frontend.feature.campuses.model.sedesMock
import ar.edu.isvdr.frontend.feature.campuses.network.SedeApiService

interface SedeRepository {
    suspend fun getSedes(): List<Sede>
    suspend fun getSedeById(id: String): Sede?
    suspend fun buscarSedes(query: String): List<Sede>
    suspend fun getCarrerasDeSede(sede: Sede): List<Carrera>
}

class SedeRepositoryImpl(
    private val apiService: SedeApiService = RetrofitClient.instance.create(SedeApiService::class.java)
) : SedeRepository {

    override suspend fun getSedes(): List<Sede> {
        return try {
            val response = apiService.getSedes()
            if (response.isNotEmpty()) response else sedesMock
        } catch (_: Exception) {
            sedesMock
        }
    }

    override suspend fun getSedeById(id: String): Sede? {
        return try {
            apiService.getSedeById(id)
        } catch (_: Exception) {
            sedesMock.find { it.id == id }
        }
    }

    override suspend fun buscarSedes(query: String): List<Sede> {
        return try {
            if (query.isBlank()) {
                val all = apiService.getSedes()
                if (all.isNotEmpty()) all else sedesMock
            } else {
                apiService.getSedes(buscar = query.trim())
            }
        } catch (_: Exception) {
            if (query.isBlank()) return sedesMock
            sedesMock.filter {
                it.ciudad.contains(query, ignoreCase = true) ||
                it.provincia.contains(query, ignoreCase = true) ||
                it.nombre.contains(query, ignoreCase = true)
            }
        }
    }

    override suspend fun getCarrerasDeSede(sede: Sede): List<Carrera> {
        val carrerasDesdeApi = sede.carreras?.mapNotNull { item ->
            item.carrera?.let { c ->
                Carrera(
                    id = c.id,
                    nombre = c.nombre,
                    duracion = c.duracionAnios?.let { "$it años" } ?: "3 años"
                )
            }
        }
        if (!carrerasDesdeApi.isNullOrEmpty()) {
            return carrerasDesdeApi
        }
        return carrerasMock.filter { it.id in (sede.carrerasIds ?: emptyList()) }
    }
}