package com.jesusvilla.test.database.entities.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "address",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id_entity"],
            childColumns = ["id_user_address"],
            onDelete = CASCADE
        )
    ]
)

data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo("id_user_address")
    var idUserEntity: Long,
    @ColumnInfo("id_address")
    var idAddress: Long,
    @ColumnInfo("street")
    var street: String,
    @ColumnInfo("external_number")
    var externalNumber: String,
    @ColumnInfo("internal_number")
    var internalNumber: String,
    @ColumnInfo("zip_code")
    var zipCode: String,
    @ColumnInfo("state")
    var state: String,
    @ColumnInfo("town_hall")
    var townHall: String,
    @ColumnInfo("city")
    var city: String,
    @ColumnInfo("colony")
    var colony: String
)
