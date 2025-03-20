package org.d3if3121.pltaconnect.data.repository.interfaces

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import org.d3if3121.pltaconnect.data.model.Pegawai
import org.d3if3121.pltaconnect.data.model.PegawaiEdit
import org.d3if3121.pltaconnect.data.model.Response
import org.d3if3121.pltaconnect.data.model.Sheet
import org.d3if3121.pltaconnect.data.model.data.Debit
import org.d3if3121.pltaconnect.data.model.data.Masuk
import org.d3if3121.pltaconnect.data.model.data.MasukRequest
import org.d3if3121.pltaconnect.data.model.data.Pemakaian
import org.d3if3121.pltaconnect.data.model.data.PemakaianRequest
import org.d3if3121.pltaconnect.data.model.data.Produksi
import org.d3if3121.pltaconnect.data.model.data.ProduksiRequest


typealias PegawaiListResponse = Response<List<Pegawai>>
typealias AddPegawaiResponse = Response<String>
typealias GetPegawaiResponse = Response<Pegawai>
typealias UpdatePegawaiResponse = Response<String>
typealias DeletePegawaiResponse = Response<Void>

typealias LoginResponse = Response<Pegawai>
//
typealias AddDebitResponse = Response<String>
typealias AddProduksiResponse = Response<String>
typealias AddPemakaianResponse = Response<String>
typealias AddMasukResponse = Response<String>
//
typealias GetDebitResponse = Response<Debit>
typealias GetProduksiResponse = Response<ProduksiRequest>
typealias GetPemakaianResponse = Response<PemakaianRequest>
typealias GetMasukResponse = Response<MasukRequest>

typealias SheetResponse = Response<Sheet>
typealias AddFotoProfil = Response<String>


interface PegawaiListInterface {
    fun getPegawaiList(): Flow<PegawaiListResponse>

    suspend fun addPegawai(pegawai: Pegawai): AddPegawaiResponse
    suspend fun updatePegawai(pegawai: PegawaiEdit): UpdatePegawaiResponse

    suspend fun loginPegawai(nim: String, password: String): LoginResponse
    //
    suspend fun addDebit(debit: Debit): AddDebitResponse
    suspend fun addProduksi(produksi: ProduksiRequest): AddProduksiResponse
    suspend fun addPemakaian(pemakaian: PemakaianRequest): AddPemakaianResponse
    suspend fun addMasuk(masuk: MasukRequest): AddMasukResponse
    //
    suspend fun getDebit(T: String): GetDebitResponse
    suspend fun getProduksi(T: String): GetProduksiResponse
    suspend fun getPemakaian(T: String): GetPemakaianResponse
    suspend fun getMasuk(T: String): GetMasukResponse
    //
    suspend fun getSheet(id: String): SheetResponse
    suspend fun addFotoProfil(nip: String, uri: Uri): AddFotoProfil


    fun getMahasiswa(nip: String): Flow<GetPegawaiResponse>
}