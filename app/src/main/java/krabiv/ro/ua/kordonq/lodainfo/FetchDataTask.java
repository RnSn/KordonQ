package krabiv.ro.ua.kordonq.lodainfo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URISyntaxException;

public class FetchDataTask extends AsyncTask<Void, Integer, String> {


    TextView tw;

    public FetchDataTask(TextView tw) {
        this.tw = tw;
    }

    @Override
    protected String doInBackground(final Void... params) {
        try {
            Q q = new Q();
            return q.lastUpdate();
        } catch (URISyntaxException | IOException e) {
            Log.e(Const.KORDON_Q, e.getMessage());
            return Q.DATE_UNKNOWN;
        }
    }


    @Override
    protected void onPostExecute(final String result) {
        tw.setText(result);
    }
}