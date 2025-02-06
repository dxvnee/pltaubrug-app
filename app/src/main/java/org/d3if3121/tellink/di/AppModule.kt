package org.d3if3121.tellink.di

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if3121.tellink.data.repository.MahasiswaListRepository
import org.d3if3121.tellink.data.repository.ProjectListRepository
import org.d3if3121.tellink.data.repository.interfaces.MahasiswaListInterface
import org.d3if3121.tellink.data.repository.interfaces.ProjectListInterface
import javax.inject.Inject
import javax.inject.Singleton

const val MAHASISWA = "mahasiswa"
const val PROJECT = "reqpost"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMahasiswaListRepository(): MahasiswaListInterface = MahasiswaListRepository(
        mahasiswaRef = Firebase.firestore.collection(MAHASISWA)
    )

    @Provides
    @Singleton
    fun provideProjectListRepository(
        @ApplicationContext context: Context
    ): ProjectListInterface = ProjectListRepository(
        mahasiswaRef = Firebase.firestore.collection(MAHASISWA),
        projectRef = Firebase.firestore.collection(PROJECT),
        context = context
    )


}
