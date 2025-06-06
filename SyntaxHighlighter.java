
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class SyntaxHighlighter {

    public static void main(String[] args) {
        JFrame editorPenceresi = new JFrame("Java Kod Editörü");
        editorPenceresi.setSize(800, 600);
        editorPenceresi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editorPenceresi.setLocationRelativeTo(null);

        JPanel anaPanel = new JPanel(new BorderLayout());

        JTextPane kodEditoru = new JTextPane();
        kodEditoru.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane kaydirmaCubugu = new JScrollPane(kodEditoru);

        JButton kontrolButonu = new JButton("Kod Kontrol");
        kontrolButonu.setFont(new Font("Arial", Font.BOLD, 14));
        kontrolButonu.setBackground(new Color(70, 130, 180));
        kontrolButonu.setForeground(Color.WHITE);

        kontrolButonu.addActionListener(e -> {
            String kodMetni = kodEditoru.getText();
            List<KodToken> tokenListesi = tokenAyirici(kodMetni);
            KodAnalizci analizci = new KodAnalizci(tokenListesi);

            if (analizci.kodKontrol()) {
                JOptionPane.showMessageDialog(editorPenceresi,
                        "Kod sözdizimi doğru!",
                        "Kontrol Sonucu",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(editorPenceresi,
                        "Sözdiziminde hata bulundu!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel butonPaneli = new JPanel(new FlowLayout(FlowLayout.CENTER));
        butonPaneli.add(kontrolButonu);

        anaPanel.add(kaydirmaCubugu, BorderLayout.CENTER);
        anaPanel.add(butonPaneli, BorderLayout.SOUTH);

        editorPenceresi.add(anaPanel);

        kodEditoru.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> renklendir(kodEditoru));
            }
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> renklendir(kodEditoru));
            }
            public void changedUpdate(DocumentEvent e) {}
        });

        editorPenceresi.setVisible(true);
    }

    public static class KodToken {
        public String tur;
        public String deger;

        public KodToken(String tur, String deger) {
            this.tur = tur;
            this.deger = deger;
        }
    }

    public static class KodAnalizci {
        private List<KodToken> tokenListesi;
        private int pozisyon = 0;

        public KodAnalizci(List<KodToken> tokenListesi) {
            this.tokenListesi = tokenListesi;
        }

        private KodToken ileri() {
            return pozisyon < tokenListesi.size() ? tokenListesi.get(pozisyon++) : null;
        }

        private KodToken guncel() {
            return pozisyon < tokenListesi.size() ? tokenListesi.get(pozisyon) : null;
        }

        private boolean esles(String beklenen) {
            if (guncel() != null && guncel().tur.equals(beklenen)) {
                ileri();
                return true;
            }
            return false;
        }

        public boolean kodKontrol() {
            pozisyon = 0;
            while (pozisyon < tokenListesi.size()) {
                if (!ifadeKontrolu()) return false;
            }
            return true;
        }

        public boolean ifadeKontrolu() {
            return ifKontrolu() || whileKontrolu() || degiskenTanimlama() || switchKontrolu() || breakKontrolu() || returnKontrolu() || forKontrolu() 
            		|| elseKontrolu();
        }


        public boolean ifKontrolu() {
            int baslangic = pozisyon;
            if (esles("IF") && esles("LPAREN") && esles("IDENT") && (esles("EQ") || esles("BUYUK") || esles("KUCUK") || esles("KESİT") || esles("BESİT"))
            		&& esles("NUMBER") && esles("RPAREN") && esles("LBRACE")) {
            	
            	while (guncel() != null && !guncel().tur.equals("RBRACE")) {
                    if (!ifadeKontrolu()) {
                        pozisyon = baslangic;
                        return false;
                    }
                }
                return esles("RBRACE");
            	
            }
            
            pozisyon = baslangic;
            return false;
        }
            
            public boolean elseKontrolu() {
                int baslangic = pozisyon;

                if (esles("ELSE") && esles("LBRACE")) {
                    while (guncel() != null && !guncel().tur.equals("RBRACE")) {
                        if (!ifadeKontrolu()) {
                            pozisyon = baslangic;
                            return false;
                        }
                    }
                    return esles("RBRACE");
                }

                pozisyon = baslangic;
                return false;
            }



        public boolean whileKontrolu() {
            int baslangic = pozisyon;
            if (esles("WHILE") && esles("LPAREN") && esles("IDENT") && (esles("EQ") || esles("BUYUK") || esles("KUCUK") || esles("KESİT") || esles("BESİT"))
            		&& esles("NUMBER") && esles("RPAREN") && esles("LBRACE")) {
            	
            	while (guncel() != null && !guncel().tur.equals("RBRACE")) {
                    if (!ifadeKontrolu()) {
                        pozisyon = baslangic;
                        return false;
                    }
                }
                return esles("RBRACE");
            	
               
            }
            
            pozisyon = baslangic;
            return false;
        }
        
        public boolean switchKontrolu() {
        	
            int baslangic = pozisyon;
            boolean enAzBirCase = false;

            if (esles("SWITCH") && esles("LPAREN") && esles("IDENT") && esles("RPAREN") && esles("LBRACE")) {
            	
            	
            	// case blokları
                while (guncel() != null && guncel().tur.equals("CASE")) {
                    ileri(); // CASE
                    if (!esles("NUMBER") || !esles("COLON")) {
                        pozisyon = baslangic;
                        return false;
                    }

                    // case içine bakıyo
                    while (guncel() != null && !guncel().tur.equals("CASE") && !guncel().tur.equals("RBRACE")) {

                        if (!ifadeKontrolu()) {
                            pozisyon = baslangic;
                            return false;
                        }
                    }

                    enAzBirCase = true;
                }
            	
             // switch bloğu kapanmalı
                if (!esles("RBRACE")) {
                    pozisyon = baslangic;
                    return false;
                }

                return enAzBirCase;
                
            }
            
            pozisyon = baslangic;
            return false;
  
        }
        
        public boolean breakKontrolu() {
            int baslangic = pozisyon;
            if (esles("BREAK") && esles("SEMICOLON")) return true;
            pozisyon = baslangic;
            return false;
        }

        public boolean returnKontrolu() {
            int baslangic = pozisyon;
            if (esles("RETURN") && ( esles("NUMBER") || esles("IDENT") ) && esles("SEMICOLON")) return true;
            pozisyon = baslangic;
            return false;
        }

        public boolean forKontrolu() {
            int baslangic = pozisyon;

            if (esles("FOR") && esles("LPAREN")) {
                if (!degiskenTanimlama()) {
                    pozisyon = baslangic;
                    return false;
                }

                if (!(esles("IDENT") && (esles("EQ") || esles("BUYUK") || esles("KUCUK") || esles("BESİT") || esles("KESİT"))
                		&& (esles("NUMBER") || esles("IDENT")) && esles("SEMICOLON"))) {
                    pozisyon = baslangic;
                    return false;
                }

                if (!(esles("IDENT") && (esles("PP") || esles("MM")))) {
                    pozisyon = baslangic;
                    return false;
                }

                if (!esles("RPAREN") || !esles("LBRACE")) {
                    pozisyon = baslangic;
                    return false;
                }

                while (guncel() != null && !guncel().tur.equals("RBRACE")) {
                    if (!ifadeKontrolu()) {
                        pozisyon = baslangic;
                        return false;
                    }
                }

                return esles("RBRACE");
            }

            pozisyon = baslangic;
            return false;
        }




        public boolean degiskenTanimlama() {
            int baslangic = pozisyon;
            if (esles("INT") && esles("IDENT") && esles("ASSIGN") && esles("NUMBER") && esles("SEMICOLON")) {
                return true;
            }
            pozisyon = baslangic;
            if (esles("FLOAT") && esles("IDENT") && esles("ASSIGN") && ( esles("FLOATNUMBER") || esles("NUMBER") ) && esles("SEMICOLON")) {
                return true;
            }
            pozisyon = baslangic;
            if (esles("STRING") && esles("IDENT") && esles("ASSIGN") && esles("STRİNGMETIN") && esles("SEMICOLON")) {
                return true;
            }
            pozisyon = baslangic;
            if (esles("CHAR") && esles("IDENT") && esles("ASSIGN") && esles("CHARMETIN") && esles("SEMICOLON")) {
                return true;
            }
            pozisyon = baslangic;
            if (esles("BOOLEAN") && esles("IDENT") && esles("ASSIGN") && (esles("TRUE") || esles("FALSE")) && esles("SEMICOLON")) {
                return true;
            }
            pozisyon = baslangic;
            return false;
        }
    }

    public enum TokenTur {
        ANAHTAR_KELIME(Color.BLUE, "\\b(if|else|for|while|switch|case|break|return)\\b"),
        TÜR(Color.GREEN, "\\b(int|string|float|double|char|boolean)\\b"),
        METIN(Color.RED, "\"([^\"\\\\]|\\\\.)*\""),
        YORUM(Color.GRAY, "//[^\\n]*|/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/"),
        SAYI(Color.MAGENTA, "\\b\\d+\\b"),
        OPERATOR(Color.ORANGE, "[+\\-*/%=&|<>!^~?:]|&&|\\|\\|");

        public final Color renk;
        public final String desen;

        TokenTur(Color renk, String desen) {
            this.renk = renk;
            this.desen = desen;
        }
    }

    public static List<KodToken> tokenAyirici(String girdi) {
        List<KodToken> tokenListesi = new ArrayList<>();
        Pattern p = Pattern.compile("==|=|<=|>=|\\++|\\--|[{}();:+\\-*/<>]|\\d+\\.\\d+|\\d+|\"[^\"]*\"|'.'|\\b[a-zA-Z_][a-zA-Z0-9_]*\\b");
        Matcher m = p.matcher(girdi);

        while (m.find()) {
            String val = m.group();
            if (val.equals("if")) tokenListesi.add(new KodToken("IF", val));
            else if (val.matches("\"[^\"]*\"")) tokenListesi.add(new KodToken("STRİNGMETIN", val)); 
        	else if (val.matches("'.'")) tokenListesi.add(new KodToken("CHARMETIN", val));
            else if (val.equals("else")) tokenListesi.add(new KodToken("ELSE", val));
            else if (val.equals("for")) tokenListesi.add(new KodToken("FOR", val));
            else if (val.equals("while")) tokenListesi.add(new KodToken("WHILE", val));
            else if (val.equals("switch")) tokenListesi.add(new KodToken("SWITCH", val));
            else if (val.equals("case")) tokenListesi.add(new KodToken("CASE", val));
            else if (val.equals("break")) tokenListesi.add(new KodToken("BREAK", val));
            else if (val.equals("return")) tokenListesi.add(new KodToken("RETURN", val));
            else if (val.equals("int")) tokenListesi.add(new KodToken("INT", val));
            else if (val.equals("string")) tokenListesi.add(new KodToken("STRING", val));
        	else if (val.equals("float")) tokenListesi.add(new KodToken("FLOAT", val));     
        	else if (val.equals("char")) tokenListesi.add(new KodToken("CHAR", val));
        	else if (val.equals("boolean")) tokenListesi.add(new KodToken("BOOLEAN", val));
        	else if (val.equals("true")) tokenListesi.add(new KodToken("TRUE", val));
        	else if (val.equals("false")) tokenListesi.add(new KodToken("FALSE", val));
            else if (val.equals("(")) tokenListesi.add(new KodToken("LPAREN", val));
            else if (val.equals(")")) tokenListesi.add(new KodToken("RPAREN", val));
            else if (val.equals("{")) tokenListesi.add(new KodToken("LBRACE", val));
            else if (val.equals("}")) tokenListesi.add(new KodToken("RBRACE", val));
            else if (val.equals("==")) tokenListesi.add(new KodToken("EQ", val));
            else if (val.equals("=")) tokenListesi.add(new KodToken("ASSIGN", val));
            else if (val.equals(";")) tokenListesi.add(new KodToken("SEMICOLON", val));
            else if (val.equals(":")) tokenListesi.add(new KodToken("COLON", val));
            else if (val.equals("<=")) tokenListesi.add(new KodToken("KESİT", val));	//KESİT = küçük esit
			else if (val.equals(">=")) tokenListesi.add(new KodToken("BESİT", val));	//BESİT = büyük esit
            else if (val.equals("<")) tokenListesi.add(new KodToken("KUCUK", val));
			else if (val.equals(">")) tokenListesi.add(new KodToken("BUYUK", val));
			else if (val.equals("++")) tokenListesi.add(new KodToken("PP", val));		//plus plus
			else if (val.equals("--")) tokenListesi.add(new KodToken("MM", val));	// minus minus
            else if (val.matches("[+*/<>\\-]")) tokenListesi.add(new KodToken("OP", val));
            else if (val.matches("<>")) tokenListesi.add(new KodToken("Esit", val));
            else if (val.matches("\\d+\\.\\d+")) tokenListesi.add(new KodToken("FLOATNUMBER", val));
            else if (val.matches("\\d+")) tokenListesi.add(new KodToken("NUMBER", val));
            else tokenListesi.add(new KodToken("IDENT", val));
        }

        return tokenListesi;
    }

    public static void renklendir(JTextPane editor) {
        try {
            StyledDocument dokuman = editor.getStyledDocument();
            StyleContext stilKonteksi = StyleContext.getDefaultStyleContext();
            String metin = dokuman.getText(0, dokuman.getLength());

            Style varsayilanStil = stilKonteksi.addStyle("Varsayilan", null);
            StyleConstants.setForeground(varsayilanStil, Color.BLACK);
            dokuman.setCharacterAttributes(0, metin.length(), varsayilanStil, true);

            for (TokenTur token : TokenTur.values()) {
                Style stil = stilKonteksi.addStyle(token.name(), null);
                StyleConstants.setForeground(stil, token.renk);

                Pattern desen = Pattern.compile(token.desen);
                Matcher eslesme = desen.matcher(metin);

                while (eslesme.find()) {
                    int baslangic = eslesme.start();
                    int bitis = eslesme.end();
                    dokuman.setCharacterAttributes(baslangic, bitis - baslangic, stil, true);
                }
            }
        } catch (BadLocationException hata) {
            System.err.println("Dokuman konum hatası: " + hata.getMessage());
        }
    }
}

