package com.yelpmo.app.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Corey on 10/5/14.
 */
public class BaseDialogFragment extends DialogFragment {

    private static DialogInterface.OnClickListener onButtonClickListener;

    public static BaseDialogFragment newInstanceOneButton(String title, String message, String buttonText,
                      DialogInterface.OnClickListener buttonClickListener) {
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        arguments.putString("message", message);
        arguments.putString("buttonText", buttonText);
        onButtonClickListener = buttonClickListener;
        BaseDialogFragment newFrag = new BaseDialogFragment();
        newFrag.setArguments(arguments);
        return newFrag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
