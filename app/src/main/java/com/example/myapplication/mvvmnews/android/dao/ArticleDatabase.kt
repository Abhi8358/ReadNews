package com.example.myapplication.mvvmnews.android.dao

import android.content.Context
import androidx.room.*
import com.example.myapplication.mvvmnews.android.model.ArticleViewData

@TypeConverters(Converter::class)
@Database(
    entities = [ArticleViewData::class],
    version = 1
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAccessObject
    companion object {

        @Volatile
        private var instant: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instant?: synchronized(LOCK) {
            instant ?: createDatabase(context).also { instant = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db_db"
            ).build()
    }
}