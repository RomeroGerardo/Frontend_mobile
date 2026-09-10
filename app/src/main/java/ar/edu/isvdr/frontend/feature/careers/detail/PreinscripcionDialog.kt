package ar.edu.isvdr.frontend.feature.careers.detail

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ar.edu.isvdr.frontend.core.components.IsvdrPrimaryButton
import ar.edu.isvdr.frontend.core.components.IsvdrSecondaryButton
import ar.edu.isvdr.frontend.feature.careers.model.PreinscripcionRequest
import java.util.Calendar
import java.util.Locale

@Composable
fun PreinscripcionDialog(
    careerId: String,
    careerName: String,
    isLoading: Boolean,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onSubmit: (PreinscripcionRequest) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var documento by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("Argentina") }
    var direccion by remember { mutableStateOf("") }
    var localidad by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("Córdoba") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var validationError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val defaultYear = calendar.get(Calendar.YEAR) - 18
    val defaultMonth = calendar.get(Calendar.MONTH)
    val defaultDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fechaNacimiento = String.format(Locale.ROOT, "%04d-%02d-%02d", year, month + 1, dayOfMonth)
                validationError = null
            },
            defaultYear,
            defaultMonth,
            defaultDay
        )
    }

    Dialog(onDismissRequest = { if (!isLoading) onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Formulario de Preinscripción",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = careerName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it; validationError = null },
                        label = { Text("Nombre *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it; validationError = null },
                        label = { Text("Apellido *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = documento,
                        onValueChange = { documento = it; validationError = null },
                        label = { Text("Documento / DNI *") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = if (fechaNacimiento.isNotEmpty()) {
                                val parts = fechaNacimiento.split("-")
                                if (parts.size == 3) "${parts[2]}/${parts[1]}/${parts[0]}" else fechaNacimiento
                            } else "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Fecha de Nacimiento *") },
                            placeholder = { Text("Toca para elegir fecha en calendario") },
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = "Abrir calendario"
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        // Cobertura invisible para que tocar cualquier parte del campo abra el calendario
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { datePickerDialog.show() }
                        )
                    }
                    OutlinedTextField(
                        value = nacionalidad,
                        onValueChange = { nacionalidad = it; validationError = null },
                        label = { Text("Nacionalidad *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it; validationError = null },
                        label = { Text("Dirección *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = localidad,
                        onValueChange = { localidad = it; validationError = null },
                        label = { Text("Localidad *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = provincia,
                        onValueChange = { provincia = it; validationError = null },
                        label = { Text("Provincia *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it; validationError = null },
                        label = { Text("Teléfono *") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; validationError = null },
                        label = { Text("Correo Electrónico *") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )

                    val activeError = validationError ?: errorMessage
                    if (activeError != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = activeError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            IsvdrSecondaryButton(
                                text = "Cancelar",
                                onClick = onDismiss
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            IsvdrPrimaryButton(
                                text = "Confirmar",
                                onClick = {
                                    if (nombre.isBlank() || apellido.isBlank() || documento.isBlank() ||
                                        fechaNacimiento.isBlank() || direccion.isBlank() || localidad.isBlank() ||
                                        telefono.isBlank() || email.isBlank()
                                    ) {
                                        validationError = "Por favor completa todos los campos obligatorios."
                                        return@IsvdrPrimaryButton
                                    }
                                    if (documento.trim().length < 6) {
                                        validationError = "El documento debe tener al menos 6 caracteres."
                                        return@IsvdrPrimaryButton
                                    }
                                    if (telefono.trim().length < 7) {
                                        validationError = "El teléfono debe tener al menos 7 dígitos."
                                        return@IsvdrPrimaryButton
                                    }
                                    if (!email.contains("@")) {
                                        validationError = "Ingresa un correo electrónico válido."
                                        return@IsvdrPrimaryButton
                                    }

                                    // Normalizar fecha de nacimiento (acepta DD/MM/AAAA o AAAA-MM-DD)
                                    var formattedDate = fechaNacimiento.trim()
                                    val ddmmyyyy = Regex("""^(\d{1,2})[/.-](\d{1,2})[/.-](\d{4})$""")
                                    val match = ddmmyyyy.matchEntire(formattedDate)
                                    if (match != null) {
                                        val day = match.groupValues[1].padStart(2, '0')
                                        val month = match.groupValues[2].padStart(2, '0')
                                        val year = match.groupValues[3]
                                        formattedDate = "$year-$month-$day"
                                    }

                                    val yyyymmdd = Regex("""^\d{4}-\d{2}-\d{2}$""")
                                    if (!yyyymmdd.matches(formattedDate)) {
                                        validationError = "La fecha debe tener formato AAAA-MM-DD o DD/MM/AAAA."
                                        return@IsvdrPrimaryButton
                                    }

                                    if (careerId.length < 10) {
                                        validationError = "Esta carrera es de demostración local y no existe en la base de datos real."
                                        return@IsvdrPrimaryButton
                                    }

                                    onSubmit(
                                        PreinscripcionRequest(
                                            nombre = nombre.trim(),
                                            apellido = apellido.trim(),
                                            documento = documento.trim(),
                                            fechaNacimiento = formattedDate,
                                            nacionalidad = nacionalidad.trim(),
                                            direccion = direccion.trim(),
                                            localidad = localidad.trim(),
                                            provincia = provincia.trim(),
                                            telefono = telefono.trim(),
                                            email = email.trim(),
                                            carreraId = careerId
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
