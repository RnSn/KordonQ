package krabiv.ro.ua.kordonq.lodainfo;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Q implements Const {

    List<String> content;
    DateFormat df;

    private static final String Q_SRC = "http://www.loda.gov" +
        ".ua/karta_kordon/index.php";

    public Q() throws URISyntaxException, IOException {
        content = content();
        // 22.00 16.03.2016
        df = new SimpleDateFormat(DF_FORMAT);
    }

    private List<String> content() throws IOException, URISyntaxException {
        List<String> res = new ArrayList<>();
        HttpURLConnection conn = connection();

        try (BufferedReader br = reader(conn)) {
            String s;
            while ((s = br.readLine()) != null) {
                res.add(s);
            }
        } catch (IOException e) {
            Log.e(Const.KORDON_Q, e.getMessage(), e);
        }
        conn.disconnect();

        return res;
    }

    @NonNull
    private BufferedReader reader(final HttpURLConnection conn) throws IOException {
        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    private HttpURLConnection connection() throws IOException, URISyntaxException {
        return (HttpURLConnection) new URI(Q_SRC).toURL().openConnection();
    }

    public String lastUpdate() {
        for (String s : content) {
            if (s.contains(ID_GODYNA)) {
                String[] split = s.split("\\s+");
                for (int i = 0; i < split.length; i++) {
                    if (isDigit(split[i])) {
                        return split[i] + " " + split[i + 1];
                    }
                }
            }
        }
        return DATE_UNKNOWN;
    }

    private boolean isDigit(final String str) {
        return str != null
            && str.length() > 0
            && Character.isDigit(str.charAt(0));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Q q = new Q();
        String lu = q.lastUpdate();
        System.out.println(lu);
    }
}
