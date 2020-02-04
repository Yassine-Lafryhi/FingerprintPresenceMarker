package SPM;


import java.awt.*;
import java.io.File;
import java.io.IOException;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Controller {
    WebEngine engine;
    double aa = 0d;

    String Enrl = "";
    @FXML
    Label EnrlMsg;
    @FXML
    TextField StudentID;

    @FXML
    DatePicker datePicker;
    @FXML
    TextField UserNameTF;
    @FXML
    TextField D1S1N, D1S1P,
            D1S2N, D1S2P,
            D1S3N, D1S3P,
            D1S4N, D1S4P,

    D2S1N, D2S1P,
            D2S2N, D2S2P,
            D2S3N, D2S3P,
            D2S4N, D2S4P,

    D3S1N, D3S1P,
            D3S2N, D3S2P,
            D3S3N, D3S3P,
            D3S4N, D3S4P,

    D4S1N, D4S1P,
            D4S2N, D4S2P,
            D4S3N, D4S3P,
            D4S4N, D4S4P,

    D5S1N, D5S1P,
            D5S2N, D5S2P,
            D5S3N, D5S3P,
            D5S4N, D5S4P,

    D6S1N, D6S1P,
            D6S2N, D6S2P,
            D6S3N, D6S3P,
            D6S4N, D6S4P;


    @FXML
    Pane ListPane;
    @FXML
    ScrollPane ListScrl;
    @FXML
    PasswordField PasswordTF;
    @FXML
    Button ConnectButton;
    @FXML
    Label LoginErrorTF;
    @FXML
    WebView WV;
    @FXML
    ProgressIndicator PI;

    SerialPort serialPort;


    String zzz = "";

    String RTDB_Date = "", RTDB_Value = "";
    String time;
    int c = 0;

    int[] SI = new int[4];

    int Seance = 1;
    String[] ns;


    String a;


    public void Connect() {

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                PI.setProgress(aa);
                aa += 0.08d;
            }
        }, 10, 100);


        Thread one = new Thread() {
            public void run() {
                try {

                    serialPort = new SerialPort("/dev/cu.SPM-DevB");//1111 as HC-06 passord


                    serialPort.openPort();
                    serialPort.setParams(SerialPort.BAUDRATE_9600,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);


                } catch (SerialPortException e) {
                    e.printStackTrace();
                }


            }
        };

        one.start();


        ConnectButton.setText("       Connected");


    }


    int i = 1;


    public void GetList() {


        try {
            //Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);


            LocalDate localDate = datePicker.getValue();


            String Year = localDate.toString().split("-")[0];
            String Month = String.valueOf(Integer.parseInt(localDate.toString().split("-")[1]));
            String Day = String.valueOf(Integer.parseInt(localDate.toString().split("-")[2]));


            RTDB_Date = localDate.toString().split("-")[0] + "/" + localDate.toString().split("-")[1] + "/" + localDate.toString().split("-")[2];


            String Date = Year + "/" + Month + "/" + Day;
            System.out.println(Date);

            serialPort.writeBytes(Date.getBytes());//Write data to port

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {


                byte[] b = serialPort.readBytes(serialPort.getInputBufferBytesCount());


                zzz = new String(b, StandardCharsets.UTF_8);
                System.out.print(zzz);


                /** if (a==null) {
                 break;

                 }
                 **/
                //  System.out.print(zzz);


                //}


                serialPort.closePort();//Close serial port
            } catch (SerialPortException ex) {
                System.out.println(ex);
            }


            //serialPort.closePort();//Close serial port
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }

        System.out.print(zzz);
        //Just to work without Hardwares

        /**  zzz = "2019/2/4-14:23\n" +
         "1\n" +
         "2\n" +
         "3\n" +
         "4\n" +
         "5\n" +
         "6\n" +
         "7\n" +
         "8\n" +
         "9\n" +
         "10\n" +
         "11\n" +
         "12\n" +
         "13\n" +
         "14\n" +
         "15\n" +
         "16\n" +
         "17\n" +
         "18\n" +
         "19\n" +
         "20\n" +
         "21\n" +
         "22\n" +
         "23\n" +
         "24\n" +
         "25\n" +
         "26\n" +
         "27\n" +
         "28\n" +
         "29\n" +
         "30\n" +

         "32\n" +
         "33\n" +
         "34\n" +
         "35\n" +
         "36\n" +
         "37\n" +
         "38\n" +
         "39\n" +
         "40\n" +
         "41\n" +
         "42\n" +
         "43\n" +
         "44\n" +
         "45\n" +
         "46\n" +
         "47\n" +
         "48\n" +
         "49\n" +
         "50\n" +
         "51\n" +
         "52\n" +
         "53\n" +
         "54\n" +
         "55\n" +
         "56\n" +
         "57\n" +
         "58\n" +
         "59\n" +
         "60\n" +
         "61\n" +
         "62\n" +
         "63\n" +
         "64\n" +

         "66\n" +
         "67\n" +
         "68\n" +
         "69\n" +
         "70\n" +
         "71\n" +
         "72\n" +
         "73\n" +
         "74\n" +

         "76\n" +
         "77\n" +
         "78\n" +
         "79\n" +
         "80";
         **/

        String[] oo = zzz.split("\n");
        ns = new String[oo.length];


        System.arraycopy(oo, 0, ns, 0, oo.length);


        for (int i = 0; i < ns.length; i++) {

            if (ns[i].length() > 3) {


                SI[c] = i;
                c++;
            }


        }


        GenerateListe();


    }


    private void GenerateListe() {


        String[] First = {
                "MOHAMMED",
                "ANASS",
                "KHAOULA",
                "ANAS",
                "AYOUB",
                "FATIMA-ZAHRA",
                "SAMIRA",
                "IMANE",
                "ASMAA",
                "MAROUA",
                "KHALID",
                "ANAS",
                "OUSSAMA",
                "OUMAIMA",
                "HAJAR",
                "EL MEHDI",
                "YOUSSEF",
                "HALIMA",
                "HAJAR",
                "MARWANE",
                "ACHRAF",
                "REDA ",
                "CHAIMA",
                "IKRAM",
                "SOUFIANE",
                "GHIZLANE",
                "SALAH EDDINE",
                "YAHYA",
                "BOUTAINA",
                "AYOUB",
                "ZAKARIA",
                "MOHAMED",
                "HALIMA",
                "YASSINE",
                "OMAR",
                "OMAR",
                "EL MEHDI",
                "MAROUANE",
                "MOHAMMED SALIM",
                "IMAD",
                "OTMANE",
                "MOHAMED KHALIL",
                "CHAIMAE",
                "SANAA",
                "HOUSSAM",
                "SOUFIANE",
                "AYMANE",
                "ZAKARIA",
                "MOATAZ",
                "KHADIJA",
                "SOUFIANE",
                "ZAKARIA",
                "HAMZA",
                "SOKAINA",
                "KHADIJA",
                "TAHA",
                "MOHAMED",
                "MOUAD",
                "YASSINE",
                "HAMZA",
                "AIMANE",
                "AYA",
                "NEZHA",
                "NOUAMANE",
                "ABDELLATIF",
                "YASSINE",
                "MANAL",
                "MOHAMED AMINE",
                "ILIAS",
                "AYOUB",
                "ABDELHAMID",
                "MOHAMED",
                "AIMENE",
                "WAIL",
                "YASSINE",
                "MARIEME",
                "YOUSSEF",
                "MOHAMED AMINE",
                "NAOUPHEL",
                "OUMAYMA"};


        String[] Last = {

                "SEKKAF",
                "TAOUSSI",
                "EL MAJNI",
                "LAOUISSI",
                "CHABANE",
                "DRIDER",
                "DIHADRIL",
                "LAHLOU",
                "EL HARAT",
                "DELLAL",
                "BYFENZINE",
                "OUARDINI",
                "DAKHCH",
                "HAIMAR",
                "ACHOUJ",
                "MOUSSAID",
                "AL-AMGHARI",
                "KHABBOUCH",
                "MISKAR",
                "ELFAROKI",
                "CHEMCHAQ",
                "ABDELHAKMI",
                "EL BAZ",
                "ILOU",
                "ZAIDAN",
                "MARDY",
                "RAZIK",
                "AJBALI",
                "ELYAZIJI",
                "HAMROUDI",
                "MAHMOUD",
                "SAFI",
                "BENDAOUD",
                "AMAL",
                "SANAD",
                "MACHKOUR",
                "LHAROUI",
                "BENCHIEKH",
                "KHALLOUKI",
                "REGUADI",
                "DRIOUECH",
                "HAJOUI",
                "ELANNAOUI",
                "OUADOUD",
                "MESBAH",
                "LORIGA",
                "EL KHAZENTI",
                "GOUMIR",
                "MARANI",
                "IDRISSI YAHYAOUI",
                "AKABI",
                "IHIRRI",
                "JERDOUJ",
                "DAABAL",
                "NAM",
                "RHALLOUB",
                "MOUHIHA",
                "HARIMECH",
                "HAMMOUCHA",
                "FAIK",
                "EZRAIDI",
                "DARKAOUI",
                "DOUAL",
                "BAHIJ",
                "AHAMMAD",
                "CHABNOUNE",
                "JANAH",
                "ELKELLALI",
                "ATTAR",
                "ELKHADDARI",
                "BENKADA",
                "EZZAHI",
                "NOURI",
                "BOUHADDA",
                "LAFRYHI",
                "BENRAHMA",
                "HABALA",
                "ABID",
                "BOUDOUMA",
                "BOURICH"};


        boolean[] Students = new boolean[80];

        for (int i = 0; i < 80; i++) {


            Students[i] = true;
        }


        if (Seance == 1) {

            if (c == 1) {
                SI[1] = ns.length;

            }


            for (int i = 1 + SI[0]; i < SI[1]; i += 1) {


                Students[Integer.parseInt(ns[i].trim()) - 1] = false;


            }

            time = ns[SI[0]];


        }


        if (Seance == 2) {

            if (c == 2) {
                SI[2] = ns.length;

            }


            for (int i = 1 + SI[1]; i < SI[2]; i += 1) {


                Students[Integer.parseInt(ns[i].trim()) - 1] = false;


            }

            time = ns[SI[1]];


        }


        if (Seance == 3) {


            if (c == 3) {
                SI[3] = ns.length;

            }


            for (int i = 1 + SI[2]; i < SI[3]; i += 1) {


                Students[Integer.parseInt(ns[i].trim()) - 1] = false;


            }

            time = ns[SI[2]];


        }


        if (Seance == 4) {

            for (int i = 1 + SI[3]; i < ns.length; i += 1) {


                Students[Integer.parseInt(ns[i].trim()) - 1] = false;


            }

            time = ns[SI[0]];


        }


        String[] timeB = time.split("-");
        String[] date = timeB[0].split("/");
        String day = date[2];
        String month = date[1];
        String year = date[0];


        String[] TimeT = timeB[1].split(":");
        String hour = TimeT[0];
        String minute = TimeT[1];


        String prof = "", subject = "", datetime = "", de = "", aa = "";

        int heure, minutee;
        heure = Integer.parseInt(hour.trim());

        minutee = Integer.parseInt(minute.trim());


        Calendar calendar = Calendar.getInstance();


        Date dateoo = new Date(Integer.parseInt(year.trim()), Integer.parseInt(month.trim()), Integer.parseInt(day.trim()));

        calendar.setTime(dateoo);

        String[] days = new String[]{"", "Jeudi", "Samedi", "Vendredi", "", "Mercredi", "Mardi", "Lundi"};

        String ourday = days[calendar.get(Calendar.DAY_OF_WEEK)];


        if (ourday.equals("Lundi")) {

            datetime = day + "/" + month + "/" + year;
            if ((heure == 8 && minutee >= 30) || (heure == 9) || (heure == 10 && minutee <= 30)) {

                prof = "Prof : H.AMAL";
                subject = "FRANCAIS/TEC";
                de = "08:30";
                aa = "10:30";

            } else if ((heure == 10 && minutee > 30) || (heure == 11) || (heure == 12 && minutee <= 30)) {

                prof = "Prof : H.AMAL";
                subject = "FRANCAIS/TEC";
                de = "10:30";
                aa = "12:30";
            } else if ((heure == 14 && minutee >= 30) || (heure == 15) || (heure == 16 && minutee <= 30)) {

                prof = "Prof : K.BOUZIANE";
                subject = "ANGLAIS";
                de = "14:30";
                aa = "16:30";
            } else if ((heure == 16 && minutee > 30) || (heure == 17) || (heure == 18 && minutee <= 30)) {

                prof = "Prof : A.BAKKALI";
                subject = "ORGANISATION ET DROIT D'ENTREPRISE";
                de = "16:30";
                aa = "18:30";

            } else {

                prof = "Prof : T.TEST";
                subject = "TEST";
                de = "11:11";
                aa = "22:22";
            }


        }


        if (ourday.equals("Mardi")) {

            datetime = day + "/" + month + "/" + year;


            if ((heure == 8 && minutee >= 30) || (heure == 9) || (heure == 10 && minutee <= 30)) {

                prof = "Prof : A.MABROUK";
                subject = "LANGAGE C TP";
                de = "08:30";
                aa = "10:30";

            }


            if ((heure == 10 && minutee > 30) || (heure == 11) || (heure == 12 && minutee <= 30)) {

                prof = "Prof : H.TOUMI";
                subject = "INFORMATIQUE INDISTRIELLE TD";
                de = "10:30";
                aa = "12:30";
            }


            if ((heure == 14 && minutee >= 30) || (heure == 15) || (heure == 16 && minutee <= 30)) {

                prof = "Prof : A.SAIDI";
                subject = "ALGORITHMIQUE TP";
                de = "14:30";
                aa = "16:30";
            }

            if ((heure == 16 && minutee > 30) || (heure == 17) || (heure == 18 && minutee <= 30)) {

                prof = "Prof : A.MABROUK";
                subject = "LANGAGE C COURS";
                de = "16:30";
                aa = "18:30";

            } else {

                prof = "Prof : T.TEST";
                subject = "TEST";
                de = "11:11";
                aa = "22:22";
            }


        }


        if (ourday.equals("Mercredi")) {

            datetime = day + "/" + month + "/" + year;
            if ((heure == 8 && minutee >= 30) || (heure == 9) || (heure == 10 && minutee <= 30)) {

                prof = "Prof : Y.BADDI";
                subject = "ARCHITECTURE ET MAINTENANCE DES ORDINATEUR";
                de = "08:30";
                aa = "10:30";

            } else if ((heure == 10 && minutee > 30) || (heure == 11) || (heure == 12 && minutee <= 30)) {

                prof = "Prof : Z.BAHRAOUI";
                subject = "ANALYSE 1";
                de = "10:30";
                aa = "12:30";
            } else if ((heure == 14 && minutee >= 30) || (heure == 15) || (heure == 16 && minutee <= 30)) {

                prof = "Prof : O.BENCHIHEB";
                subject = "ANALYSE 1 TD";
                de = "14:30";
                aa = "16:30";
            } else if ((heure == 16 && minutee > 30) || (heure == 17) || (heure == 18 && minutee <= 30)) {

                prof = "Prof : H.LAKRIMI";
                subject = "ALGEBRE 1 TD";
                de = "16:30";
                aa = "18:30";

            } else {

                prof = "Prof : T.TEST";
                subject = "TEST";
                de = "11:11";
                aa = "22:22";
            }


        }


        if (ourday.equals("Jeudi")) {

            datetime = day + "/" + month + "/" + year;

            if ((heure == 10 && minutee > 30) || (heure == 11) || (heure == 12 && minutee <= 30)) {

                prof = "Prof : A.ZAAKOUNI";
                subject = "BUREAUTIQUE";
                de = "10:30";
                aa = "12:30";
            } else if ((heure == 14 && minutee >= 30) || (heure == 15) || (heure == 16 && minutee <= 30)) {

                prof = "Prof : A.SAIDI";
                subject = "ALGORITHMIQUE";
                de = "14:30";
                aa = "16:30";
            } else {

                prof = "Prof : T.TEST";
                subject = "TEST";
                de = "11:11";
                aa = "22:22";
            }


        }


        if (ourday.equals("Vendredi")) {

            datetime = day + "/" + month + "/" + year;
            if ((heure == 8 && minutee >= 30) || (heure == 9) || (heure == 10 && minutee <= 30)) {

                prof = "Prof : Z.BAHRAOUI";
                subject = "ALGEBRE 1";
                de = "08:30";
                aa = "10:30";

            } else if ((heure == 10 && minutee > 30) || (heure == 11) || (heure == 12 && minutee <= 30)) {

                prof = "Prof : M.CHOUKI";
                subject = "INFORMATIQUE INDUSTRIELLE TP";
                de = "10:30";
                aa = "12:30";
            } else if ((heure == 14 && minutee >= 30) || (heure == 15) || (heure == 16 && minutee <= 30)) {

                prof = "Prof : H.TOUMI";
                subject = "INFORMATIQUE INDUSTRIELLE TD";
                de = "14:30";
                aa = "16:30";
            } else if ((heure == 16 && minutee > 30) || (heure == 17) || (heure == 18 && minutee <= 30)) {

                prof = "Prof : A.ZAKOUNI";
                subject = "BUREATIQUE TP";
                de = "16:30";
                aa = "18:30";

            } else {

                prof = "Prof : T.TEST";
                subject = "TEST";
                de = "11:11";
                aa = "22:22";
            }


        }


        if (ourday.equals("Samedi")) {

            datetime = day + "/" + month + "/" + year;
            if ((heure == 8 && minutee >= 30) || (heure == 9) || (heure == 10 && minutee <= 30)) {

                prof = "Prof : H.TOUMI";
                subject = "INFORMATIQUE INDUSTRIELLE";
                de = "08:30";
                aa = "10:30";

            } else if ((heure == 10 && minutee > 30) || (heure == 11) || (heure == 12 && minutee <= 30)) {

                prof = "Prof : H.TOUMI";
                subject = "ELECTRONIQUE ANALOGIQUE ET NUMERIQUE";
                de = "10:30";
                aa = "12:30";
            } else {

                prof = "Prof : T.TEST";
                subject = "TEST";
                de = "11:11";
                aa = "22:22";
            }


        }


        String html =
                "<html>\n" +
                        "\n" +
                        "<head>\n" +
                        "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\"/>\n" +
                        "<style id=\"LISTE ESTSB_31937_Styles\">\n" +
                        "\n" +
                        "table\n" +
                        "\t{mso-displayed-decimal-separator:\"\\.\";\n" +
                        "\tmso-displayed-thousand-separator:\"\\,\";}\n" +
                        "@page\n" +
                        "\t{margin:.75in .7in .75in .7in;\n" +
                        "\tmso-header-margin:.3in;\n" +
                        "\tmso-footer-margin:.3in;}\n" +
                        "tr\n" +
                        "\t{mso-height-source:auto;}\n" +
                        "col\n" +
                        "\t{mso-width-source:auto;}\n" +
                        "br\n" +
                        "\t{mso-data-placement:same-cell;}\n" +
                        ".style0\n" +
                        "\t{mso-number-format:General;\n" +
                        "\ttext-align:general;\n" +
                        "\tvertical-align:bottom;\n" +
                        "\twhite-space:nowrap;\n" +
                        "\tmso-rotate:0;\n" +
                        "\tmso-background-source:auto;\n" +
                        "\tmso-pattern:auto;\n" +
                        "\tcolor:black;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\tfont-weight:400;\n" +
                        "\tfont-style:normal;\n" +
                        "\ttext-decoration:none;\n" +
                        "\tfont-family:Calibri, sans-serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\tborder:1;\n" +
                        "\tmso-protection:locked visible;\n" +
                        "\tmso-style-name:Normal;\n" +
                        "\tmso-style-id:0;}\n" +
                        "td\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tpadding-top:1px;\n" +
                        "\tpadding-right:1px;\n" +
                        "\tpadding-left:1px;\n" +
                        "\tmso-ignore:padding;\n" +
                        "\tcolor:black;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\tfont-weight:400;\n" +
                        "\tfont-style:normal;\n" +
                        "\ttext-decoration:none;\n" +
                        "\tfont-family:Calibri, sans-serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\tmso-number-format:General;\n" +
                        "\ttext-align:general;\n" +
                        "\tvertical-align:bottom;\n" +
                        "\tborder:1;\n" +
                        "\tmso-background-source:auto;\n" +
                        "\tmso-pattern:auto;\n" +
                        "\tmso-protection:locked visible;\n" +
                        "\twhite-space:nowrap;\n" +
                        "\tmso-rotate:0;}\n" +
                        ".xl65\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;}\n" +
                        ".xl66\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;}\n" +
                        ".xl67\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;}\n" +
                        ".xl68\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:left;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;}\n" +
                        ".xl69\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;}\n" +
                        ".xl70\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;\n" +
                        "\t\n" +
                        "\tmso-rotate:90;}\n" +
                        ".xl71\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;\n" +
                        "\tmso-rotate:90;}\n" +
                        ".xl72\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:top;\n" +
                        "\tborder:.1pt solid windowtext;}\n" +
                        ".xl73\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:top;\n" +
                        "\tborder-top:.1pt solid windowtext;\n" +
                        "\t\n" +
                        "\tborder-bottom:.1pt solid windowtext;\n" +
                        "\tborder-left:.1pt solid windowtext;}\n" +
                        ".xl74\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:top;\n" +
                        "\tborder-top:.1pt solid windowtext;\n" +
                        "\tborder-right:.01pt solid windowtext;\n" +
                        "\tborder-bottom:.1pt solid windowtext;\n" +
                        "\t}\n" +
                        ".xl75\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:left;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;}\n" +
                        ".xl76\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:left;\n" +
                        "\tborder:.1pt solid windowtext;}\n" +
                        ".xl77\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:left;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;\n" +
                        "\tbackground:#92D050;\n" +
                        "\tmso-pattern:black none;}\n" +
                        ".xl78\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;\n" +
                        "\tbackground:#92D050;\n" +
                        "\tmso-pattern:black none;}\n" +
                        ".xl79\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:left;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;\n" +
                        "\tbackground:#F9918F;\n" +
                        "\tmso-pattern:black none;}\n" +
                        ".xl80\n" +
                        "\t{mso-style-parent:style0;\n" +
                        "\tfont-size:10.0pt;\n" +
                        "\t    font-weight: bold;\n" +
                        "\tfont-family:\"American Typewriter\", serif;\n" +
                        "\tmso-font-charset:0;\n" +
                        "\ttext-align:center;\n" +
                        "\tvertical-align:middle;\n" +
                        "\tborder:.1pt solid windowtext;\n" +
                        "\tbackground:#F9918F;\n" +
                        "\tmso-pattern:black none;}\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body link=\"#0563C1\" vlink=\"#954F72\" class='xl66'>\n" +
                        "<div id=\"LISTE ESTSB_31937\" align=center x:publishsource=\"Excel\">\n" +
                        "\n" +
                        "<table border=1 cellpadding=0 cellspacing=0 width=659 style='border-collapse:\n" +
                        " collapse;table-layout:fixed;width:494pt'>\n" +
                        " <col class='xl66' width=87 style='mso-width-source:userset;mso-width-alt:2773;\n" +
                        " width:65pt'></col>\n" +
                        " <col class='xl66' width=25 style='mso-width-source:userset;mso-width-alt:810;\n" +
                        " width:19pt'></col>\n" +
                        " <col class='xl66' width=21 style='mso-width-source:userset;mso-width-alt:682;\n" +
                        " width:16pt'></col>\n" +
                        " <col class='xl65' width=151 style='mso-width-source:userset;mso-width-alt:4821;\n" +
                        " width:113pt'></col>\n" +
                        " <col class='xl65' width=164 style='mso-width-source:userset;mso-width-alt:5248;\n" +
                        " width:123pt'></col>\n" +
                        " <col class='xl65' width=211 style='mso-width-source:userset;mso-width-alt:6741;\n" +
                        " width:158pt'></col>\n" +
                        " <tr height=27 style='mso-height-source:userset; '>\n" +
                        "  <td style='border:.5px #000' colspan=3 height=27 class='xl69' width=133 >Matière\n" +
                        "  :</td>\n" +
                        "  <td style='border:.5px #000' colspan=2 class='xl73' width=315 style='border-right:.01pt solid black;\n" +
                        "  width:236pt'></td>\n" +
                        "  <td style='border:solid .1px #000' class='xl72' width=211 >Date :\n" +
                        "  </td>\n" +
                        " </tr>\n" +
                        " <tr height=29 style='mso-height-source:userset;height:22.0pt'>\n" +
                        "  <td style='border:.5px #000' colspan=3 height=29 class='xl69' >Professeur :</td>\n" +
                        "  <td style='border:.5px #000' colspan=2 class='xl73'></td>\n" +
                        "  <td style='border:.5px #000' class='xl72' >De : <span\n" +
                        "  style='mso-spacerun:yes'>  </span>A : </td>\n" +
                        " </tr>\n" +
                        " <tr height=21 style='mso-height-source:userset;height:16.0pt'>\n" +
                        "  <td rowspan=81 height=170 class='xl70' width=87 style='border:.5px #000;height:129.0pt;\n" +
                        "  width:65pt;-webkit-transform: rotate(-90deg);\n" +
                        "    -moz-transform: rotate(-90deg);\n" +
                        "    -o-transform: rotate(-90deg);\n" +
                        "    -ms-transform: rotate(-90deg);\n" +
                        "    transform: rotate(-90deg);writing-mode: vertical-rl;'>Liste des étudiants de la filière: Génie\n" +
                        "  Informatique (G.I) - Groupe COURS<br></br>\n" +
                        "    1ére Année du Cycle  D.U.T - Année Universitaire : 2018 / 2019</td>\n" +
                        "  <td style='text-align: center;border:.5px #000' colspan=2 class='xl69' >N</td>\n" +
                        "  <td style='text-align: center;border:.5px #000' class='xl75' >Nom</td>\n" +
                        "  <td style='text-align: center;border:.5px #000' class='xl75' >Prénom</td>\n" +
                        "  <td style='border:.5px #000' class='xl69' >Emargement</td>\n" +
                        " </tr>";


        for (int i = 0; i < 80; i++) {


            if (!Students[i]) {


                html = html + "<tr height=21 style='mso-height-source:userset;height:16.0pt'>\n" +
                        "  <td style='border:.5px #000' colspan=2 height=21 class='xl80' >" + (i + 1) + "</td>\n" +
                        "  <td style='border:.5px #000' class=xl80 >" + First[i] + "<span\n" +
                        "  style='mso-spacerun:yes'> </span></td>\n" +
                        "  <td style='border:.5px #000' class='xl80' >" + Last[i] + "</td>\n" +
                        "  <td style='border:.5px #000' class='xl80' >Absent</td>\n" +
                        " </tr>";


                RTDB_Value = RTDB_Value + String.valueOf((i + 1)) + ",";

            }

            if (Students[i]) {


                html = html + "<tr height=21 style='mso-height-source:userset;height:16.0pt'>\n" +
                        "  <td style='border:.5px #000' colspan=2 height=21 class='xl78' >" + (i + 1) + "</td>\n" +
                        "  <td style='border:.5px #000' class='xl78' >" + First[i] + "<span\n" +
                        "  style='mso-spacerun:yes'> </span></td>\n" +
                        "  <td style='border:.5px #000' class='xl78' >" + Last[i] + "</td>\n" +
                        "  <td style='border:.5px #000' class='xl78' >Présent</td>\n" +
                        " </tr>";


            }


        }
/**<h1><center>" + "Présence Markée en :" + ourday + day + "/" + month + "/" + year + "vers" + hour + ":" + minute + "</center></h1>"
 +**/

        html = html +

                "<tr height=51 style='mso-height-source:userset;height:51.0pt'>" +
                "  <td style='border:.5px #000' colspan=4 rowspan=2  class='xl69' >Signature\n" +
                "  du professeur :</td>\n" +
                "  <td  style='border:.5px #000' colspan=2 rowspan=2 class='xl69'>" + prof + "</td>\n" +
                " </tr>\n" +


                "</table>\n" +
                "\n" +
                "</div>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String cur = sdf.format(new Date());


        String LN = "";
        if (Seance == 1) {
            LN = "08:30-10:30";
        }

        if (Seance == 2) {
            LN = "10:30-12:30";
        }

        if (Seance == 3) {
            LN = "14:30-16:30";
        }

        if (Seance == 4) {
            LN = "16:30-18:30";
        }


        //Here form the Pane Content


        ListScrl.setFitToWidth(true);


        Label Mtr = CreateLabel(34.0d, 156.0d, 8.0d, 26.0d, 24.0d, "#000000", "#ffffff", "Matière :");
        ListPane.getChildren().addAll(Mtr);

        Label Dt = CreateLabel(34.0d, 160.0d, 615.0d, 26.0d, 24.0d, "#000000", "#ffffff", "Date");
        ListPane.getChildren().addAll(Dt);

        Label DA = CreateLabel(34.0d, 160.0d, 615.0d, 59.0d, 24.0d, "#000000", "#ffffff", "De: A:");
        ListPane.getChildren().addAll(DA);

        Label Prf = CreateLabel(34.0d, 156.0d, 8.0d, 59.0d, 24.0d, "#000000", "#ffffff", "Professeur :");
        ListPane.getChildren().addAll(Prf);

        Label PrfNm = CreateLabel(34.0d, 453.0d, 163.0d, 59.0d, 24.0d, "#000000", "#ffffff", " ");
        ListPane.getChildren().addAll(PrfNm);

        Label MtrNm = CreateLabel(34.0d, 453.0d, 163.0d, 26.0d, 24.0d, "#000000", "#ffffff", " ");
        ListPane.getChildren().addAll(MtrNm);


        Label NNm = CreateLabel(34.0d, 43.0d, 121.0d, 92.0d, 24.0d, "#000000", "#ffffff", "N");
        ListPane.getChildren().addAll(NNm);


        Label PrnNm = CreateLabel(34.0d, 227.0d, 163.0d, 92.0d, 24.0d, "#000000", "#ffffff", "Prénom");
        ListPane.getChildren().addAll(PrnNm);


        Label NomNm = CreateLabel(34.0d, 227.0d, 389.0d, 92.0d, 24.0d, "#000000", "#ffffff", "Nom");
        ListPane.getChildren().addAll(NomNm);


        Label EmrgNm = CreateLabel(34.0d, 160.0d, 615.0d, 92.0d, 24.0d, "#000000", "#ffffff", "Emargement");
        ListPane.getChildren().addAll(EmrgNm);


        //Here For loop


        double Y = 92.0d;

        for (int i = 0; i < 80; i++) {
            Y += 33d;

            if (!Students[i]) {

                ListPane.getChildren().addAll(CreateLabel(34.0d, 43.0d, 121.0d, Y, 24.0d, "#000000", "#F9918F", String.valueOf(i + 1)));

                ListPane.getChildren().addAll(CreateLabel(34.0d, 227.0d, 163.0d, Y, 24.0d, "#000000", "#F9918F", Last[i]));

                ListPane.getChildren().addAll(CreateLabel(34.0d, 227.0d, 389.0d, Y, 24.0d, "#000000", "#F9918F", First[i]));

                ListPane.getChildren().addAll(CreateLabel(34.0d, 160.0d, 615.0d, Y, 24.0d, "#000000", "#F9918F", "Absent"));

            }

            if (Students[i]) {
                ListPane.getChildren().addAll(CreateLabel(34.0d, 43.0d, 121.0d, Y, 24.0d, "#000000", "#92D050", String.valueOf(i + 1)));

                ListPane.getChildren().addAll(CreateLabel(34.0d, 227.0d, 163.0d, Y, 24.0d, "#000000", "#92D050", Last[i]));

                ListPane.getChildren().addAll(CreateLabel(34.0d, 227.0d, 389.0d, Y, 24.0d, "#000000", "#92D050", First[i]));

                ListPane.getChildren().addAll(CreateLabel(34.0d, 160.0d, 615.0d, Y, 24.0d, "#000000", "#92D050", "Présent"));

            }


        }


        //END for loop


        Label Grp = CreateLabel(114.0, Y - 33d, -1284.0d, 1384.5d, 34.0d, "#000000", "#ffffff", " Liste des étudiants de la filière : Génie Informatique (G.I) - Groupe COURS 1ére Année du Cycle D.U.T - Année Universitaire : 2018 / 2019");
        Grp.setRotate(-90d);
        ListPane.getChildren().addAll(Grp);


        Label SignNm = CreateLabel(34.0d, 382.0d, 8.0d, Y + 33d, 24.0d, "#000000", "#ffffff", "Signature du professeur : ");
        ListPane.getChildren().addAll(SignNm);


        Label Sign = CreateLabel(34.0d, 386.0d, 389.0d, Y + 33d, 24.0d, "#000000", "#ffffff", " ");
        ListPane.getChildren().addAll(Sign);


        // WV.getEngine().load("file:///Users/yassine/Desktop/SPM/" + filepath);


        File dir = new File(Settings.DataPath + cur);
        dir.mkdirs();


        File dir2 = new File(Settings.DataPath + cur + "/PDF");
        dir2.mkdirs();


        File dir5 = new File(Settings.DataPath + cur + "/DB");
        dir5.mkdirs();


        RealtimeDatabase.SetDate("test-e27ad", "1VVe04KdzKlAPJgdksku8ovtIL4F8DQa9mfkUZHr", RTDB_Date, RTDB_Value);


        //End Of the  the Pane Content
        try {
            String k = html;
            OutputStream file = new FileOutputStream(new File(Settings.DataPath + cur + "/PDF/" + LN + ".pdf"));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(k.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        DBManager.OpenDataBase(Settings.DataPath + cur + "/DB/" + cur.replace("/", "-") + ".db");
        DBManager.CreateTableInDataBase(Settings.DataPath + cur + "/DB/" + cur.replace("/", "-") + ".db", "AbsentStudents",

                "(Numbers           TEXT    NOT NULL) "
        );


        String ASN = "";
        for (int i = 0; i < 80; i++) {


            if (!Students[i]) {

                ASN = ASN + String.valueOf(i + 1) + ",";
            }

        }
        char[] A = ASN.toCharArray();
        ASN = "";
        for (int j = 0; j < A.length - 1; j++) {
            ASN = ASN + String.valueOf(A[j]);

        }


        DBManager.InsertIntoTable(Settings.DataPath + cur + "/DB/" + cur.replace("/", "-") + ".db",
                "INSERT INTO AbsentStudents (Numbers) " +
                        "VALUES ('" + ASN + "');"
        );


        System.out.println(html);


        /** engine = WV.getEngine();
         String url = "file:///Users/yassine/Desktop/SPM/src/resources/web/viewer.html";


         engine.setJavaScriptEnabled(true);
         engine.load(url);

         engine.getLoadWorker()
         .stateProperty()
         .addListener((observable, oldValue, newValue) -> {
         // to debug JS code by showing console.log() calls in IDE console
         JSObject window = (JSObject) engine.executeScript("window");
         window.setMember("java", new Controller());


         // this pdf file will be opened on application startup
         if (newValue == Worker.State.SUCCEEDED) {

         }
         });**/


    }


    public void opppeen() {

        try {
            byte[] data = Files.readAllBytes(new File("/Users/yassine/Desktop/Test.pdf").toPath());
            String base64 = Base64.getEncoder().encodeToString(data);
            engine.executeScript("openFileFromBase64('" + base64 + "')");

            //System.out.println(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Exit() {
        System.exit(0);
    }


    public void Minimize() {
        Main.PS.setIconified(true);
    }


    public void LogIn() {

        if (UserNameTF.getText().equals("Admin") && PasswordTF.getText().equals("Admin")) {
            LoginErrorTF.setText("");
            Main.PS.close();
            Stage Stg = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("main.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene s = new Scene(root);
            s.setFill(Color.TRANSPARENT);
            s.getStylesheets().add("Style.css");
            Stg.setScene(s);
            Stg.setResizable(false);
            Stg.initStyle(StageStyle.TRANSPARENT);
            Stg.show();


        } else {

            LoginErrorTF.setText("An error occurred while signing in");


        }


    }


    public Label CreateLabel(double Height, double Width, double X, double Y, double FontSize, String Color, String BackgroundColor, String Text) {


        Label l = new Label();

        l.setPrefHeight(Height);
        l.setPrefWidth(Width);
        l.setLayoutX(X);
        l.setStyle("-fx-background-color:" + BackgroundColor + ";-fx-border-color: black;");
        l.setAlignment(Pos.CENTER);
        l.setTextFill(Paint.valueOf(Color));
        l.setLayoutY(Y);
        l.setFont(Font.font("American Typewriter", FontSize));
        l.setText(Text);


        return l;
    }


    public void StartEnrolement() {


        serialPort = new SerialPort("/dev/cu.LA_SALLE_T10-DevB");


        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);


        } catch (SerialPortException e) {
            e.printStackTrace();
        }


        while (true) {
            SendToPort("en", 2000);

            String S = ReadFromPort().trim();


            if (S.contains("p")) break;


        }


        EnrlMsg.setText("Enter The Student ID");


    }


    public void SendId() {

        while (true) {
            SendToPort(StudentID.getText(), 2000);

            String S = ReadFromPort();
            System.out.println(S);


            if (S.contains("W")) {


                break;

            }
        }


        Enrl = "Put Your finger on the Fingerprintsensor !";


        while (true) {


            String S = ReadFromPort().trim();
            //System.out.println(S);


            if (S.contains("v")) {
                break;
            }


        }

        Enrl = "Remove your finger !";


        while (true) {


            String S = ReadFromPort().trim();
            //System.out.println(S);


            if (S.contains("m")) {
                Enrl = "Put Again the same finger !";


                break;
            }
        }


        while (true) {


            String S = ReadFromPort().trim();
            //System.out.println(S);


            if (S.contains("t")) {
                Enrl = "Took !";


                break;
            }
        }


        while (true) {


            String S = ReadFromPort().trim();
            System.out.println(S);


            if (S.contains("S")) {
                Enrl = "The student fingerprint has been successfully Stored !";
                break;
            }


            if (S.contains("h")) {
                EnrlMsg.setText("Mich");

                break;
            }
        }


    }

    public String ReadFromPort() {
        String Read = null;

        try {


            byte[] b = serialPort.readBytes(serialPort.getInputBufferBytesCount());
            Read = new String(b, StandardCharsets.UTF_8);


            //System.out.println("\n\n\n\n" + q.length());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }


        return Read;
    }


    public void SendToPort(String Message, int Daly) {


        try {
            serialPort.writeBytes(Message.getBytes());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(Daly);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void OpenPDFfile() {


        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("/Users/yassine/Desktop/SPM/SpmData/" + RTDB_Date + "/PDF/08:30-10:30.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }


}
