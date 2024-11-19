package com.jesusvilla.test.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesusvilla.test.database.daos.AddressDao
import com.jesusvilla.test.database.daos.PostDao
import com.jesusvilla.test.database.daos.TagDao
import com.jesusvilla.test.database.daos.UserDao
import com.jesusvilla.test.database.daos.transactions.InvoicesDao
import com.jesusvilla.test.database.daos.transactions.TransactionsDao
import com.jesusvilla.test.database.entities.test.PostEntity
import com.jesusvilla.test.database.entities.transactions.InvoiceEntity
import com.jesusvilla.test.database.entities.transactions.TransactionEntity
import com.jesusvilla.test.database.entities.users.AddressEntity
import com.jesusvilla.test.database.entities.users.TagEntity
import com.jesusvilla.test.database.entities.users.UserEntity

@Database(
    entities = [
        PostEntity::class
    ],
    version = 2, exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
