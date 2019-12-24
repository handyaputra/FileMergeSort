cara penggunaan :
1. Run Main.java di package RankerLauncher
2. Klik Browse.. pilih file yg digunakan.. pastikan berformat .TXT
3. Pilih Urutan Berdasarkan Nilai Terendah / Nilai Tertinggi
4. Klik RANK!
5. Muncul pop-up pengolahan dan pesan di console (tentang keadaan proses)
6. Cek hasil file di folder src/Ranker
7. File score.xls merupakan data nilai keseluruhan,
   File result.xls merupakan hasil perankingan

---NOTES---
Main.java di package Ranker merupakan inisiator dan debugger program
Berisi proses MergeSort (method sort, merge, printArray) dan ConvertTxt2Excel (method create)
Jika code ini dijalankan akan default memanggil file src/Ranker.score.txt dengan pengurutan berdasarkan Nilai Tertinggi

File Main.java di package RankerLauncher merupakan tempat inisialisasi GUI bagi program Ranker.Main.java
Core/Inti pengolahan program tetap berada pada Ranker.Main.java
