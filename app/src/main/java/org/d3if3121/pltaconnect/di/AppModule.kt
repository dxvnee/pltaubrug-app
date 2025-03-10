package org.d3if3121.pltaconnect.di

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if3121.pltaconnect.data.datastore.UserPreferences
import org.d3if3121.pltaconnect.data.repository.PegawaiListRepository
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListInterface
import javax.inject.Singleton

const val PEGAWAI = "pegawai"
const val DEBIT = "debit"
const val MASUK = "masuk"
const val PEMAKAIAN = "pemakaian"
const val PRODUKSI = "produksi"
const val SHEET = "sheet"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMahasiswaListRepository(): PegawaiListInterface = PegawaiListRepository(
        pegawaiRef = Firebase.firestore.collection(PEGAWAI),
        debitRef = Firebase.firestore.collection(DEBIT),
        masukRef = Firebase.firestore.collection(MASUK),
        pemakaianRef = Firebase.firestore.collection(PEMAKAIAN),
        produksiRef = Firebase.firestore.collection(PRODUKSI),
        sheetRef = Firebase.firestore.collection(SHEET),
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

