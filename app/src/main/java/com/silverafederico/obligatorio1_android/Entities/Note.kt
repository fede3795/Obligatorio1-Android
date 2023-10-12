package com.silverafederico.obligatorio1_android.Entities

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Note(
    var title: String,
    var description: String,
    var date: String
)