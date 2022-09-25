package cstjean.mobile.ecole.database
import androidx.room.Database
import androidx.room.RoomDatabase
import cstjean.mobile.ecole.travail.Travail
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [ Travail::class ], version=2, exportSchema = false)
@TypeConverters(TravailTypeConverters::class)
abstract class TravailDatabase : RoomDatabase() {
    abstract fun travailDao(): TravailDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE travail ADD COLUMN coequipier TEXT NOT NULL DEFAULT ''"
        )
    }
}

