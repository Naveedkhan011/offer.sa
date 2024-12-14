package utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import models.AllDropDownValues
import models.DataXXX
import network.Ktor

class DropDownManager : ScreenModel {

    private var allDropDownValues by mutableStateOf(AllDropDownValues())

    fun getData(id: Int): DataXXX {
        allDropDownValues.data?.let { it ->
            it.forEach {
                if (it.id == id) return it
            }
        }
        return DataXXX()
    }


    fun getDropDownValues() {
        screenModelScope.launch {
            try {
                val response =
                    Ktor.client
                        .get("/portal-api/insurance/rest/showInsuranceCodeName")
                        .body<AllDropDownValues>()
                allDropDownValues = response
            } catch (_: Exception) { }
        }
    }

}