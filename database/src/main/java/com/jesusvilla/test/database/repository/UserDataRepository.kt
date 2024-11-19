package com.jesusvilla.test.database.repository

import androidx.annotation.WorkerThread
import com.jesusvilla.test.database.daos.AddressDao
import com.jesusvilla.test.database.daos.TagDao
import com.jesusvilla.test.database.daos.UserDao
import com.jesusvilla.test.database.daos.transactions.TransactionsDao
import com.jesusvilla.test.database.di.qualifiers.AddressDaoQualifier
import com.jesusvilla.test.database.di.qualifiers.TagDaoQualifier
import com.jesusvilla.test.database.di.qualifiers.TransactionsDaoQualifier
import com.jesusvilla.test.database.di.qualifiers.UserDaoQualifier
import com.jesusvilla.test.database.entities.users.AddressEntity
import com.jesusvilla.test.database.entities.users.TagEntity
import com.jesusvilla.test.database.entities.users.UserDataComplete
import com.jesusvilla.test.database.entities.users.UserEntity
import com.jesusvilla.test.database.resources.DataBaseResource
import javax.inject.Inject

@Suppress("TooManyFunctions", "TooGenericExceptionCaught")
class UserDataRepository @Inject constructor(
    @UserDaoQualifier private val userDao: UserDao,
    @AddressDaoQualifier private val addressDao: AddressDao,
    @TagDaoQualifier private val tagDao: TagDao,
    @TransactionsDaoQualifier private val transactionsDao: TransactionsDao
) {

    @WorkerThread
    suspend fun getLastUserCoroutine(): DataBaseResource<UserDataComplete> {
        return try {
            val lastUser = userDao.getLastUser()
            DataBaseResource.Success(lastUser)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun getUserWithAddressAndTags(): DataBaseResource<UserDataComplete> {
        return try {
            val user = userDao.getLastUserSuspend()
            DataBaseResource.Success(user)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun getSelectedTag(): DataBaseResource<TagEntity> {
        return try {
            val user = userDao.getLastUserSuspend().user
            val tag = tagDao.getTagSelectedSuspend(user.tagSelected)
            DataBaseResource.Success(tag)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun getTagData(tagId: Long): DataBaseResource<TagEntity> {
        return try {
            val tag = tagDao.getTagData(tagId)
            DataBaseResource.Success(tag)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun updateTagNameAndColor(
        tagId: Long,
        tagName: String,
        colorCar: Int
    ): DataBaseResource<Boolean> {
        return try {
            tagDao.updateTagNameAndColor(
                tagId,
                tagName,
                colorCar
            )
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun deleteTag(
        tagId: Long
    ): DataBaseResource<Boolean> {
        return try {
            tagDao.deleteTagById(tagId)
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun addTag(tag: TagEntity): DataBaseResource<Long> {
        return try {
            val user = userDao.getLastUser().user
            tag.idUserEntity = user.idEntity
            val idTagEntity = tagDao.insertTag(tag)
            DataBaseResource.Success(idTagEntity)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun insertUserWithAddressAndTags(
        user: UserEntity,
        address: AddressEntity,
        tags: List<TagEntity>
    ) {
        val userId = userDao.insertNewUser(user)
        address.idUserEntity = userId
        addressDao.insertNewAddress(address)
        tags.forEach { it.idUserEntity = userId }
        tagDao.insertNewTags(tags)
    }

    @WorkerThread
    suspend fun updateUserInfo(
        idUser: Long,
        name: String,
        paternalName: String,
        maternalName: String
    ): DataBaseResource<Boolean> {
        return try {
            userDao.updateUserInfo(
                idUser,
                name,
                paternalName,
                maternalName
            )
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun updateSelectedTag(
        userId: Long,
        tagId: Long
    ): DataBaseResource<Boolean> {
        return try {
            userDao.updateSelectedTag(
                tagId,
                userId
            )
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun updateSelectedAddress(
        address: AddressEntity,
    ): DataBaseResource<Boolean> {
        return try {
            addressDao.updateAddress(
                address.idAddress,
                address.street,
                address.externalNumber,
                address.internalNumber,
                address.zipCode,
                address.state,
                address.townHall,
                address.city,
                address.colony
            )
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun getLastSelectedAddress(): DataBaseResource<AddressEntity> {
        return try {
            val address = addressDao.getLastAddress()
            DataBaseResource.Success(address)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun deleteUserData(): DataBaseResource<Boolean> {
        return try {
            userDao.deleteUserData()
            transactionsDao.deleteAllTransactionData()
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun getTokenOperation(): DataBaseResource<String> {
        return try {
            val userComplete = userDao.getLastUser()
            DataBaseResource.Success(userComplete.user.tokenOperation)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    suspend fun saveTokenOperation(
        tokenOperation: String,
        phone: String
    ): DataBaseResource<Boolean> {
        return try {
            userDao.getLastUserId()?.let {
                userDao.updateUserDetails(
                    userId = it,
                    tokenOperation = tokenOperation,
                    phone = phone
                )
                DataBaseResource.Success(true)
            }
            DataBaseResource.Success(false)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun updateTag(tag: TagEntity): DataBaseResource<Boolean> {
        return try {
            tagDao.updateTag(
                tag.idTag, tag.name,
                prefix = tag.prefix,
                number = tag.number,
                balance = tag.balance,
                paymentType = tag.paymentType,
                paymentMethod = tag.paymentMethod,
                paymentStyle = tag.paymentStyle,
                brand = tag.brand,
                isNewTag = tag.isNewTag,
                isActive = tag.isActive,
                isUber = tag.isUber,
                hasDebts = tag.hasDebts,
                verificationDigit = tag.verificationDigit,
                isPromotionTDU = tag.isPromotionTDU,
                isPaypalEnabled = tag.isPaypalEnabled,
                tdcBrand = tag.tdcBrand,
                tdcNumberEnded = tag.tdcNumberEnded,
            )
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    suspend fun changeCarColor(tagId: Long, carSelectedId: Int): DataBaseResource<Boolean> {
        return try {
            tagDao.updateTagCarColor(tagId, carSelectedId)
            DataBaseResource.Success(true)
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }
}
