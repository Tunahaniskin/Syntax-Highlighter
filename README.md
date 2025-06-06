# Gerçek Zamanlı Sözdizimi Vurgulamalı Java Kod Editörü

## İçerik

- Proje Tanımı

- Seçilen Programlama Dili ve Nedenleri

- Sözcüksel Analiz

- Sözdizimsel Analiz

- Gerçek Zamanlı Vurgulama

- GUI İşlemleri

- Kullanılan Teknikler Özeti

- Örnek Kod Parçası

- Nasıl Çalışır?

- Kurulum ve Çalıştırma

- Demo Videosu

- Proje Raporu

---

## Proje Tanımı

Bu proje, Swing kullanarak oluşturulmuş bir Java kod editörüdür. Editör, gerçek zamanlı olarak sözdizimi vurgulaması yapar ve yazılan kodun dilbilgisel doğruluğunu kontrol eder. Projede hiçbir harici kütüphane kullanılmamıştır. Bu projenin yapımında AI'dan yardım alınmıştır.

---

## Seçilen Programlama Dili ve Nedenleri

- Dil: Java

Nedenler:

    - Swing ile GUI desteği

    - Platformdan bağımsız

    - Regex (Pattern/Matcher) ile sözcüksel analiz kolaylığı

---

## Sözcüksel Analiz

Girdi, Pattern ve Matcher kullanılarak şu token türlerine ayrılır:

- IF, ELSE, FOR, WHILE, SWITCH, CASE, RETURN, BREAK

- INT, FLOAT, STRING, CHAR, BOOLEAN, TRUE, FALSE

- Literaller: NUMBER, FLOATNUMBER, STRİNGMETIN, CHARMETIN

- Operatörler: ASSIGN (=), EQ (==), PP (++), MM (--), vb.

- Parantez ve blok işaretleri: LPAREN, RPAREN, LBRACE, RBRACE, SEMICOLON, COLON

---

## Sözdizimsel Analiz

Yukarıdan aşağıya (Top-Down) yaklaşımla recursive parser yazılmıştır.
Aşağıdaki yapılar desteklenmektedir:

- if-else

- while

- for (int i = 0; i < 10; i++) yapısı)

- switch-case

- return ve break

- Değişken tanımlama (int, float, string, char, boolean)

---

## Gerçek Zamanlı Vurgulama

- DocumentListener kullanılarak metin değiştiğinde renklendir() metodu çağrılır.

- StyledDocument ile her token tipi farklı renkte gösterilir.

---

## GUI İşlemleri

- Swing JFrame, JPanel, JTextPane, JButton kullanılmıştır.

- Kaydırma çubuğu ile büyük kodlar rahat gezilir.

- Kod Kontrol butonuna basıldığında analiz yapılır.

---

## Kullanılan Teknikler Özeti

- Sözcüksel Analiz: Regex tabanlı

- Sözdizimsel Analiz: Top-Down recursive descent

- GUI: Swing + JTextPane + StyledDocument

- Vurgulama: En az 6 token tipi renklendirilmekte

---

## Örnek Kod Parçası

    int a = 5;
    if (a < 10) {
        string mesaj = "Merhaba";
    } else {
        char c = 'x';
    }

---

## Nasıl Çalışır?

1. Kullanıcı metin yazdıkça, DocumentListener tetiklenir.

2. renklendir() metodu regex ile token tiplerini bulur ve renklendirir.

3. Kod Kontrol butonuna basılırsa, tokenAyirici ile token listesi çıkarılır.

4. Bu liste KodAnalizci ile parse edilir.

---

## Kurulum ve Çalıştırma

1. Java 17+ kurulu olmalı
2. IDE olarak IntelliJ, VSCode veya NetBeans kullanabilirsiniz
3. proje_main.java dosyasını çalıştırın

---

## Demo Videosu

Video: [YouTube Bağlantısı Gelecek]

---

## Proje Raporu

Raporu indir: 📄 [Proje Raporunu İndir](https://github.com/Tunahaniskin/Syntax-Highlighter/blob/main/ProjeRaporu.docx?raw=true)




