package hu.bme.aut.amorg.examples.servicedemo.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.StatFs;

public class IntentServiceFileSystemStats extends IntentService {

    public static final String KEY_MESSENGER = "KEY_MESSENGER";

    public IntentServiceFileSystemStats() {
        super("IntentServiceFileSystemStats");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long freeSpace = getFreeSpace();
        sendResultFreeSpace(intent, freeSpace);
    }

    private void sendResultFreeSpace(Intent intent, long freeSpace) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Messenger messenger = (Messenger) extras.get(KEY_MESSENGER);
            Message msg = Message.obtain();
            msg.arg1 = Activity.RESULT_OK;
            msg.obj = new Long(freeSpace);
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public long getFreeSpace() {
        StatFs statFs = new StatFs(
                Environment.getExternalStorageDirectory().getAbsolutePath()
        );
        statFs.restat(Environment.getExternalStorageDirectory().getAbsolutePath());
        long available = ((long)statFs.getAvailableBlocks() * (long)statFs.getBlockSize());
        return available/1024/1024;
    }
}