package cstjean.mobile.ecole
import android.content.Context
import androidx.room.Room
import cstjean.mobile.ecole.database.MIGRATION_1_2
import cstjean.mobile.ecole.database.TravailDatabase
import cstjean.mobile.ecole.travail.Travail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "travail-database"
class TravailRepository private constructor(context: Context,
                                            private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val database: TravailDatabase = Room
        .databaseBuilder(context.applicationContext, TravailDatabase::class.java,DATABASE_NAME)
        .createFromAsset(DATABASE_NAME)
        .addMigrations(MIGRATION_1_2)
        .build()

    fun getTravaux(): Flow<List<Travail>> = database.travailDao().getTravaux()
    suspend fun getTravail(id: UUID): Travail = database.travailDao().getTravail(id)

    suspend fun addTravail(travail: Travail) {
        database.travailDao().addTravail(travail)
    }

        companion object {
        private var INSTANCE: TravailRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TravailRepository(context)
            }
        }
        fun get(): TravailRepository {
            return INSTANCE ?:
            throw IllegalStateException("TravailRepository doit être initialisé.")
        }
    }
    fun updateTravail(travail: Travail) {
        coroutineScope.launch {
            database.travailDao().updateTravail(travail)
        }
    }
}