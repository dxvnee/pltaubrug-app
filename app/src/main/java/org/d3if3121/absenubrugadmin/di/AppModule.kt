package org.d3if3121.absenubrugadmin.di

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if3121.absenubrugadmin.data.datastore.UserPreferences
import org.d3if3121.absenubrugadmin.data.repository.MahasiswaListRepository
import org.d3if3121.absenubrugadmin.data.repository.interfaces.MahasiswaListInterface
import javax.inject.Singleton

const val MAHASISWA = "pegawai"
const val ABSEN = "absen"
const val DATA = "sheet"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMahasiswaListRepository(): MahasiswaListInterface = MahasiswaListRepository(
        mahasiswaRef = Firebase.firestore.collection(MAHASISWA),
        absenRef = Firebase.firestore.collection(ABSEN),
        dataRef = Firebase.firestore.collection(DATA),
    )

}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}
