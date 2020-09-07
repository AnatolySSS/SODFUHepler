package com.scorp.sodfuhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scorp.sodfuhelper.data.DBHelper;
import static com.scorp.sodfuhelper.data.Contract.Entry.*;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    static String UDOV = "УДОВЛЕТВОРЕНИЕ";
    static String OTK = "ОТКАЗ";

    float x1, x2;

    TextView dateOfNTE;
    Calendar dateAndTime = Calendar.getInstance();
    Button checkbtn;
    TextView answer, answerDesicion, answerBase, answerSumm;


    int valueOfnte;

    RadioGroup osagoCasco;
    RadioButton osagoCascoBtn;
    String osagoCascoBtnStr;

    RadioGroup approveReject;
    RadioButton approveRejectBtn;
    String approveRejectBtnStr;

    RadioGroup Type;
    RadioButton TypeBtn;
    String TypeBtnStr;

    RadioGroup Docs;
    RadioButton DocsBtn;
    String DocsBtnStr;

    RadioGroup DocsQuality;
    RadioButton DocsQualityBtn;
    String DocsQualityBtnStr;

    RadioGroup DecisionValue;
    RadioButton DecisionValueBtn;
    String DecisionValueBtnStr;

    Spinner spinner;

    String selectedRegion;
    String[] allRegions;

    public int selectedValueReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateOfNTE = findViewById(R.id.setDate);
        checkbtn = findViewById(R.id.checkbtn);
        answer = findViewById(R.id.answer);
        answerDesicion = findViewById(R.id.answerDesicion);
        answerBase = findViewById(R.id.answerBase);
        answerSumm = findViewById(R.id.answerSumm);
        osagoCasco = findViewById(R.id.osagoCasco);
        approveReject = findViewById(R.id.approveReject);
        Type = findViewById(R.id.Type);
        Docs = findViewById(R.id.Docs);
        DocsQuality = findViewById(R.id.DocsQuality);
        DecisionValue = findViewById(R.id.DecisionValue);

        //Create a database
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        String[] projection  = {
                CULUMN_REGION
        };

        cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        allRegions = new String[cursor.getCount()];
        int i =0;
        while (cursor.moveToNext()){

            int columnIndex = cursor.getColumnIndex(CULUMN_REGION);
            allRegions[i] = cursor.getString(columnIndex);
            i++;
        }
        cursor.close();

        //create a list of regions
        spinner = findViewById(R.id.regions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allRegions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Calendar c01062019 = Calendar.getInstance();
        c01062019.set(2019,5,1,0,0,0);

        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    displayDatabaseInfo();
                    isChecked();

                    osagoCascoBtn = (RadioButton) findViewById(osagoCasco.getCheckedRadioButtonId());
                    osagoCascoBtnStr = (String) osagoCascoBtn.getText();

                    approveRejectBtn = (RadioButton) findViewById(approveReject.getCheckedRadioButtonId());
                    approveRejectBtnStr = (String) approveRejectBtn.getText();

                    TypeBtn = (RadioButton) findViewById(Type.getCheckedRadioButtonId());
                    TypeBtnStr = (String) TypeBtn.getText();

                    DocsBtn = (RadioButton) findViewById(Docs.getCheckedRadioButtonId());
                    DocsBtnStr = (String) DocsBtn.getText();

                    DocsQualityBtn = (RadioButton) findViewById(DocsQuality.getCheckedRadioButtonId());
                    DocsQualityBtnStr = (String) DocsQualityBtn.getText();

                    DecisionValueBtn = (RadioButton) findViewById(DecisionValue.getCheckedRadioButtonId());
                    DecisionValueBtnStr = (String) DecisionValueBtn.getText();

                    // OSAGO
                    if (osagoCascoBtnStr.equals("ОСАГО")) {
                        // FO approve nte
                        if (approveRejectBtnStr.equals("ФО признала")) {
                            // Complex
                            if (TypeBtnStr.equals("Комплекс")) {
                                //Docs is on
                                if (DocsBtnStr.equals("Есть")) {
                                    //Docs is ok
                                    if (DocsQualityBtnStr.equals("Надлежащее")) {
                                        /*answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему рынку: " + selectedValueReg
                                                + " рублей 00 копеек");*/
                                        answerDesicion.setText(UDOV);
                                        answerBase.setText("По среднему рынку");
                                        if (selectedValueReg != 0) {
                                            answerSumm.setText(String.valueOf(selectedValueReg));
                                        } else {
                                            answerSumm.setText("");
                                        }
                                    //Docs id not ok
                                    } else {
                                        //answer.setText("ОТКАЗ. Основание: ненадлежащие документы");
                                        answerDesicion.setText(OTK);
                                        answerBase.setText("Ненадлежащие документы");
                                        answerSumm.setText("");
                                    }
                                //Docs is off
                                } else {
                                    //answer.setText("ОТКАЗ. Основание: нет документов");
                                    answerDesicion.setText(OTK);
                                    answerBase.setText("Нет документов");
                                    answerSumm.setText("");
                                }
                            // UTS
                            } else if (TypeBtnStr.equals("УТС")) {
                                //Docs is on
                                if (DocsBtnStr.equals("Есть")) {
                                    //Docs is ok
                                    if (DocsQualityBtnStr.equals("Надлежащее")) {
                                        //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: в полном размере");
                                        answerDesicion.setText(UDOV);
                                        answerBase.setText("В полном размере");
                                        answerSumm.setText("");
                                    //Docs is not ok
                                    } else {
                                        /*answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему: " + selectedValueReg +
                                                " рублей 00 копеек, ненадлежащие документы");*/
                                        answerDesicion.setText(UDOV);
                                        answerBase.setText("По среднему рынку");
                                        if (selectedValueReg != 0) {
                                            answerSumm.setText(String.valueOf(selectedValueReg));
                                        } else {
                                            answerSumm.setText("");
                                        }
                                    }
                                //Docs is off
                                } else {
                                    /*answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему: " + selectedValueReg +
                                            " рублей 00 копеек, нет документов");*/
                                    answerDesicion.setText(UDOV);
                                    answerBase.setText("По среднему рынку");
                                    if (selectedValueReg != 0) {
                                        answerSumm.setText(String.valueOf(selectedValueReg));
                                    } else {
                                        answerSumm.setText("");
                                    }
                                }
                            // Property
                            } else if (TypeBtnStr.equals("Имущество")) {
                                //Docs is on
                                if (DocsBtnStr.equals("Есть")) {
                                    //Docs is ok
                                    if (DocsQualityBtnStr.equals("Надлежащее")) {
                                        //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: в полном размере");
                                        answerDesicion.setText(UDOV);
                                        answerBase.setText("В полном размере");
                                        answerSumm.setText("");
                                    //Docs is not ok
                                    } else {
                                        //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, ненадлежащие документы");
                                        answerDesicion.setText(UDOV);
                                        answerBase.setText("По среднему рынку");
                                        if (selectedValueReg != 0) {
                                            answerSumm.setText(String.valueOf(selectedValueReg));
                                        } else {
                                            answerSumm.setText("");
                                        }
                                    }
                                //Docs is off
                                } else {
                                    //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, нет документов");
                                    answerDesicion.setText(UDOV);
                                    answerBase.setText("По среднему рынку");
                                    if (selectedValueReg != 0) {
                                        answerSumm.setText(String.valueOf(selectedValueReg));
                                    } else {
                                        answerSumm.setText("");
                                    }
                                }
                            }
                        // FO not approve nte
                        } else {
                            //Date after 01/06/2019
                            if(dateAndTime.compareTo(c01062019) >= 0){
                                //answer.setText("ОТКАЗ. Основание: после 01 июня 2019 года");
                                answerDesicion.setText(OTK);
                                answerBase.setText("После 01 июня 2019 года");
                                answerSumm.setText("");
                            //Date before 01/06/2019
                            } else {
                                //Approve claim
                                RadioButton DecisionValueOn = findViewById(R.id.DecisionValueOn);
                                if(DecisionValueBtnStr.equals(DecisionValueOn.getText())){
                                    // Complex
                                    if (TypeBtnStr.equals("Комплекс")) {
                                        //Docs is on
                                        if (DocsBtnStr.equals("Есть")) {
                                            //Docs is ok
                                            if (DocsQualityBtnStr.equals("Надлежащее")) {
                                                /*answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему рынку: " + selectedValueReg
                                                        + " рублей 00 копеек");*/
                                                answerDesicion.setText(UDOV);
                                                answerBase.setText("По среднему рынку");
                                                if (selectedValueReg != 0) {
                                                    answerSumm.setText(String.valueOf(selectedValueReg));
                                                } else {
                                                    answerSumm.setText("");
                                                }
                                                //Docs id not ok
                                            } else {
                                                //answer.setText("ОТКАЗ. Основание: ненадлежащие документы");
                                                answerDesicion.setText(OTK);
                                                answerBase.setText("Ненадлежащие документы");
                                                answerSumm.setText("");
                                            }
                                            //Docs is off
                                        } else {
                                            //answer.setText("ОТКАЗ. Основание: нет документов");
                                            answerDesicion.setText(OTK);
                                            answerBase.setText("Нет документов");
                                            answerSumm.setText("");
                                        }
                                        // UTS
                                    } else if (TypeBtnStr.equals("УТС")) {
                                        //Docs is on
                                        if (DocsBtnStr.equals("Есть")) {
                                            //Docs is ok
                                            if (DocsQualityBtnStr.equals("Надлежащее")) {
                                                //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: в полном размере");
                                                answerDesicion.setText(UDOV);
                                                answerBase.setText("В полном размер");
                                                answerSumm.setText("");
                                                //Docs is not ok
                                            } else {
                                                /*answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему: " + selectedValueReg +
                                                        " рублей 00 копеек, ненадлежащие документы");*/
                                                answerDesicion.setText(UDOV);
                                                answerBase.setText("По среднему рынку");
                                                if (selectedValueReg != 0) {
                                                    answerSumm.setText(String.valueOf(selectedValueReg));
                                                } else {
                                                    answerSumm.setText("");
                                                }
                                            }
                                            //Docs is off
                                        } else {
                                            /*answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему: " + selectedValueReg +
                                                    " рублей 00 копеек, нет документов");*/
                                            answerDesicion.setText(UDOV);
                                            answerBase.setText("По среднему рынку");
                                            if (selectedValueReg != 0) {
                                                answerSumm.setText(String.valueOf(selectedValueReg));
                                            } else {
                                                answerSumm.setText("");
                                            }
                                        }
                                        // Property
                                    } else if (TypeBtnStr.equals("Имущество")) {
                                        //Docs is on
                                        if (DocsBtnStr.equals("Есть")) {
                                            //Docs is ok
                                            if (DocsQualityBtnStr.equals("Надлежащее")) {
                                                //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: в полном размере");
                                                answerDesicion.setText(UDOV);
                                                answerBase.setText("В полном размере");
                                                answerSumm.setText("");
                                                //Docs is not ok
                                            } else {
                                                //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, ненадлежащие документы");
                                                answerDesicion.setText(UDOV);
                                                answerBase.setText("По среднему рынку");
                                                if (selectedValueReg != 0) {
                                                    answerSumm.setText(String.valueOf(selectedValueReg));
                                                } else {
                                                    answerSumm.setText("");
                                                }
                                            }
                                            //Docs is off
                                        } else {
                                            //answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, нет документов");
                                            answerDesicion.setText(UDOV);
                                            answerBase.setText("По среднему рынку");
                                            if (selectedValueReg != 0) {
                                                answerSumm.setText(String.valueOf(selectedValueReg));
                                            } else {
                                                answerSumm.setText("");
                                            }
                                        }
                                    }
                                //Not approve claim
                                } else {
                                    //answer.setText("ОТКАЗ. Основание: как неиспользованную");
                                    answerDesicion.setText(OTK);
                                    answerBase.setText("Как неиспользованную");
                                    answerSumm.setText("");
                                }
                            }

                        }
                        //CASCO
                    } else {
                        answerDesicion.setText("Смотрим правила страхования КАСКО");
                        answerBase.setText("");
                        answerSumm.setText("");
                    }
                } catch (Exception e) {
                Toast.makeText(MainActivity.this,"Нужно выбрать все пункты", Toast.LENGTH_SHORT).show();
                }
            }

        });

        try {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selectedRegion = allRegions[position];

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    valueOfnte = 0;
                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Something gone wrong", Toast.LENGTH_SHORT).show();

        }

    }

    public void isChecked() {

        int textColor;
        RadioButton osagoBtn = findViewById(R.id.osagoBtn);
        RadioButton cascoBtn = findViewById(R.id.cascoBtn);
        RadioButton approveBtn = findViewById(R.id.approveBtn);
        RadioButton rejectBtn = findViewById(R.id.rejectBtn);
        RadioButton ComplexBtn = findViewById(R.id.ComplexBtn);
        RadioButton UTSBtn = findViewById(R.id.UTSBtn);
        RadioButton PropertyBtn = findViewById(R.id.PropertyBtn);
        RadioButton DocsYes = findViewById(R.id.DocsYes);
        RadioButton DocsNo = findViewById(R.id.DocsNo);
        RadioButton DocsIsOK = findViewById(R.id.DocsIsOK);
        RadioButton DocsIsNotOK = findViewById(R.id.DocsIsNotOK);
        RadioButton DecisionValueOn = findViewById(R.id.DecisionValueOn);
        RadioButton DecisionValueOff = findViewById(R.id.DecisionValueOff);

        if (osagoCasco.getCheckedRadioButtonId() == -1){
            textColor = ContextCompat.getColor(this, R.color.colorEmpty);
            osagoBtn.setTextColor(textColor);
            cascoBtn.setTextColor(textColor);
        } else {
            textColor = ContextCompat.getColor(this, R.color.colorBlack);
            osagoBtn.setTextColor(textColor);
            cascoBtn.setTextColor(textColor);
        }

        if (approveReject.getCheckedRadioButtonId() == -1){
            textColor = ContextCompat.getColor(this, R.color.colorEmpty);
            approveBtn.setTextColor(textColor);
            rejectBtn.setTextColor(textColor);
        } else {
            textColor = ContextCompat.getColor(this, R.color.colorBlack);
            approveBtn.setTextColor(textColor);
            rejectBtn.setTextColor(textColor);
        }

        if (Type.getCheckedRadioButtonId() == -1){
            textColor = ContextCompat.getColor(this, R.color.colorEmpty);
            ComplexBtn.setTextColor(textColor);
            UTSBtn.setTextColor(textColor);
            PropertyBtn.setTextColor(textColor);
        } else {
            textColor = ContextCompat.getColor(this, R.color.colorBlack);
            ComplexBtn.setTextColor(textColor);
            UTSBtn.setTextColor(textColor);
            PropertyBtn.setTextColor(textColor);
        }

        if (Docs.getCheckedRadioButtonId() == -1){
            textColor = ContextCompat.getColor(this, R.color.colorEmpty);
            DocsYes.setTextColor(textColor);
            DocsNo.setTextColor(textColor);
        } else {
            textColor = ContextCompat.getColor(this, R.color.colorBlack);
            DocsYes.setTextColor(textColor);
            DocsNo.setTextColor(textColor);
        }

        if (DocsQuality.getCheckedRadioButtonId() == -1){
            textColor = ContextCompat.getColor(this, R.color.colorEmpty);
            DocsIsOK.setTextColor(textColor);
            DocsIsNotOK.setTextColor(textColor);
        } else {
            textColor = ContextCompat.getColor(this, R.color.colorBlack);
            DocsIsOK.setTextColor(textColor);
            DocsIsNotOK.setTextColor(textColor);
        }

        if (DecisionValue.getCheckedRadioButtonId() == -1){
            textColor = ContextCompat.getColor(this, R.color.colorEmpty);
            DecisionValueOn.setTextColor(textColor);
            DecisionValueOff.setTextColor(textColor);
        } else {
            textColor = ContextCompat.getColor(this, R.color.colorBlack);
            DecisionValueOn.setTextColor(textColor);
            DecisionValueOff.setTextColor(textColor);
        }
    }

    private void displayDatabaseInfo() {

        String[] projection  = {
                CULUMN_PERIOD1,
                CULUMN_PERIOD2,
                CULUMN_PERIOD3,
                CULUMN_PERIOD4,
                CULUMN_PERIOD5
        };

        String selection = CULUMN_REGION + "=?";
        String[] selectionArgs = {selectedRegion};

        cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();

        Calendar c01012018 = Calendar.getInstance();
        c01012018.set(2018,0,1);

        Calendar c01072018 = Calendar.getInstance();
        c01072018.set(2018,6,1);

        Calendar c01012019 = Calendar.getInstance();
        c01012019.set(2019,0,1);

        Calendar c01072019 = Calendar.getInstance();
        c01072019.set(2019,6,1);

        Calendar c01012020 = Calendar.getInstance();
        c01012020.set(2020,0,1);

        int columnIndex;
        if (dateAndTime.compareTo(c01072018) >= 0 && dateAndTime.compareTo(c01012019) < 0){
            columnIndex = cursor.getColumnIndex(CULUMN_PERIOD1);
            selectedValueReg = cursor.getInt(columnIndex);
        } else if (dateAndTime.compareTo(c01012019) >= 0 && dateAndTime.compareTo(c01072019) < 0){
            columnIndex = cursor.getColumnIndex(CULUMN_PERIOD2);
            selectedValueReg = cursor.getInt(columnIndex);
        } else if (dateAndTime.compareTo(c01072019) >= 0 && dateAndTime.compareTo(c01012020) < 0){
            columnIndex = cursor.getColumnIndex(CULUMN_PERIOD3);
            selectedValueReg = cursor.getInt(columnIndex);
        } else {
            //selectedValueReg = 5;
            Toast.makeText(MainActivity.this, "Сведений о средней стоимости НТЭ не имеется", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(MainActivity.this, String.valueOf(selectedValueReg), Toast.LENGTH_SHORT).show();

        cursor.close();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateAndTime.set(Calendar.HOUR, 0);
            dateAndTime.set(Calendar.MINUTE, 0);
            dateAndTime.set(Calendar.SECOND, 0);
            dateAndTime.set(Calendar.MILLISECOND, 0);
            setInitialDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {

        dateOfNTE.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }

    public boolean onTouchEvent (MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                if(x1 > x2 + 300){
                    Intent intent = new Intent(this, ChangeValuesOfNte.class);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (x1 < x2 - 300){
                    Intent intent = new Intent(this, ChangeValuesOfNte.class);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }

}
