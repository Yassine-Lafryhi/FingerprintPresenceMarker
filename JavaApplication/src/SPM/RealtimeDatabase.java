package SPM;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RealtimeDatabase {


    public static void SetDate(String DatabaseName, String Secret, String DataName, String DataValue) {
        try {
            // enter code here


            String[] arg = new String[]{"/bin/bash", "-c", "curl -X PATCH -d '{\n" +


                    "    \"" + DataName + "\": \"" + DataValue + "\"\n" +


                    "}' 'https://" + DatabaseName + ".firebaseio.com/" + "Content" + ".json?auth=" + Secret + "'"};
            //System.out.println(arg[2]);
            Process proc = new ProcessBuilder(arg).start();

            try (BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                String line;

                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

    }


    public static String GetData(String DatabaseName, String Secret, String DataName) {
        String Got = null;


        try {
            URL oracle = new URL("https://" + DatabaseName + ".firebaseio.com/" + "Content/" + DataName + ".json?auth=" + Secret + "");
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine, All = "";
            while ((inputLine = in.readLine()) != null)

                All = All + inputLine;
            in.close();

            Got = All.replace("\"", "");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return Got;
    }


}
