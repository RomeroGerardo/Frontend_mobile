package ar.edu.isvdr.frontend.feature.campuses.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class SedeRepositoryTest {

    private val repository: SedeRepository = SedeRepositoryImpl()

    @Test
    fun `getSedes retorna todas las sedes mock`() = runTest {
        val resultado = repository.getSedes()
        assertEquals(3, resultado.size)
    }

    @Test
    fun `getSedeById retorna la sede correcta`() = runTest {
        val sede = repository.getSedeById("1")
        assertNotNull(sede)
        assertEquals("Sede Central", sede?.nombre)
    }

    @Test
    fun `getSedeById retorna null si el id no existe`() = runTest {
        val sede = repository.getSedeById("no-existe")
        assertNull(sede)
    }

    @Test
    fun `buscarSedes filtra por ciudad`() = runTest {
        val resultado = repository.buscarSedes("Córdoba")
        assertTrue(resultado.isNotEmpty())
        assertTrue(resultado.all {
            it.ciudad.contains("Córdoba", ignoreCase = true) ||
            it.provincia.contains("Córdoba", ignoreCase = true)
        })
    }

    @Test
    fun `buscarSedes con query vacio retorna todas las sedes`() = runTest {
        val resultado = repository.buscarSedes("")
        assertEquals(3, resultado.size)
    }

    @Test
    fun `buscarSedes sin coincidencias retorna lista vacia`() = runTest {
        val resultado = repository.buscarSedes("CiudadInexistente")
        assertTrue(resultado.isEmpty())
    }
}
