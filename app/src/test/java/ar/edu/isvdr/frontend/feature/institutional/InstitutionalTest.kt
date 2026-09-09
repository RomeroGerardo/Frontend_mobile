package ar.edu.isvdr.frontend.feature.institutional

import ar.edu.isvdr.frontend.feature.institutional.model.FechaImportante
import ar.edu.isvdr.frontend.feature.institutional.model.InstitutionalInfo
import ar.edu.isvdr.frontend.feature.institutional.repository.InstitutionalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests unitarios del módulo Institucional (I5 - Gabriel).
 */
class InstitutionalTest {

    @Test
    fun `verificar creacion de modelo InstitutionalInfo`() {
        val info = InstitutionalInfo(
            nombre = "Instituto Superior Villa del Rosario",
            sigla = "ISVDR",
            lema = "Formando profesionales para el desarrollo regional",
            descripcion = "Educación pública superior técnica.",
            resena = "Historia de la institución."
        )

        assertEquals("Instituto Superior Villa del Rosario", info.nombre)
        assertEquals("ISVDR", info.sigla)
        assertEquals("Formando profesionales para el desarrollo regional", info.lema)
    }

    @Test
    fun `verificar modelo de fechas importantes`() {
        val fecha = FechaImportante(
            id = "f1",
            titulo = "Preinscripciones 2026",
            fecha = "01 Nov - 15 Dic",
            descripcion = "Preinscripción online.",
            tipo = "Inscripción"
        )

        assertEquals("f1", fecha.id)
        assertEquals("Inscripción", fecha.tipo)
    }

    @Test
    fun `verificar repositorio institucional retorna datos validos`() = runTest {
        val repository = InstitutionalRepository()

        val infoResult = repository.getInstitutionalInfo()
        val fechasResult = repository.getFechasImportantes()
        val contactoResult = repository.getContacto()
        val seccionesResult = repository.getSecciones()

        assertTrue(infoResult.isSuccess)
        assertEquals("ISVDR", infoResult.getOrNull()?.sigla)

        assertTrue(fechasResult.isSuccess)
        assertFalse(fechasResult.getOrNull().isNullOrEmpty())

        assertTrue(contactoResult.isSuccess)
        assertEquals("Villa del Rosario", contactoResult.getOrNull()?.localidad)

        assertTrue(seccionesResult.isSuccess)
        assertEquals(4, seccionesResult.getOrNull()?.size)
    }
}
