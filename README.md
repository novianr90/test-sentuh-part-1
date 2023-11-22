# Test Sentuh Part 1

## Brief
1. Buat Activity sederhana berisi 1 tombol
2. Kemudian klik tombol untuk mengaktifkan service
3. Di dalam service ada 1 tombol
4. Klik tombol di service tersebut untuk mengirim string ke Activity melalui interface / callback dan bukan memakai Intent
5. Tampilkan data dari interface tersebut

## Solution
1. Membuat sebuah interface yang digunakan antara `MainActivity` dan `ServiceSendString`
2. `ServiceSendString` memiliki logic untuk menampilkan sebuah button yang floating (bukan FloatingActionButton) yang berfungsi untuk mengirim data **String from Service**
3. Menggunakan `IBinder` juga dengan `bindService()` dan `unbindService()` sehingga dapat `inject` dependency yaitu `ServiceCallback` ke dalam `ServiceSendString` class Service
4. Ketika Button `Activate Service` ditekan, `bindService()` akan terinvoke
5. Ketika memasuki `onDestroy()`, `unbindService()` akan terinvoke

## Docs
![Result](https://raw.githubusercontent.com/novianr90/test-sentuh-part-1/master/result/result.gif)

[File APK](https://github.com/novianr90/test-sentuh-part-1/blob/master/debug/app-debug.apk)
