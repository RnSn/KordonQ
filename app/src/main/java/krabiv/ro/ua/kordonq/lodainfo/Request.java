package krabiv.ro.ua.kordonq.lodainfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Request implements Const {

    public static final String DIV_RAMKA = "div.vn_ramka";
    public static final String TD_NUMBER = "td.number";

    private DateFormat inputFormat;
    private DateFormat outputFormat;
    private Document document;

    private static final String Q_SRC = "http://www.loda.gov" +
        ".ua/karta_kordon/index.php";

    public Request() throws IOException {
        document = Jsoup.connect(Q_SRC).get();
        inputFormat = new SimpleDateFormat(DF_FORMAT_INPUT);
        outputFormat = new SimpleDateFormat(DF_FORMAT_OUTPUT);
    }


    public String lastUpdate() {
        String[] text = document.getElementById(Const.ID_GODYNA).text().split("\\s+");
        for (int i = 0; i < text.length; i++) {
            if (timeDateStart(text, i)) {
                return formatted(text[i] + " " + text[i + 1]);
            }
        }

        return Const.DATE_UNKNOWN;
    }

    public LinkedHashMap<String, List<String>> queues() {
        LinkedHashMap<String, List<String>> result = new LinkedHashMap<>();
        Elements spots = document.select(DIV_RAMKA);
        for (Element div : spots) {
            Element span = div.children().first();
            String crossing = span.text();

            Element table = span.nextElementSibling();
            Elements tds = table.select(TD_NUMBER);

            List<String> numbers = new ArrayList<>(tds.size());
            for (Element td : tds) {
                numbers.add(td.children().first().text());
            }
            result.put(crossing, numbers);
        }

        return result;
    }

    private boolean isDigit(final String str) {
        return str != null
            && str.length() > 0
            && Character.isDigit(str.charAt(0));
    }

    private boolean timeDateStart(String[] text, int i) {
        return isDigit(text[i]) && text.length > (i + 1);
    }

    private String formatted(String timeDate) {
        try {
            return outputFormat.format(inputFormat.parse(timeDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return Const.DATE_UNKNOWN;
        }
    }
}
