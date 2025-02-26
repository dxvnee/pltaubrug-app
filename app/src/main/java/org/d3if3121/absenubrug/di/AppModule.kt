package org.d3if3121.absenubrug.di

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if3121.absenubrug.data.repository.MahasiswaListRepository
import org.d3if3121.absenubrug.data.repository.interfaces.MahasiswaListInterface
import javax.inject.Singleton

const val MAHASISWA = "pegawai"
const val PROJECT = "reqpost"
const val ABSEN = "absen"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMahasiswaListRepository(): MahasiswaListInterface = MahasiswaListRepository(
        mahasiswaRef = Firebase.firestore.collection(MAHASISWA),
        absenRef = Firebase.firestore.collection(ABSEN),
    )

}
