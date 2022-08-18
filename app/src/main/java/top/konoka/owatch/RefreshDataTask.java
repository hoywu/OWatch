package top.konoka.owatch;

import android.os.AsyncTask;
import android.widget.TextView;

public class RefreshDataTask {
    private NetworkGetData networkGetData = new NetworkGetData(PrivateConstants.dataURL);
    private RefreshDataAsyncTask task;
    private boolean refreshData = true;
    private MyData returnData;
    private TextView lastUpdatedTextView;
    private TextView temperatureTextView;
    private TextView humidityTextView;

    public RefreshDataTask(TextView lastUpdatedTextView, TextView temperatureTextView, TextView humidityTextView) {
        this.lastUpdatedTextView = lastUpdatedTextView;
        this.temperatureTextView = temperatureTextView;
        this.humidityTextView = humidityTextView;
    }

    public void startTask() {
        stopTask();
        refreshData = true;
        task = (RefreshDataAsyncTask) new RefreshDataAsyncTask().execute();
    }

    public void stopTask() {
        if (task != null) {
            refreshData = false;
            task.cancel(true);
            task = null;
        }
    }

    private class RefreshDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (refreshData) {
                returnData = new MyData(networkGetData.getHtmlData());
                publishProgress();
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            lastUpdatedTextView.setText(returnData.getLastUpdated());
            temperatureTextView.setText(String.format("%s℃", returnData.getTemperature()));
            humidityTextView.setText(String.format("%s%%", returnData.getHumidity()));
        }
    }

}
