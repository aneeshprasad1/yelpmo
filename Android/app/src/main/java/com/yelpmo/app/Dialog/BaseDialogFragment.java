package com.yelpmo.app.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
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

    public static BaseDialogFragment showDialog(FragmentManager fragmentManager,
           String title, String message, String buttonText, DialogInterface.OnClickListener buttonClickListener) {
        BaseDialogFragment fragment = newInstanceOneButton(title, message, buttonText, buttonClickListener);
        fragment.show(fragmentManager, title);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("message"))
                .setNeutralButton("buttonText", onButtonClickListener);
        return builder.create();
    }
}
