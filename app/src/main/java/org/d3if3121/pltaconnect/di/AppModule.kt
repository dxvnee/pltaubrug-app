package org.d3if3121.pltaconnect.di

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if3121.pltaconnect.data.repository.PegawaiListRepository
import org.d3if3121.pltaconnect.data.repository.ProjectListRepository
import org.d3if3121.pltaconnect.data.repository.interfaces.PegawaiListInterface
import org.d3if3121.pltaconnect.data.repository.interfaces.ProjectListInterface
import javax.inject.Singleton

const val PEGAWAI = "pegawai"
const val PROJECT = "reqpost"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMahasiswaListRepository(): PegawaiListInterface = PegawaiListRepository(
        pegawaiRef = Firebase.firestore.collection(PEGAWAI)
    )

    @Provides
    @Singleton
    fun provideProjectListRepository(
        @ApplicationContext context: Context
    ): ProjectListInterface = ProjectListRepository(
        pegawaiRef = Firebase.firestore.collection(PEGAWAI),
        projectRef = Firebase.firestore.collection(PROJECT),
        context = context
    )


}
