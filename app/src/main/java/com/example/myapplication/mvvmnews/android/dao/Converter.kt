package com.example.myapplication.mvvmnews.android.dao

import androidx.room.TypeConverter
import com.example.myapplication.mvvmnews.android.model.SourceViewData


class Converter {

    @TypeConverter
    fun fromSource(source: SourceViewData): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): SourceViewData {
        return SourceViewData(name,name)
    }
}