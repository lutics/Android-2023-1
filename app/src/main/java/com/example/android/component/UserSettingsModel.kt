package com.example.android.component

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.datastore.DataStoreStorage

object UserSettingsModel : SettingsModel(DataStoreStorage(name = "settings")) {
    val favorites by stringSetPref(setOf())
}
