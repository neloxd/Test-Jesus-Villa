package com.jesusvilla.test.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jesusvilla.test.database.entities.users.UserDataComplete
import com.jesusvilla.test.database.entities.users.UserEntity

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM USERS ORDER BY id_user LIMIT 1")
    suspend fun getLastUserSuspend(): UserDataComplete

    @Transaction
    @Query("SELECT * FROM USERS ORDER BY id_user LIMIT 1")
    fun getLastUser(): UserDataComplete

    @Query("SELECT id_user FROM USERS ORDER BY id_user LIMIT 1")
    fun getLastUserId(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUser(user: UserEntity): Long

    @Query("DELETE FROM users")
    suspend fun deleteUserData()

    @Query(
        "UPDATE USERS SET token_operation = :tokenOperation, phone = :phone," +
            " active_token = :activeToken, phone_confirmed = :phoneConfirmed WHERE id_user = :userId"
    )
    fun updateUserDetails(
        userId: Long,
        tokenOperation: String,
        phone: String,
        activeToken: Boolean = true,
        phoneConfirmed: Boolean = true
    ): Int

    @Query("UPDATE USERS SET phone = :phone WHERE id_user =:userId")
    suspend fun updatePhone(userId: Long, phone: String): Int

    @Transaction
    @Query(
        "UPDATE USERS SET name = :newName, paternal_name = :newPaternalName, maternal_name = :newMaternalName " +
            "WHERE id_user = :userId"
    )
    suspend fun updateUserInfo(
        userId: Long,
        newName: String,
        newPaternalName: String,
        newMaternalName: String
    ): Int

    @Transaction
    @Query("UPDATE USERS SET tag_selected = :tagId  WHERE id_user = :userId")
    suspend fun updateSelectedTag(
        tagId: Long,
        userId: Long
    ): Int
}
