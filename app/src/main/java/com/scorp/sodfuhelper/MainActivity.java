package com.scorp.sodfuhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView dateOfNTE;
    Calendar dateAndTime = Calendar.getInstance();
    Button checkbtn;
    TextView answer;
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
    Spinner spinner;
    int checkedRadioButtonId;



    String[] regions = {"Москва", "Самара", "Вологда", "Волгоград", "Саратов", "Воронеж"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateOfNTE = findViewById(R.id.setDate);
        checkbtn = findViewById(R.id.checkbtn);
        answer = findViewById(R.id.answer);
        osagoCasco = (RadioGroup) findViewById(R.id.osagoCasco);
        approveReject = (RadioGroup) findViewById(R.id.approveReject);
        Type = (RadioGroup) findViewById(R.id.Type);
        Docs = (RadioGroup) findViewById(R.id.Docs);
        DocsQuality = (RadioGroup) findViewById(R.id.DocsQuality);

        spinner = findViewById(R.id.regions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if(osagoCascoBtnStr.equals("ОСАГО")) {
                    if (approveRejectBtnStr.equals("ФО признала")) {
                        answer.setText("ФО признала");
                        if (TypeBtnStr.equals("Комплекс")) {
                            answer.setText("Комплекс");
                            if (DocsBtnStr.equals("Есть")) {
                                answer.setText("Есть");
                                if (DocsQualityBtnStr.equals("Надлежащее")) {
                                    answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему рынку, посмотри регион");
                                } else {
                                    answer.setText("ОТКАЗ. Основание: ненадлежащие документы");
                                }
                            } else {
                                answer.setText("ОТКАЗ. Основание: нет документов");
                            }
                        } else if (TypeBtnStr.equals("УТС")) {
                            if (DocsBtnStr.equals("Есть")) {
                                if (DocsQualityBtnStr.equals("Надлежащее")) {
                                    answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: в полном размере");
                                } else {
                                    answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, ненадлежащие документы");
                                }
                            } else {
                                answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, нет документов");
                            }
                        } else if (TypeBtnStr.equals("Имущество")) {
                            if (DocsBtnStr.equals("Есть")) {
                                if (DocsQualityBtnStr.equals("Надлежащее")) {
                                    answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: в полном размере.");
                                } else {
                                    answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, ненадлежащие документы");
                                }
                            } else {
                                answer.setText("УДОВЛЕТВОРЕНИЕ. Основание: по среднему, нет документов");
                            }
                        }
                    } else {

                    }
                } else {

                }
            }
        };

        checkbtn.setOnClickListener(ocl);
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
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {

        dateOfNTE.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }


}
