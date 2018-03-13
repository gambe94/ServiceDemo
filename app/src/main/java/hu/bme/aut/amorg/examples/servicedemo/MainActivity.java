package hu.bme.aut.amorg.examples.servicedemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import hu.bme.aut.amorg.examples.servicedemo.service.IntentServiceFileSystemStats;

public class MainActivity extends AppCompatActivity {

    private Handler handlerFreeSpaceIntentService = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == RESULT_OK) {
                long freeMB = (Long) msg.obj;
                long freeGB = freeMB / 1024;
                Toast.makeText(MainActivity.this, getString(R.string.txt_free_space, freeMB, freeGB), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Messenger freeSpaceMessenger = new Messenger(handlerFreeSpaceIntentService);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layoutContainer, new LocationDashboardFragment())
                    .commit();
        }


        SettingsActivity.startServiceWhenEnabled(PreferenceManager.getDefaultSharedPreferences(this), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_free_space:
                Intent intentStartService = new Intent(MainActivity.this,IntentServiceFileSystemStats.class);
                intentStartService.putExtra(IntentServiceFileSystemStats.KEY_MESSENGER, freeSpaceMessenger);
                startService(intentStartService);
                break;
            case R.id.action_settings:
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                intentSettings.putExtra(SettingsActivity.EXTRA_SHOW_FRAGMENT,
                        SettingsActivity.FragmentSettingsBasic.class.getName());
                intentSettings.putExtra(SettingsActivity.EXTRA_NO_HEADERS, true);
                startActivity(intentSettings);
                break;
        }

        return true;
    }
}
