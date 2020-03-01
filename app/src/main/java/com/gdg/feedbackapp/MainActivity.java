package com.gdg.feedbackapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.service.autofill.Validator;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button submitBtn;
    EditText nameEdt;
    RatingBar rb;
    Spinner qualificationSpn;
    RadioButton studentRB,profRB;
    EditText suggestionET;
    SeekBar ageSB;
    CheckBox agreeCB;
    ArrayList<GDGFeedback> gList;
    TextView textview;
    RadioGroup mRgAllButtons;
    SharedPreferences mypref = null;
    DBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        nameEdt = (EditText)findViewById(R.id.nameEdt);
        setSupportActionBar(toolbar);

        submitBtn = (Button)findViewById(R.id.submitBtn);

        rb = (RatingBar)findViewById(R.id.feedbackRB);
        qualificationSpn = (Spinner)findViewById(R.id.qualificationSpn);
        studentRB =(RadioButton)findViewById(R.id.studentRB);
        profRB=(RadioButton)findViewById(R.id.profRB);
        suggestionET=(EditText) findViewById(R.id.suggestionET);
        ageSB=(SeekBar)findViewById(R.id.ageSB);
        agreeCB=(CheckBox)findViewById(R.id.consentCB);
        textview=(TextView)findViewById(R.id.ageText);
        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.myLayout);


        mypref = getSharedPreferences("settings", MODE_PRIVATE);

        //create object of database helper class
        dbHelper = new DBHelper(this);

        ageSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textview.setText("Age: " + i+" years");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        studentRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myLayout.removeAllViewsInLayout();

                //setting linear layout
                LinearLayout.LayoutParams params = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);

                //create textview
                TextView institute = new TextView(MainActivity.this);
                institute.setText("Institute Name *");
                institute.setTextSize(20);
                institute.setTextSize(20);
                institute.setLayoutParams(params);
                ll.addView(institute);

                EditText instit = new EditText(MainActivity.this);
                instit.setHint("Enter institute name");
                instit.setLayoutParams(params);
                ll.addView(instit);

                TextView yearText = new TextView(MainActivity.this);
                yearText.setText("Year of Study *");
                yearText.setTextSize(20);
                yearText.setTextSize(20);
                yearText.setLayoutParams(params);
                ll.addView(yearText);

                EditText year = new EditText(MainActivity.this);
                year.setHint("Enter Year of Study");
                year.setLayoutParams(params);
                ll.addView(year);

                TextView mobileText = new TextView(MainActivity.this);
                mobileText.setText("Mobile Number *");
                mobileText.setTextSize(20);
                mobileText.setTextSize(20);
                mobileText.setLayoutParams(params);
                ll.addView(mobileText);

                EditText mobile = new EditText(MainActivity.this);
                mobile.setHint("Enter your mobile number");
                mobile.setLayoutParams(params);
                ll.addView(mobile);

                myLayout.addView(ll);

            }
        });

        profRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLayout.removeAllViewsInLayout();

                LinearLayout.LayoutParams params = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);

                //create textview
                TextView orgtext = new TextView(MainActivity.this);
                orgtext.setText("Organization Name *");
                orgtext.setTextSize(20);
                orgtext.setTextSize(20);
                orgtext.setLayoutParams(params);
                ll.addView(orgtext);

                EditText org = new EditText(MainActivity.this);
                org.setHint("Enter organization name");
                org.setLayoutParams(params);
                ll.addView(org);

                TextView roletext = new TextView(MainActivity.this);
                roletext.setText("Organization Name *");
                roletext.setTextSize(20);
                roletext.setTextSize(20);
                roletext.setLayoutParams(params);
                ll.addView(roletext);

                EditText role = new EditText(MainActivity.this);
                role.setHint("Enter organization name");
                role.setLayoutParams(params);
                ll.addView(role);

                TextView exptext = new TextView(MainActivity.this);
                exptext.setText("Experience in years *");
                exptext.setTextSize(20);
                exptext.setTextSize(20);
                exptext.setLayoutParams(params);
                ll.addView(exptext);

                EditText year = new EditText(MainActivity.this);
                year.setHint("Enter years of experience");
                year.setLayoutParams(params);
                ll.addView(year);

                TextView mobileText = new TextView(MainActivity.this);
                mobileText.setText("Mobile Number *");
                mobileText.setTextSize(20);
                mobileText.setTextSize(20);
                mobileText.setLayoutParams(params);
                ll.addView(mobileText);

                EditText mobile = new EditText(MainActivity.this);
                mobile.setHint("Enter your mobile number");
                mobile.setLayoutParams(params);
                ll.addView(mobile);

                myLayout.addView(ll);

            }
        });

    submitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            String name = nameEdt.getText().toString();
            String suggestion = suggestionET.getText().toString();
            String qualification =qualificationSpn.getSelectedItem().toString();
            String occupation=null;
            if(studentRB.isChecked()){
                occupation="Student";
            }

            if(profRB.isChecked()){
                occupation="Professional";
            }
            int age = ageSB.getProgress();
            boolean isAgree = agreeCB.isChecked();
            int rating = rb.getProgress();

          GDGFeedback gf = new GDGFeedback(name,occupation,rating,qualification,suggestion,age,isAgree);
          if(dbHelper.insertFeedback(gf)){
              Toast.makeText(MainActivity.this, "inserted successfully", Toast.LENGTH_SHORT).show();
          }
          else{
              Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
          }
          SharedPreferences.Editor editor = mypref.edit();


          editor.putString("name", name);
          editor.putString("occupation", occupation);
          editor.putInt("rating", rating);
          editor.putString("qualification", qualification);
          editor.putString("suggestion", suggestion);
          editor.putInt("age", age);
          editor.commit();

            Intent i = new Intent(MainActivity.this,ThankYouActivity.class);
            i.putExtra("name",nameEdt.getText().toString());
            i.putExtra("feedback",gf);
            startActivity(i);

        }
    });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
