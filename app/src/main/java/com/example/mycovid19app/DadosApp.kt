package com.example.mycovid19app

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var PacienteSelecionado : Paciente? = null
        var localSelecionado : Local? = null

    }
}