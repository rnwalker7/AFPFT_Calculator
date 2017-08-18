package com.rnwalker7.fit2fight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<AgeGroups> ageGroupsList;
    private int ageGroup = 0;
    private List<HiAltGroups> hiAltList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rnwalker7.fit2fight.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.rnwalker7.fit2fight.R.id.toolbar);
        setSupportActionBar(toolbar);


        // UI objects
        final Button pickTest = (Button) findViewById(com.rnwalker7.fit2fight.R.id.btn_select_tester);
        final Spinner gender_picker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.gender_picker);
        final Spinner age_picker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.age_picker);
        final Button calcResult = (Button) findViewById(com.rnwalker7.fit2fight.R.id.btn_score_test);
        final Button resetTest = (Button) findViewById(com.rnwalker7.fit2fight.R.id.btn_reset);
        final CheckBox chkHiAlt = (CheckBox) findViewById(com.rnwalker7.fit2fight.R.id.alt_check);

        // Read in age group scores from XML

        AgeXMLParser myParser = new AgeXMLParser();

        try {
            InputStream is = getAssets().open("scores.xml", AssetManager.ACCESS_STREAMING);
            ageGroupsList = myParser.parse(is);
            is.close();
        }
        catch(IOException e) {
            Log.e("PTTestCalculator", "exception", e);
        }

        // Which age group are we working with?

        pickTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (gender_picker.getSelectedItemPosition() == 1)
                    switch (age_picker.getSelectedItemPosition()) {
                        case 1: ageGroup = 6; break;
                        case 2: ageGroup = 7; break;
                        case 3: ageGroup = 8; break;
                        case 4: ageGroup = 9; break;
                        default: ageGroup = 5;
                    }
                else
                    switch (age_picker.getSelectedItemPosition()) {
                        case 1: ageGroup = 1; break;
                        case 2: ageGroup = 2; break;
                        case 3: ageGroup = 3; break;
                        case 4: ageGroup = 4; break;
                        default: ageGroup = 0;
                    }

                loadPickers(ageGroup);
                showFields();

        }});

        chkHiAlt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Spinner hiAltPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.hi_alt_picker);

                if(isChecked)
                    hiAltPicker.setVisibility(View.VISIBLE);
                else
                    hiAltPicker.setVisibility(View.INVISIBLE);
            }

        });

        calcResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calcTotalScore(ageGroup);
            }
        });

        resetTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideFields();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.rnwalker7.fit2fight.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.rnwalker7.fit2fight.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadPickers(int myAge) {
        Spinner ACPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.AC_picker);
        Spinner PUPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.PU_picker);
        Spinner CHPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.CH_picker);

        AgeGroups tempGroup = ageGroupsList.get(myAge);

        String ACArray[] = new String[11];
        String PUArray[] = new String[tempGroup.getPushupRecs()];
        String CHArray[] = new String[tempGroup.getCrunchRecs()];

        for (int x = 0; x < 11; x++)
            ACArray[x] = tempGroup.getAbCircString(x);
        for (int x = 0; x < tempGroup.getPushupRecs(); x++)
            PUArray[x] = tempGroup.getPushupString(x);
        for (int x = 0; x < tempGroup.getCrunchRecs(); x++)
            CHArray[x] = tempGroup.getCrunchString(x);

        ArrayAdapter<String> ACAdapter = new ArrayAdapter<String>(this, com.rnwalker7.fit2fight.R.layout.support_simple_spinner_dropdown_item, ACArray);
        ACPicker.setAdapter(ACAdapter);

        ArrayAdapter<String> PUAdapter = new ArrayAdapter<String>(this, com.rnwalker7.fit2fight.R.layout.support_simple_spinner_dropdown_item, PUArray);
        PUPicker.setAdapter(PUAdapter);

        ArrayAdapter<String> CHAdapter = new ArrayAdapter<String>(this, com.rnwalker7.fit2fight.R.layout.support_simple_spinner_dropdown_item, CHArray);
        CHPicker.setAdapter(CHAdapter);

    }

    public void calcTotalScore(int myAge) {
        int maxPts = 0;
        double runningTotal = 0;
        boolean componentFail = false;
        HiAltXMLParser myParser = new HiAltXMLParser();

        AgeGroups tempGroup = ageGroupsList.get(myAge);

        Spinner ACPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.AC_picker);
        Spinner PUPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.PU_picker);
        Spinner CHPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.CH_picker);
        EditText runTime = (EditText) findViewById(com.rnwalker7.fit2fight.R.id.input_run);
        CheckBox walkTest = (CheckBox) findViewById(com.rnwalker7.fit2fight.R.id.walk_check);
        CheckBox runExempt = (CheckBox) findViewById(com.rnwalker7.fit2fight.R.id.chk_run_exempt);
        CheckBox hiAlt = (CheckBox) findViewById(com.rnwalker7.fit2fight.R.id.alt_check);
        Spinner hiAltPicker = (Spinner) findViewById(com.rnwalker7.fit2fight.R.id.hi_alt_picker);

        double ACPts = tempGroup.getAbCircPts(ACPicker.getSelectedItemPosition());
        double PUPts = tempGroup.getPushupPts(PUPicker.getSelectedItemPosition());
        double CHPts = tempGroup.getCrunchPts(CHPicker.getSelectedItemPosition());
        String myTime = runTime.getText().toString();

        try {
            InputStream is = getAssets().open("high-altitude.xml", AssetManager.ACCESS_STREAMING);
            hiAltList = myParser.parse(is);
            is.close();
        } catch(IOException e) {
            showDialog("Unable to load high altitude data");
            return;
        }
        if (ACPts > -1) {
            maxPts += 20;
            runningTotal += ACPts;
            if (ACPts == 0)
                componentFail = true;
        }

        if (PUPts > -1) {
            maxPts += 10;
            runningTotal += PUPts;
            if (PUPts == 0)
                componentFail = true;
        }

        if (CHPts > -1) {
            maxPts += 10;
            runningTotal += CHPts;
            if (CHPts == 0)
                componentFail = true;
        }

        if (!(runExempt.isChecked())) {
            int nRunTime = 0;

            String mmss[] = myTime.split("[:]");
            try {
                nRunTime = Integer.parseInt(mmss[1]);
                if (nRunTime >= 60)
                    return;
                nRunTime += Integer.parseInt(mmss[0]) * 60;
            } catch(Exception e) {
               // Throw exception code here
                String alertMsg = "You have entered an invalid time. Please try again!";
                showDialog(alertMsg);
                return;
            }

            // Check for high altitude adjustment

            if (hiAlt.isChecked()) {
                HiAltGroups tempHiAlt = hiAltList.get(hiAltPicker.getSelectedItemPosition());

                int x = 0;
                if(walkTest.isChecked()) {

                } else {
                    while ((nRunTime < tempHiAlt.getMaxTime(x)) && (x < tempHiAlt.getAltRecs())) x++;
                    nRunTime = nRunTime - tempHiAlt.getAltAdj(x);
                }
            }

            if (!(walkTest.isChecked())) {
                maxPts += 60;
                int x = 0;

                while (nRunTime > tempGroup.getMaxTime(x))
                    x++;



                runningTotal += tempGroup.getRunPts(x);
            } else {
                if (nRunTime > tempGroup.getWalkTime())
                    componentFail = true;
            }

        }

        Double finalScore = (runningTotal / maxPts) * 100;

        String alertMsg = "You have scored a " + finalScore + " on your test.";

        if (componentFail)
            alertMsg += " However, you have failed a component on your test, therefore your overall score is unsatisfactory.";

        showDialog(alertMsg);

    }

    private void showDialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, com.rnwalker7.fit2fight.R.style.Base_Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Results");
        builder.setMessage(msg);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.create();
        builder.show();

    }

    public void hideFields() {
        TableRow rowAC = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_abs);
        TableRow rowLabel1 = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_Label1);
        TableRow rowPUCH = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_PU_CH);
        TableRow rowLabel2 = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_Label2);
        TableRow rowRun = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_run);
        TableRow rowHiAlt = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_hi_alt);
        TableRow rowSubmit = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_submit);

        rowAC.setVisibility(View.INVISIBLE);
        rowLabel1.setVisibility(View.INVISIBLE);
        rowPUCH.setVisibility(View.INVISIBLE);
        rowLabel2.setVisibility(View.INVISIBLE);
        rowRun.setVisibility(View.INVISIBLE);
        rowHiAlt.setVisibility(View.INVISIBLE);
        rowSubmit.setVisibility(View.INVISIBLE);

    }

    public void showFields() {
        TableRow rowAC = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_abs);
        TableRow rowLabel1 = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_Label1);
        TableRow rowPUCH = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_PU_CH);
        TableRow rowLabel2 = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_Label2);
        TableRow rowRun = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_run);
        TableRow rowHiAlt = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_hi_alt);
        TableRow rowSubmit = (TableRow) findViewById(com.rnwalker7.fit2fight.R.id.row_submit);

        rowAC.setVisibility(View.VISIBLE);
        rowLabel1.setVisibility(View.VISIBLE);
        rowPUCH.setVisibility(View.VISIBLE);
        rowLabel2.setVisibility(View.VISIBLE);
        rowRun.setVisibility(View.VISIBLE);
        rowHiAlt.setVisibility(View.VISIBLE);
        rowSubmit.setVisibility(View.VISIBLE);

    }

}
