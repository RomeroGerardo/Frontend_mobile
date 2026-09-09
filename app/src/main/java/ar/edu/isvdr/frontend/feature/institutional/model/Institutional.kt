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

data class ValorDetalle(
    val titulo: String,
    val descripcion: String
)

data class HitoHistorico(
    val periodo: String,
    val titulo: String,
    val descripcion: String
)

data class ActividadVidaInstitucional(
    val id: String,
    val titulo: String,
    val categoria: String,
    val descripcion: String,
    val estado: String
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
        ),
        FechaImportante(
            id = "f5",
            titulo = "Mesas de Exámenes Turno Febrero / Marzo",
            fecha = "17 Feb - 07 Mar",
            descripcion = "Mesas ordinarias de exámenes finales previas al inicio de cursado.",
            tipo = "Exámenes"
        ),
        FechaImportante(
            id = "f6",
            titulo = "Inicio de Clases - 1° Cuatrimestre 2026",
            fecha = "16 de Marzo",
            descripcion = "Inicio formal del dictado de clases en todas las tecnicaturas y sedes.",
            tipo = "Académico"
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

    val valoresDetallados = listOf(
        ValorDetalle(
            titulo = "Excelencia académica",
            descripcion = "Planes de estudio actualizados, docentes con experiencia técnica y formación práctica orientada a la resolución de problemas reales."
        ),
        ValorDetalle(
            titulo = "Compromiso social y regional",
            descripcion = "Articulación permanente con organizaciones, municipios y el entramado productivo de Villa del Rosario y zonas aledañas."
        ),
        ValorDetalle(
            titulo = "Innovación constante",
            descripcion = "Incorporación continua de tecnologías digitales, metodologías activas y herramientas de vanguardia en cada tecnicatura."
        ),
        ValorDetalle(
            titulo = "Inclusión e igualdad",
            descripcion = "Acceso a educación superior pública, gratuita y de calidad sin distinciones, fomentando el desarrollo de talentos locales."
        ),
        ValorDetalle(
            titulo = "Ética profesional",
            descripcion = "Fomento de la responsabilidad, honestidad y rectitud tanto en la vida académica como en el futuro desempeño laboral."
        )
    )

    val hitosHistoricos = listOf(
        HitoHistorico(
            periodo = "Fundación",
            titulo = "Nacimiento de la Educación Superior en Villa del Rosario",
            descripcion = "Se crea el Instituto para dar respuesta a la creciente demanda de formación técnica de jóvenes y trabajadores de la región que debían trasladarse a otras ciudades para continuar sus estudios."
        ),
        HitoHistorico(
            periodo = "Crecimiento",
            titulo = "Consolidación de las Primeras Tecnicaturas",
            descripcion = "Se abren carreras pioneras respondiendo a la matriz productiva local, egresando las primeras camadas de técnicos que hoy lideran proyectos en la región."
        ),
        HitoHistorico(
            periodo = "Modernización",
            titulo = "Transformación Digital y Laboratorios",
            descripcion = "Incorporación de equipamiento informático, entornos de aprendizaje modernos y convenios de pasantías con empresas e instituciones públicas."
        ),
        HitoHistorico(
            periodo = "Actualidad",
            titulo = "Referente Técnico Regional",
            descripcion = "El ISVDR cuenta con múltiples sedes y carreras en constante actualización, consolidándose como un pilar fundamental del desarrollo educativo de Córdoba."
        )
    )

    val actividadesVidaInstitucional = listOf(
        ActividadVidaInstitucional(
            id = "v1",
            titulo = "Prácticas Profesionalizantes y Pasantías",
            categoria = "Pasantías",
            descripcion = "Convenios con empresas e instituciones de Villa del Rosario y la región para que los estudiantes de los últimos años apliquen sus conocimientos en situaciones reales de trabajo.",
            estado = "Convenios activos"
        ),
        ActividadVidaInstitucional(
            id = "v2",
            titulo = "Talleres de Habilidades Técnicas y Blandas",
            categoria = "Talleres",
            descripcion = "Ciclos de capacitación complementaria en nuevas tecnologías, metodologías ágiles, oratoria y preparación para entrevistas laborales.",
            estado = "Inscripciones periódicas"
        ),
        ActividadVidaInstitucional(
            id = "v3",
            titulo = "Programa de Tutorías entre Pares",
            categoria = "Tutorías",
            descripcion = "Espacio de orientación y acompañamiento académico donde estudiantes avanzados brindan apoyo a ingresantes en materias troncales.",
            estado = "Todo el ciclo lectivo"
        ),
        ActividadVidaInstitucional(
            id = "v4",
            titulo = "Proyectos de Extensión y Vinculación Comunitaria",
            categoria = "Extensión",
            descripcion = "Iniciativas en las que docentes y estudiantes desarrollan proyectos tecnológicos, relevamientos y capacitaciones abiertas a la comunidad.",
            estado = "Convocatoria abierta"
        ),
        ActividadVidaInstitucional(
            id = "v5",
            titulo = "Biblioteca Física y Repositorio Digital",
            categoria = "Biblioteca",
            descripcion = "Acceso a bibliografía técnica, libros de consulta, salas de estudio grupal y recursos digitales disponibles para toda la comunidad del ISVDR.",
            estado = "Abierto de 18 a 22 hs"
        )
    )
}
