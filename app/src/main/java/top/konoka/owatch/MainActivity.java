package top.konoka.owatch;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RefreshDataTask refreshDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        refreshDataTask = new RefreshDataTask(findViewById(R.id.textView_LastUpdated),
                findViewById(R.id.textView_tdata), findViewById(R.id.textView_hdata));
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshDataTask.startTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        refreshDataTask.stopTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshDataTask.stopTask();
    }
}
