package com.example.hd.rizolve;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class add_complaint extends AppCompatActivity {

    EditText title1;
    EditText description1;
    int complaint_type=0;
    int visibilityStudent=0;
    int visibilityProf=0;
    int resolve_category=0;

    static String serverAddress;
    static RequestQueue myQueue;
    final int duration = Toast.LENGTH_LONG;

    Globals global;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        global = ((Globals) this.getApplication());
        serverAddress = global.getServerAddress();
        myQueue = global.getVolleyQueue();
        context = this;

        title1 = (EditText) findViewById(R.id.complaint_title);
        description1 = (EditText) findViewById(R.id.complaint_description);
        final LinearLayout layout1 = (LinearLayout) findViewById(R.id.decide_individual);
        final LinearLayout layout3 = (LinearLayout) findViewById(R.id.decide_post_to);
        final RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.decide_insti1);
        final RadioButton button1 = (RadioButton) findViewById(R.id.student_radio_button);
        final RadioButton button2 = (RadioButton) findViewById(R.id.faculty_radio_button);
        final RadioButton button3 = (RadioButton) findViewById(R.id.individual_radio_button);
        final RadioButton button4 = (RadioButton) findViewById(R.id.hostel_radio_button);
        final RadioButton button5 = (RadioButton) findViewById(R.id.insti_radio_button);
        final RadioButton button6 = (RadioButton) findViewById(R.id.mess_individual);
        final RadioButton button7 = (RadioButton) findViewById(R.id.main_individual);
        final RadioButton button8 = (RadioButton) findViewById(R.id.dean1);
        final RadioButton button9 = (RadioButton) findViewById(R.id.dean2);
        final RadioButton button10 = (RadioButton) findViewById(R.id.dean3);
        final RadioButton button11 = (RadioButton) findViewById(R.id.dean4);
        final RadioButton button12 = (RadioButton) findViewById(R.id.dean5);
        final RadioButton button13 = (RadioButton) findViewById(R.id.dean6);
        final Button post_button = (Button) findViewById(R.id.complaint_post);

        /*
        if((button3.isChecked()==false && button4.isChecked()==false&& button5.isChecked()==false)||(title1.getText().toString().length()==0 ||description1.getText().toString().length()==0)||(button1.isChecked()==false && button2.isChecked()==false)){

            post_button.setClickable(false);

        }
        */



        View.OnClickListener third_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button4.setChecked(false);
                button5.setChecked(false);
                layout1.setVisibility(View.VISIBLE);
                complaint_type = 0;
                layout2.setVisibility(View.INVISIBLE);
                layout3.setVisibility(View.INVISIBLE);

            }
        };
        View.OnClickListener fourth_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button3.setChecked(false);
                button5.setChecked(false);
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.INVISIBLE);
                layout3.setVisibility(View.INVISIBLE);
                complaint_type = 1;
                resolve_category = 0;
            }
        };
        View.OnClickListener fifth_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button3.setChecked(false);
                button4.setChecked(false);
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
                complaint_type = 2;
            }
        };

        View.OnClickListener first_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                visibilityStudent =1;
            }
        };

        View.OnClickListener second_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                visibilityProf=1;
            }
        };

        View.OnClickListener six_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
               resolve_category =0;
                button7.setChecked(false);
            }
        };

        View.OnClickListener seven_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button6.setChecked(false);
                resolve_category = 1;
            }
        };
        View.OnClickListener eight_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {

                button9.setChecked(false);
                button10.setChecked(false);
                button11.setChecked(false);
                button12.setChecked(false);
                button13.setChecked(false);
                resolve_category = 1;
            }
        };
        View.OnClickListener nine_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button8.setChecked(false);

                button10.setChecked(false);
                button11.setChecked(false);
                button12.setChecked(false);
                button13.setChecked(false);
                resolve_category = 2;
            }
        };
        View.OnClickListener ten_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button8.setChecked(false);
                button9.setChecked(false);

                button11.setChecked(false);
                button12.setChecked(false);
                button13.setChecked(false);
                resolve_category = 3;
            }
        };
        View.OnClickListener eleven_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button8.setChecked(false);
                button9.setChecked(false);
                button10.setChecked(false);

                button12.setChecked(false);
                button13.setChecked(false);
                resolve_category = 4;
            }
        };
        View.OnClickListener twelve_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button8.setChecked(false);
                button9.setChecked(false);
                button10.setChecked(false);
                button11.setChecked(false);

                button13.setChecked(false);
                resolve_category = 5;
            }
        };
        View.OnClickListener thirteen_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                button8.setChecked(false);
                button9.setChecked(false);
                button10.setChecked(false);
                button11.setChecked(false);
                button12.setChecked(false);

                resolve_category = 6;
            }
        };


        button1.setOnClickListener(first_radio_listener);
        button2.setOnClickListener(second_radio_listener);
        button3.setOnClickListener(third_radio_listener);
        button4.setOnClickListener(fourth_radio_listener);
        button5.setOnClickListener(fifth_radio_listener);
        button6.setOnClickListener(six_radio_listener);
        button7.setOnClickListener(seven_radio_listener);
        button8.setOnClickListener(eight_radio_listener);
        button9.setOnClickListener(nine_radio_listener);
        button10.setOnClickListener(ten_radio_listener);
        button11.setOnClickListener(eleven_radio_listener);
        button12.setOnClickListener(twelve_radio_listener);
        button13.setOnClickListener(thirteen_radio_listener);

    }

    public void post_complaint(final View view){


        String title = title1.getText().toString().replaceAll("\\s+", "%20");
        String description = description1.getText().toString().replaceAll("\\s+","%20");
        String url_resolving = serverAddress.concat("/complaint/post_complaint.json?complaint_type=")
                .concat(String.valueOf(complaint_type)).concat("&complaint=")
                .concat(description).concat("&visibilityStudent=").concat(String.valueOf(visibilityStudent))
                .concat("&visibilityProf=").concat(String.valueOf(visibilityProf))
                .concat("&resolve_category=").concat(String.valueOf(resolve_category))
                .concat("&complaint_title=").concat(title);

        Log.i("haggax",url_resolving);
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url_resolving,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast toast = Toast.makeText(context, "Complaint is registered", duration);
                toast.show();

                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                toast.show();
            }
        }) ;

        myQueue.add(request1);

    }

}
