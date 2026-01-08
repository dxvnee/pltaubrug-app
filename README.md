# 📱 PLTA Ubrug App Collection

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)

*Kumpulan aplikasi Android untuk pengelolaan proyek, absensi, dan data PLTA*

</div>

---

## 📋 Daftar Isi

- [📱 PLTA Ubrug App Collection](#-plta-ubrug-app-collection)
  - [📋 Daftar Isi](#-daftar-isi)
  - [📖 Tentang Repository](#-tentang-repository)
    - [1. 📋 AbsenUbrug (Branch: `egia`)](#1--absenubrug-branch-egia)
    - [2. 👨‍💼 AbsenUbrug Admin (Branch: `AdminApp`)](#2--absenubrug-admin-branch-adminapp)
    - [4. 📊 PLTAConnect (Branch: `AplikasiSheet`)](#4--pltaconnect-branch-aplikasisheet)
  - [🛠️ Teknologi yang Digunakan](#️-teknologi-yang-digunakan)
  - [💻 Persyaratan Sistem](#-persyaratan-sistem)
  - [🚀 Cara Instalasi](#-cara-instalasi)
    - [Clone Repository](#clone-repository)
    - [Pindah ke Branch yang Diinginkan](#pindah-ke-branch-yang-diinginkan)
  - [📊 Integrasi Google Sheets Otomatis](#-integrasi-google-sheets-otomatis)
    - [✨ Keunggulan Fitur](#-keunggulan-fitur)
    - [⚙️ Alur Sinkronisasi Data](#️-alur-sinkronisasi-data)
  - [👨‍💻 Pengembang](#-pengembang)
  - [📄 Lisensi](#-lisensi)

---

## 📖 Tentang Repository

Repository ini berisi kumpulan aplikasi Android yang dikembangkan untuk mendukung berbagai kebutuhan operasional. Setiap branch pada repository ini berisi aplikasi yang berbeda dengan fungsi dan tujuan masing-masing.

---


### 1. 📋 AbsenUbrug (Branch: `egia`)
**Aplikasi Absensi Karyawan**

AbsenUbrug adalah aplikasi mobile untuk melakukan pencatatan kehadiran karyawan dengan fitur upload bukti hadir dan pengelolaan jadwal.

**Fitur Utama:**
- 🔐 Autentikasi Pengguna
- 📅 Kalender Absensi
- 📷 Upload Bukti Kehadiran
- 📊 Rekap Kehadiran
- 👤 Profil Pengguna

**Package:** `org.d3if3121.absenubrug`

---

### 2. 👨‍💼 AbsenUbrug Admin (Branch: `AdminApp`)
**Aplikasi Admin Pengelolaan Absensi**

AbsenUbrugAdmin adalah aplikasi untuk administrator dalam mengelola data kehadiran karyawan secara komprehensif.

**Fitur Utama:**
- 🔐 Login Admin
- 👥 Manajemen Data Karyawan
- 📋 Detail Karyawan
- 📊 Rekap Absensi
- 📝 Pengelolaan Proyek
- 📅 Kalender Terintegrasi

**Package:** `org.d3if3121.absenubrugadmin`

⚡ Integrasi Spreadsheet Otomatis
Seluruh data yang masuk melalui aplikasi ini tidak hanya tersimpan di Firebase Firestore, tetapi juga dikonfigurasi untuk tersinkronisasi secara otomatis ke Google Sheets.

Keunggulan Fitur:

Real-time Sync: Setiap ada data absensi atau produksi baru, baris baru akan otomatis muncul di Spreadsheet.

Easy Reporting: Memudahkan staf kantor untuk mengolah data (filter/pivot) tanpa perlu membuka Console Firebase.

Automated Backup: Berfungsi sebagai cadangan data yang mudah diakses kapan saja.

---

### 4. 📊 PLTAConnect (Branch: `AplikasiSheet`)
**Aplikasi Manajemen Data PLTA**

PLTAConnect adalah aplikasi untuk pengelolaan dan pencatatan data operasional Pembangkit Listrik Tenaga Air (PLTA).

**Fitur Utama:**
- 🔐 Autentikasi Pengguna
- 📈 Pencatatan Data Produksi
- 💧 Data Debit Air
- ⚡ Data Pemakaian Listrik
- 📥 Data Masuk
- 📅 Kalender Operasional
- 📊 Laporan Terintegrasi

**Package:** `org.d3if3121.pltaconnect`

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Keterangan |
|-----------|------------|
| **Kotlin** | Bahasa pemrograman utama |
| **Jetpack Compose** | UI Toolkit modern untuk Android |
| **Hilt** | Dependency Injection |
| **Firebase Firestore** | Database real-time |
| **Firebase Storage** | Penyimpanan file dan gambar |
| **Coil** | Image loading library |
| **Material Design 3** | Komponen UI |
| **Navigation Compose** | Navigasi antar halaman |
| **DataStore** | Penyimpanan preferensi lokal |

---

## 💻 Persyaratan Sistem

- **Android Studio:** Hedgehog atau lebih baru
- **Minimum SDK:** API 26 (Android 8.0 Oreo)
- **Target SDK:** API 34 (Android 14)
- **JDK:** Java 19
- **Gradle:** 8.x

---

# Untuk aplikasi AbsenUbrug
git checkout egia

# Untuk aplikasi AbsenUbrug Admin
git checkout AdminApp

# Untuk aplikasi PLTAConnect
git checkout AplikasiSheet


### Build dan Run
1. Buka proyek di Android Studio
2. Sync Gradle files
3. Hubungkan perangkat Android atau jalankan emulator
4. Klik **Run** atau tekan `Shift + F10`

---

## 🌿 Struktur Branch

```
pltaubrug-app/
├── egia          → AbsenUbrug (Absensi karyawan)
├── AdminApp      → AbsenUbrug Admin (Admin Absensi)
├── AplikasiSheet → PLTAConnect (Data PLTA)
```

---

## 📊 Integrasi Google Sheets Otomatis

Proyek ini telah dikonfigurasi dengan sistem sinkronisasi data otomatis. Setiap data yang masuk melalui **Firebase Firestore** akan diteruskan secara *real-time* ke **Google Sheets** menggunakan *Firebase Cloud Functions* / *Extension*.

### ✨ Keunggulan Fitur
* **🔄 Real-time Sync:** Setiap kali ada data absensi atau operasional baru, baris baru akan otomatis muncul di Spreadsheet tanpa perlu input manual.
* **📂 Reporting yang Mudah:** Memudahkan staf administrasi untuk melakukan filter, membuat tabel pivot, dan mencetak laporan bulanan tanpa perlu akses ke Firebase Console.
* **🛡️ Backup Terotomatisasi:** Berfungsi sebagai cadangan data (mirroring) yang aman dan mudah diakses oleh pihak manajemen.

### ⚙️ Alur Sinkronisasi Data


1.  **Aplikasi Android** mengirimkan data ke Firestore.
2.  **Firebase trigger** mendeteksi adanya dokumen baru.
3.  **Google Sheets API** menulis data tersebut ke sheet yang telah ditentukan sesuai dengan kategori (Absensi atau Produksi).

## 👨‍💻 Pengembang

*Dxvnee*

---

## 📄 Lisensi

Proyek ini dikembangkan sebagai bagian dari tugas akademik D3 Teknik Informatika Telkom University.

---

<div align="center">

**Dibuat dengan ❤️ oleh Tim PLTA Ubrug**

*© 2024 D3 Teknik Informatika - Telkom University*

</div>
