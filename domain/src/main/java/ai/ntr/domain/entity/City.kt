package ai.ntr.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val name: String,
    val time: Int
)
