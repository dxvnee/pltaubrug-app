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

- [Tentang Repository](#-tentang-repository)
- [Daftar Aplikasi](#-daftar-aplikasi)
- [Teknologi yang Digunakan](#-teknologi-yang-digunakan)
- [Persyaratan Sistem](#-persyaratan-sistem)
- [Cara Instalasi](#-cara-instalasi)
- [Struktur Branch](#-struktur-branch)
- [Tim Pengembang](#-tim-pengembang)

---

## 📖 Tentang Repository

Repository ini berisi kumpulan aplikasi Android yang dikembangkan untuk mendukung berbagai kebutuhan operasional. Setiap branch pada repository ini berisi aplikasi yang berbeda dengan fungsi dan tujuan masing-masing.

---

## 📱 Daftar Aplikasi

### 1. 🔗 Tellink (Branch: `main`)
**Aplikasi Rekrutmen Proyek Mahasiswa**

Tellink adalah aplikasi yang menghubungkan mahasiswa satu dengan mahasiswa lainnya, terutama mahasiswa yang ingin membuat proyek dengan mahasiswa yang ingin bergabung ke proyek tersebut.

**Fitur Utama:**
- 📝 Registrasi dan Login
- 🏠 Beranda Proyek
- 📋 Manajemen Proyek
- ✅ Konfirmasi Pendaftaran
- ✏️ Edit Profil
- 📤 Upload Berkas Pendukung

**Package:** `org.d3if3121.tellink`

---

### 2. 📋 AbsenUbrug (Branch: `egia`)
**Aplikasi Absensi Mahasiswa**

AbsenUbrug adalah aplikasi mobile untuk melakukan pencatatan kehadiran mahasiswa dengan fitur upload bukti hadir dan pengelolaan jadwal.

**Fitur Utama:**
- 🔐 Autentikasi Pengguna
- 📅 Kalender Absensi
- 📷 Upload Bukti Kehadiran
- 📊 Rekap Kehadiran
- 👤 Profil Pengguna

**Package:** `org.d3if3121.absenubrug`

---

### 3. 👨‍💼 AbsenUbrug Admin (Branch: `AdminApp`)
**Aplikasi Admin Pengelolaan Absensi**

AbsenUbrugAdmin adalah aplikasi untuk administrator dalam mengelola data kehadiran mahasiswa secara komprehensif.

**Fitur Utama:**
- 🔐 Login Admin
- 👥 Manajemen Data Karyawan/Mahasiswa
- 📋 Detail Karyawan
- 📊 Rekap Absensi
- 📝 Pengelolaan Proyek
- 📅 Kalender Terintegrasi

**Package:** `org.d3if3121.absenubrugadmin`

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

### 5. 🗂️ AdminAbsen (Branch: `absenadmin`)
**Aplikasi Admin Ketidakhadiran**

AdminAbsen adalah aplikasi untuk pengelolaan dan konfirmasi data ketidakhadiran serta manajemen pengguna.

**Fitur Utama:**
- 📝 Registrasi dan Login
- ✅ Konfirmasi Kehadiran
- ✏️ Edit Data
- 📋 Manajemen Proyek
- 👤 Profil Pengguna

**Package:** `org.d3if3121.AdminAbsen`

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

## 🚀 Cara Instalasi

### Clone Repository
```bash
git clone https://github.com/dxvnee/pltaubrug-app.git
cd pltaubrug-app
```

### Pindah ke Branch yang Diinginkan
```bash
# Untuk aplikasi Tellink (main)
git checkout main

# Untuk aplikasi AbsenUbrug
git checkout egia

# Untuk aplikasi AbsenUbrug Admin
git checkout AdminApp

# Untuk aplikasi PLTAConnect
git checkout AplikasiSheet

# Untuk aplikasi AdminAbsen
git checkout absenadmin
```

### Build dan Run
1. Buka proyek di Android Studio
2. Sync Gradle files
3. Hubungkan perangkat Android atau jalankan emulator
4. Klik **Run** atau tekan `Shift + F10`

---

## 🌿 Struktur Branch

```
pltaubrug-app/
├── main          → Tellink (Rekrutmen Proyek)
├── egia          → AbsenUbrug (Absensi Mahasiswa)
├── AdminApp      → AbsenUbrug Admin (Admin Absensi)
├── AplikasiSheet → PLTAConnect (Data PLTA)
└── absenadmin    → AdminAbsen (Admin Ketidakhadiran)
```

---

## 👨‍💻 Tim Pengembang

<div align="center">

| NIM | Nama | GitHub |
|:---:|:----:|:------:|
| 6706223121 | **Eigiya Daramuli Kale** | [![GitHub](https://img.shields.io/badge/-dxvnee-181717?style=flat-square&logo=github)](https://github.com/dxvnee) |
| 6706223124 | **Yustifani Putri Candrawulaningsih** | [![GitHub](https://img.shields.io/badge/-yustifani-181717?style=flat-square&logo=github)](https://github.com/yustifani) |
| 6706223164 | **Dzikri Abdu Rahman** | [![GitHub](https://img.shields.io/badge/-dziryizami-181717?style=flat-square&logo=github)](https://github.com/dziryizami) |

</div>

---

## 📄 Lisensi

Proyek ini dikembangkan sebagai bagian dari tugas akademik D3 Teknik Informatika Telkom University.

---

<div align="center">

**Dibuat dengan ❤️ oleh Tim PLTA Ubrug**

*© 2024 D3 Teknik Informatika - Telkom University*

</div>
