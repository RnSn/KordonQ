package krabiv.ro.ua.kordonq.lodainfo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import krabiv.ro.ua.kordonq.R;

public class FetchDataTask extends AsyncTask<Void, Integer, List<Q>> {

    private TextView tw;
    private AfterRequest ar;

    public FetchDataTask(TextView tw) {
        this.tw = tw;
    }

    public FetchDataTask(AfterRequest ar) {
        this.ar = ar;
    }

    @Override
    protected List<Q> doInBackground(final Void... params) {
        List<Q> result = new ArrayList<>();
        try {
            Request req = new Request();
            LinkedHashMap<String, List<String>> queues = req.queues();
            result.add(new Q(R.id.rrhQ, queues.get("Рава-Руська - Хребенне")
                .get(0)));
            result.add(new Q(R.id.gbQ, queues.get("Грушів - Будомеж")
                .get(0)));
            result.add(new Q(R.id.kkQ, queues.get("Краковець - Корчова")
                .get(0)));
            result.add(new Q(R.id.shmQ, queues.get("Шегині - медика")
                .get(0)));
        } catch (IOException e) {
            Log.e(Const.KORDON_Q, e.getMessage());
        }
        return result;
    }


    @Override
    protected void onPostExecute(final List<Q> result) {
        ar.call(result);
    }
}