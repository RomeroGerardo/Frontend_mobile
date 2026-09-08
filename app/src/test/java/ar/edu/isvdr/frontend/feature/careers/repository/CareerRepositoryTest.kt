package ar.edu.isvdr.frontend.feature.careers.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class CareerRepositoryTest {

    @Test
    fun `verificar que el repositorio obtiene datos de la API real`() = runBlocking {
        // Arrange
        val repository = CareerRepository()

        // Act
        val result = repository.getCareers()

        // Assert
        assertTrue("La llamada a la API debería ser exitosa", result.isSuccess)
        val list = result.getOrNull()
        assertTrue("La lista no debería ser nula", list != null)
        
        // Si hay datos, mostramos el primero en consola (para debug visual)
        if (list!!.isNotEmpty()) {
            println("Carrera obtenida desde el backend: ${list[0].nombre}")
        } else {
            println("El backend devolvió una lista vacía (sin carreras activas).")
        }
    }
}
