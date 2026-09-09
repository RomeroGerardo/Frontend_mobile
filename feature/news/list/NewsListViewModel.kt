package ar.edu.isvdr.frontend.feature.news.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.isvdr.frontend.feature.news.data.NewsEventModule
import ar.edu.isvdr.frontend.feature.news.data.NewsEventRepository
import ar.edu.isvdr.frontend.feature.news.model.NewsType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

/**
 * ViewModel de la pantalla de listado de noticias y eventos.
 * Combina filtro por tipo y filtro por rango de fecha, y los envía
 * al repositorio en cada carga (tarjeta 4).
 */
class NewsListViewModel @JvmOverloads constructor(
    private val repository: NewsEventRepository = NewsEventModule.repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUiState())
    val uiState: StateFlow<NewsListUiState> = _uiState.asStateFlow()

    init {
        cargarPublicaciones()
    }

    /** Cambia el filtro por tipo (NOTICIA / EVENTO / null = todas) y recarga. */
    fun setFiltroTipo(tipo: NewsType?) {
        _uiState.value = _uiState.value.copy(filtroTipo = tipo)
        cargarPublicaciones()
    }

    /** Cambia el rango de fecha rápido y recarga. */
    fun setFiltroFecha(rango: RangoFecha) {
        _uiState.value = _uiState.value.copy(filtroFecha = rango)
        cargarPublicaciones()
    }

    fun limpiarFiltros() {
        _uiState.value = _uiState.value.copy(filtroTipo = null, filtroFecha = RangoFecha.TODAS)
        cargarPublicaciones()
    }

    fun reintentar() = cargarPublicaciones()

    fun cargarPublicaciones() {
        val estadoActual = _uiState.value
        val (desde, hasta) = resolverRango(estadoActual.filtroFecha)

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPublicaciones(
                tipo = estadoActual.filtroTipo,
                fechaDesde = desde,
                fechaHasta = hasta
            )
                .onSuccess { lista ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        publicaciones = lista,
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "No se pudieron cargar las publicaciones"
                    )
                }
        }
    }

    /**
     * Resuelve un [RangoFecha] rápido a fechaDesde/fechaHasta ISO 8601 UTC,
     * el formato que exige el endpoint (ej. 2026-10-20T18:00:00.000Z).
     */
    private fun resolverRango(rango: RangoFecha): Pair<String?, String?> {
        if (rango == RangoFecha.TODAS) return null to null

        val hoyUtc = Instant.now().atZone(ZoneOffset.UTC)

        val desde = when (rango) {
            RangoFecha.HOY -> hoyUtc.toLocalDate().atStartOfDay(ZoneOffset.UTC)
            RangoFecha.ESTA_SEMANA -> hoyUtc.toLocalDate()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay(ZoneOffset.UTC)
            RangoFecha.ESTE_MES -> hoyUtc.toLocalDate()
                .withDayOfMonth(1)
                .atStartOfDay(ZoneOffset.UTC)
            RangoFecha.TODAS -> return null to null
        }

        val hasta = hoyUtc.toLocalDate()
            .plusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .minus(1, ChronoUnit.MILLIS)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return formatter.format(desde) to formatter.format(hasta)
    }
}