package ar.edu.isvdr.frontend.feature.gallery.network

import ar.edu.isvdr.frontend.feature.gallery.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Album
}
