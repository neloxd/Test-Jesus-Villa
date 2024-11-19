package com.jesusvilla.test.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesusvilla.test.database.entities.users.TagEntity

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTags(tags: List<TagEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tag: TagEntity): Long

    @Delete
    fun deleteTag(tag: TagEntity)

    @Query("SELECT * FROM tag WHERE id_user_tag = :idUserTag")
    fun getTagsByUser(idUserTag: Long): List<TagEntity>

    @Query("SELECT * FROM tag WHERE id_tag = :tagSelected")
    fun getTagSelected(tagSelected: Long): TagEntity

    @Query("SELECT * FROM tag WHERE id_tag = :tagSelected")
    suspend fun getTagSelectedSuspend(tagSelected: Long): TagEntity

    @Query("SELECT * FROM tag WHERE id_tag = :tagId")
    suspend fun getTagData(tagId: Long): TagEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTags(tags: List<TagEntity>): List<Long>

    @Delete
    suspend fun deleteNewTag(tag: TagEntity)

    @Suppress("LongParameterList")
    @Query(
        "UPDATE TAG SET name = :name," +
                " prefix = :prefix, number = :number, balance = :balance, payment_type = :paymentType, " +
                "payment_method = :paymentMethod, payment_style = :paymentStyle, brand = :brand, " +
                "is_new_tag = :isNewTag, is_active = :isActive, is_uber = :isUber, has_debts = :hasDebts, " +
                "verification_digit = :verificationDigit, is_promotion_tdu = :isPromotionTDU, " +
                "is_paypal_enabled = :isPaypalEnabled, tdc_brand = :tdcBrand, tdc_number_ended = :tdcNumberEnded " +
                "WHERE id_tag = :idTag"
    )
    suspend fun updateTag(
        idTag: Long,
        name: String,
        prefix: String,
        number: Int,
        balance: Float,
        paymentType: String,
        paymentMethod: String,
        paymentStyle: String,
        brand: String,
        isNewTag: Boolean,
        isActive: Boolean,
        isUber: Boolean,
        hasDebts: Boolean,
        verificationDigit: Int,
        isPromotionTDU: Boolean,
        isPaypalEnabled: Boolean,
        tdcBrand: String,
        tdcNumberEnded: String
    )

    @Query("UPDATE TAG SET name = :tagName, car_selected_id = :carSelectedId WHERE id_tag = :idTag")
    suspend fun updateTagNameAndColor(
        idTag: Long,
        tagName: String,
        carSelectedId: Int
    )

    @Query("DELETE FROM TAG WHERE id_tag = :idTag")
    suspend fun deleteTagById(idTag: Long)

    @Query("UPDATE TAG SET car_selected_id = :carSelectedId WHERE id_tag = :idTag")
    suspend fun updateTagCarColor(idTag: Long, carSelectedId: Int): Int
}
