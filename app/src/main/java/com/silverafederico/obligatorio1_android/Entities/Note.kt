package com.silverafederico.obligatorio1_android.Entities

import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class Note(
    var id: String,
    var title: String,
    var description: String,
    var date: String,
    val isList: Boolean,
    var listItems: MutableList<String> = mutableListOf()
)