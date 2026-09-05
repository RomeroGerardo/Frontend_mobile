package ar.edu.isvdr.frontend.feature.careers.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CareerTest {

    @Test
    fun `verificar creacion de modelo Career`() {
        // Arrange (preparar datos)
        val career = Career(
            id = "clc1a2b3c4d5e6f7g8h9i0j1",
            nombre = "Tecnicatura Superior en Programacion",
            slug = "tecnicatura-superior-en-programacion",
            descripcion = "Carrera orientada al desarrollo de software.",
            duracionAnios = 3,
            tituloOtorgado = "Tecnico Superior en Programacion",
            modalidad = "PRESENCIAL",
            activa = true,
            createdAt = "2026-09-05T10:00:00Z",
            updatedAt = "2026-09-05T10:00:00Z"
        )

        // Act & Assert (verificar que los campos se asignan correctamente)
        assertEquals("clc1a2b3c4d5e6f7g8h9i0j1", career.id)
        assertEquals("Tecnicatura Superior en Programacion", career.nombre)
        assertEquals("PRESENCIAL", career.modalidad)
        assertEquals(3, career.duracionAnios)
        assertEquals(true, career.activa)
    }
}
