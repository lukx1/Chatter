package night.legacy.org.chatterandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;


public class PopupHandler {


    public PopupHandler()
    {

    }

    private void runOnUiThread(Runnable r)
    {
        new Handler(Looper.getMainLooper()).post(r);
    }

    public void ShowDialog(Activity activity, String message)
    {
        final Dialog diag = GetAlertOK(activity,message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                diag.show();
            }
        });
    }

    public Dialog GetAlertOK(Activity activity, String message )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
