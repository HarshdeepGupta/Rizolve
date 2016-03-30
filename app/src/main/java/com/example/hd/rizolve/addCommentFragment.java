package com.example.hd.rizolve;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class addCommentFragment extends android.support.v4.app.DialogFragment implements TextView.OnEditorActionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText mEditText;

    public addCommentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static addCommentFragment newInstance() {
        addCommentFragment fragment = new addCommentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_comment, container, false);

        mEditText = (EditText) view.findViewById(R.id.add_comment);
        getDialog().setTitle("Comment");
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
        return view;
    }




    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            ComplaintsActivity activity = (ComplaintsActivity) getActivity();
            activity.onFinishEditDialog(mEditText.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }

}
