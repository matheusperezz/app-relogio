package com.vanquish.despertador.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `AlarmNew` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timeString` TEXT NOT NULL, `daysOfWeekString` TEXT NOT NULL, `soundUriString` TEXT NOT NULL, `label` TEXT NOT NULL)")
        database.execSQL("DROP TABLE `Alarm`")
        database.execSQL("ALTER TABLE `AlarmNew` RENAME TO `Alarm`")
    }

}