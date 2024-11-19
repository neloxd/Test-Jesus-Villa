package com.jesusvilla.test.database.entities.users

import androidx.room.Embedded
import androidx.room.Relation

class UserDataComplete(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id_entity",
        entityColumn = "id_user_address"
    )
    val address: AddressEntity,
    @Relation(
        parentColumn = "id_entity",
        entityColumn = "id_user_tag"
    )
    var tags: List<TagEntity> = emptyList(),
)
