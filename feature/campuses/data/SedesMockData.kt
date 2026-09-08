package ar.edu.isvdr.frontend.feature.campuses.data

val sedesMock = listOf(
    Sede(
        id = "1",
        nombre = "Sede Central",
        ciudad = "Córdoba",
        provincia = "Córdoba",
        direccion = "Av. Colón 123",
        telefono = "351-4000000",
        horarios = "8 a 20 hs",
        carrerasIds = listOf("c1", "c2")
    ),
    Sede(
        id = "2",
        nombre = "Sede Norte",
        ciudad = "Villa María",
        provincia = "Córdoba",
        direccion = "San Martín 456",
        telefono = "353-4111111",
        horarios = "9 a 18 hs",
        carrerasIds = listOf("c1")
    ),
    Sede(
        id = "3",
        nombre = "Sede Rosario",
        ciudad = "Rosario",
        provincia = "Santa Fe",
        direccion = "Bv. Oroño 789",
        telefono = "341-4222222",
        horarios = "8 a 19 hs",
        carrerasIds = listOf("c2", "c3")
    )
)