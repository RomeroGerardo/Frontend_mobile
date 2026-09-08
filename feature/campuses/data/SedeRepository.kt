package ar.edu.isvdr.frontend.feature.campuses.data

interface SedeRepository {
    suspend fun getSedes(): List<Sede>
    suspend fun getSedeById(id: String): Sede?
    suspend fun buscarSedes(query: String): List<Sede>
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
}