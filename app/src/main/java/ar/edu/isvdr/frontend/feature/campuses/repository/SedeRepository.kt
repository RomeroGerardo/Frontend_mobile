package ar.edu.isvdr.frontend.feature.campuses.repository

import ar.edu.isvdr.frontend.feature.campuses.model.Carrera
import ar.edu.isvdr.frontend.feature.campuses.model.Sede
import ar.edu.isvdr.frontend.feature.campuses.model.carrerasMock
import ar.edu.isvdr.frontend.feature.campuses.model.sedesMock

interface SedeRepository {
    suspend fun getSedes(): List<Sede>
    suspend fun getSedeById(id: String): Sede?
    suspend fun buscarSedes(query: String): List<Sede>
    suspend fun getCarrerasDeSede(sede: Sede): List<Carrera> // temporal, ver TODO en CarreraMock.kt
}

class SedeRepositoryImpl : SedeRepository {

    private val sedes = sedesMock

    override suspend fun getSedes(): List<Sede> = sedes

    override suspend fun getSedeById(id: String): Sede? =
        sedes.find { it.id == id }

    override suspend fun buscarSedes(query: String): List<Sede> {
        if (query.isBlank()) return sedes
        return sedes.filter {
            it.ciudad.contains(query, ignoreCase = true) ||
            it.provincia.contains(query, ignoreCase = true) ||
            it.nombre.contains(query, ignoreCase = true)
        }
    }

    // TODO: reemplazar por una llamada real al repositorio de Carreras de Gerardo
    override suspend fun getCarrerasDeSede(sede: Sede): List<Carrera> =
        carrerasMock.filter { it.id in sede.carrerasIds }
}