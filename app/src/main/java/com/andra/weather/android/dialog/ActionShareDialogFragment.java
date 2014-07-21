package com.andra.weather.android.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.andra.weather.android.R;
import com.andra.weather.android.utility.Utils;

public class ActionShareDialogFragment extends DialogFragment {


    private String mSharedString;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mSharedString = getArguments().getString(Utils.ARG_SHARED_TEMP);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_share_dialog)
                .setItems(R.array.actions_share_dialog_items,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {

                                Toast.makeText(ActionShareDialogFragment.this.getActivity(),
                                        mSharedString,
                                        Toast.LENGTH_LONG).show();
                                switch (position) {
                                    case 0:
                                        // Share with any app that allows it
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(Intent.ACTION_SEND);
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, mSharedString);
                                        sendIntent.setType("text/plain");
                                        // Force the app chooser dialog
                                        startActivity(Intent.
                                                createChooser(sendIntent, "Share forecast to..."));
                                        break;
                                    case 1:
                                        // Share with an email app
                                        Intent sendMailIntent = new Intent();
                                        sendMailIntent.setAction(Intent.ACTION_SENDTO);
                                        sendMailIntent.setData(Uri.parse("mailto:" +
                                                "?subject=Weather Forecast" +
                                                "&body=" + mSharedString));
                                        // Force the app chooser dialog
                                        startActivity(Intent.
                                                createChooser(sendMailIntent, "Share forecast to..."));
                                        break;
                                    case 2:
                                        // Share with an SMS app
                                        Intent sendSMSIntent = new Intent();
                                        sendSMSIntent.setAction(Intent.ACTION_VIEW);
                                        sendSMSIntent.setData(Uri.parse("sms:"));
                                        sendSMSIntent.putExtra("sms_body", mSharedString);
                                        startActivity(sendSMSIntent);
                                        break;
                                }
                            }
                        }
                );
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
