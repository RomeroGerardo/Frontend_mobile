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
}
