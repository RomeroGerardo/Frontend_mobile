package ar.edu.isvdr.frontend.feature.gallery.repository

import ar.edu.isvdr.frontend.core.network.RetrofitClient
import ar.edu.isvdr.frontend.feature.gallery.model.Album
import ar.edu.isvdr.frontend.feature.gallery.network.AlbumApiService

interface AlbumRepository {
    suspend fun getAlbums(): Result<List<Album>>
    suspend fun getAlbumById(id: String): Result<Album>
}

class AlbumRepositoryImpl(
    private val apiService: AlbumApiService = RetrofitClient.instance.create(AlbumApiService::class.java)
) : AlbumRepository {

    override suspend fun getAlbums(): Result<List<Album>> {
        return try {
            val albums = apiService.getAlbums()
            Result.success(albums)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlbumById(id: String): Result<Album> {
        return try {
            val album = apiService.getAlbumById(id)
            Result.success(album)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
