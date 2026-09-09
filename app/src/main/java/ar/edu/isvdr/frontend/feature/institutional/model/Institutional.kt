package ar.edu.isvdr.frontend.feature.institutional.model

/**
 * Modelos de datos del módulo Institucional (I5 - Gabriel).
 */

data class InstitutionalInfo(
    val nombre: String,
    val sigla: String,
    val lema: String,
    val descripcion: String,
    val resena: String
)

data class MisionVision(
    val mision: String,
    val vision: String,
    val valores: List<String>
)

data class FechaImportante(
    val id: String,
    val titulo: String,
    val fecha: String,
    val descripcion: String,
    val tipo: String
)

data class ContactoInfo(
    val sede: String,
    val direccion: String,
    val localidad: String,
    val provincia: String,
    val telefono: String,
    val whatsapp: String,
    val email: String,
    val horarioAtencion: String,
    val sitioWeb: String
)

data class SeccionInstitucional(
    val id: String,
    val titulo: String,
    val subtitulo: String,
    val ruta: String
)

object InstitutionalMockData {

    val infoGeneral = InstitutionalInfo(
        nombre = "Instituto Superior Villa del Rosario",
        sigla = "ISVDR",
        lema = "Formando profesionales para el desarrollo regional",
        descripcion = "Institución pública de educación superior técnica orientada a brindar oportunidades formativas accesibles, inclusivas y de alta calidad para la comunidad de Villa del Rosario y la región.",
        resena = "Desde su creación, el ISVDR acompaña el crecimiento productivo y social de nuestra comunidad, formando egresados preparados para responder a las demandas del mercado laboral con sólida base técnica y compromiso ético."
    )

    val misionVision = MisionVision(
        mision = "Formar profesionales técnicos comprometidos con la innovación, la ética y el desarrollo socio-productivo de la región, brindando una educación pública y de excelencia.",
        vision = "Consolidarnos como un instituto referente de educación superior técnica en la provincia de Córdoba por la calidad formativa y la rápida inserción laboral de nuestros graduados.",
        valores = listOf(
            "Excelencia académica",
            "Compromiso social",
            "Innovación constante",
            "Inclusión e igualdad de oportunidades",
            "Ética profesional"
        )
    )

    val fechasImportantes = listOf(
        FechaImportante(
            id = "f1",
            titulo = "Preinscripciones Ciclo 2026",
            fecha = "01 Nov - 15 Dic",
            descripcion = "Apertura de preinscripciones online a todas las carreras técnicas.",
            tipo = "Inscripción"
        ),
        FechaImportante(
            id = "f2",
            titulo = "Turno Ordinario de Exámenes",
            fecha = "24 Nov - 05 Dic",
            descripcion = "Mesas de exámenes finales correspondientes al turno de noviembre/diciembre.",
            tipo = "Exámenes"
        ),
        FechaImportante(
            id = "f3",
            titulo = "Cierre de Ciclo Lectivo",
            fecha = "19 de Diciembre",
            descripcion = "Finalización formal del ciclo lectivo y actividades administrativas.",
            tipo = "Académico"
        ),
        FechaImportante(
            id = "f4",
            titulo = "Curso de Nivelación e Introducción",
            fecha = "15 Feb - 06 Mar",
            descripcion = "Módulos introductorios y de ambientación para ingresantes.",
            tipo = "Ingreso"
        )
    )

    val contacto = ContactoInfo(
        sede = "Sede Central Villa del Rosario",
        direccion = "Av. Hipólito Yrigoyen 450",
        localidad = "Villa del Rosario",
        provincia = "Córdoba",
        telefono = "03573 - 424100",
        whatsapp = "+54 9 3573 55-1234",
        email = "instituto.isvdr@gmail.com",
        horarioAtencion = "Lunes a Viernes de 18:00 a 22:30 hs",
        sitioWeb = "https://isvdr-cba.infd.edu.ar"
    )

    val secciones = listOf(
        SeccionInstitucional(
            id = "mision_vision",
            titulo = "Misión, Visión e Historia",
            subtitulo = "Conocé nuestra identidad, valores y trayectoria formativa",
            ruta = "institutional_mision_vision"
        ),
        SeccionInstitucional(
            id = "vida_institucional",
            titulo = "Vida Institucional",
            subtitulo = "Proyectos, talleres, pasantías y comunidad estudiantil",
            ruta = "institutional_vida"
        ),
        SeccionInstitucional(
            id = "fechas_importantes",
            titulo = "Fechas Importantes",
            subtitulo = "Calendario académico, inscripciones y turnos de exámenes",
            ruta = "institutional_fechas"
        ),
        SeccionInstitucional(
            id = "contacto",
            titulo = "Contacto y Ubicación",
            subtitulo = "Canales de atención, teléfonos, correo y redes oficiales",
            ruta = "institutional_contacto"
        )
    )
}
