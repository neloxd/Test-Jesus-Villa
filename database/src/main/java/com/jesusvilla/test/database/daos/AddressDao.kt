package com.jesusvilla.test.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesusvilla.test.database.entities.users.AddressEntity

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(addressEntity: AddressEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewAddress(addressEntity: AddressEntity): Long

    @Suppress("LongParameterList")
    @Query(
        "UPDATE ADDRESS SET id_address = :idAddress, street = :street, external_number = :extNum, " +
            "internal_number = :insNum, zip_code = :zipCode, state = :state, town_hall = :townHall, " +
            "city = :city, colony = :colony"
    )
    suspend fun updateAddress(
        idAddress: Long,
        street: String,
        extNum: String,
        insNum: String,
        zipCode: String,
        state: String,
        townHall: String,
        city: String,
        colony: String,
    )

    @Query("SELECT * FROM ADDRESS WHERE id = 1")
    suspend fun getLastAddress(): AddressEntity
}
