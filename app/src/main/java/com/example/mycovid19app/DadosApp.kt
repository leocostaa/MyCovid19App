package com.example.mycovid19app

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        var PacienteSelecionado : Paciente? = null
        var FragmentPacientes: FragmentPacientes? = null
        var FragmentNovoPaciente: FragmentNovoPaciente? = null
        lateinit var FragmentInicioPage: FragmentInicioPage

    }
}