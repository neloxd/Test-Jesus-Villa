package com.jesusvilla.test.database.entities.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_entity")
    var idEntity: Long = 0,
    @ColumnInfo(name = "id_user")
    var idUser: Long,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "paternal_name")
    val paternalSurname: String,
    @ColumnInfo(name = "maternal_name")
    val maternalSurname: String,
    @ColumnInfo(name = "active")
    val active: Boolean,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "verified")
    val verified: Boolean,
    @ColumnInfo(name = "disable_recharge")
    val disabledRecharge: Boolean,
    @ColumnInfo(name = "registration_date")
    val registrationDate: Long,
    @ColumnInfo(name = "active_token")
    var activeToken: Boolean = false,
    @ColumnInfo(name = "active_drive")
    val activeDrive: Boolean = false,
    @ColumnInfo(name = "phone_confirmed")
    var phoneConfirmed: Boolean = false,
    @ColumnInfo(name = "token_operation")
    var tokenOperation: String,
    @ColumnInfo(name = "can_pay")
    val canPay: Boolean,
    @ColumnInfo(name = "can_recharge")
    val canRecharge: Boolean,
    @ColumnInfo(name = "phone_ts")
    val phoneTs: String,
    @ColumnInfo(name = "tag_selected")
    val tagSelected: Long
) {
    fun equalizeTeamSize(teamSize: Array<Int>, k: Int): Int {
        val countByTeam = teamSize.groupingBy { it }.eachCount()

        val mostEquals = countByTeam.maxBy { it.value }.key

        var index = 0

        countByTeam.forEach {
            if(it.key != mostEquals) {
                index += it.value
            }
        }

        return index
    }
}