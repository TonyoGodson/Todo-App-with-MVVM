package com.example.newmvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newmvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("wash the dishes", important = true, true))
                dao.insert(Task("do the laundry", important = false, false))
                dao.insert(Task("buy groceries", important = false, completed = true))
                dao.insert(Task("prepare food", important = false, completed = true))
                dao.insert(Task("call mom", important = false, false))
                dao.insert(Task("repair my bike", false, false))
                dao.insert(Task("call Elon Musk", false, false))
            }
        }
    }
}
