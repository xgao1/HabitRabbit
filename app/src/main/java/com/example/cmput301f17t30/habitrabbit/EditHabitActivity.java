/*
 *     <HabitRabbit- A habit tracking app.>
 *     Copyright (C) <2017>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.example.cmput301f17t30.habitrabbit;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.ImageButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.cmput301f17t30.habitrabbit.MainActivity.eventList;
import static com.example.cmput301f17t30.habitrabbit.MainActivity.habitController;
import static com.example.cmput301f17t30.habitrabbit.MainActivity.habitList;
import static java.lang.Boolean.FALSE;

/**
 * Activity for editing a habit object.
 *
 */
public class EditHabitActivity extends AppCompatActivity {

    ArrayList<Boolean> days = new ArrayList<>();

    private static final int MONDAY = 0;
    private static final int TUESDAY = 1;
    private static final int WEDNESDAY = 2;
    private static final int THURSDAY = 3;
    private static final int FRIDAY = 4;
    private static final int SATURDAY = 5;
    private static final int SUNDAY = 6;

    EditText name;
    EditText reason;
    EditText date;
    ImageButton datePickerButton;

    Calendar dateSelected;
    DatePickerDialog datePickerDialog;

    SimpleDateFormat format;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        days = habitController.getDays();
        String oldName = habitController.getTitle();
        String oldReason = habitController.getReason();
        Date oldDate = habitController.getStartDate();

        name = (EditText) findViewById(R.id.editHabitName);
        reason = (EditText) findViewById(R.id.editHabitReason);
        date = (EditText) findViewById(R.id.editHabitStartDate);
        datePickerButton = (ImageButton)findViewById(R.id.datePickerButton);

        dateSelected = Calendar.getInstance();

        format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(FALSE);

        String startDate = format.format(oldDate);

        name.setText(oldName);
        reason.setText(oldReason);
        date.setText(startDate);

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    date.setText("");
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    name.setText("");
            }
        });

        reason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    reason.setText("");
            }
        });

        CheckBox mondayButton = (CheckBox) findViewById(R.id.editHabitMondayCheck);
        CheckBox tuesdayButton = (CheckBox) findViewById(R.id.editHabitTuesdayCheck);
        CheckBox wednesdayButton = (CheckBox) findViewById(R.id.editHabitWednesdayCheck);
        CheckBox thursdayButton = (CheckBox) findViewById(R.id.editHabitThursdayCheck);
        CheckBox fridayButton = (CheckBox) findViewById(R.id.editHabitFridayCheck);
        CheckBox saturdayButton = (CheckBox) findViewById(R.id.editHabitSaturdayCheck);
        CheckBox sundayButton = (CheckBox) findViewById(R.id.editHabitSundayCheck);

        mondayButton.setChecked(days.get(MONDAY));
        tuesdayButton.setChecked(days.get(TUESDAY));
        wednesdayButton.setChecked(days.get(WEDNESDAY));
        thursdayButton.setChecked(days.get(THURSDAY));
        fridayButton.setChecked(days.get(FRIDAY));
        saturdayButton.setChecked(days.get(SATURDAY));
        sundayButton.setChecked(days.get(SUNDAY));

        CompoundButton.OnCheckedChangeListener dayCheckListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton day, boolean isChecked) {
                switch (day.getId()){
                    case R.id.editHabitMondayCheck:
                        days.set(MONDAY, isChecked);
                        break;

                    case R.id.editHabitTuesdayCheck:
                        days.set(TUESDAY, isChecked);
                        break;

                    case R.id.editHabitWednesdayCheck:
                        days.set(WEDNESDAY, isChecked);
                        break;

                    case R.id.editHabitThursdayCheck:
                        days.set(THURSDAY, isChecked);
                        break;

                    case R.id.editHabitFridayCheck:
                        days.set(FRIDAY, isChecked);
                        break;

                    case R.id.editHabitSaturdayCheck:
                        days.set(SATURDAY, isChecked);
                        break;

                    case R.id.editHabitSundayCheck:
                        days.set(SUNDAY, isChecked);
                        break;
                }
            }
        };

        mondayButton.setOnCheckedChangeListener(dayCheckListener);
        tuesdayButton.setOnCheckedChangeListener(dayCheckListener);
        wednesdayButton.setOnCheckedChangeListener(dayCheckListener);
        thursdayButton.setOnCheckedChangeListener(dayCheckListener);
        fridayButton.setOnCheckedChangeListener(dayCheckListener);
        saturdayButton.setOnCheckedChangeListener(dayCheckListener);
        sundayButton.setOnCheckedChangeListener(dayCheckListener);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField();
            }
        });

        Button doneButton = (Button) findViewById(R.id.editHabitDone);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                //minimum date
                Date earliestEventDate = null;
                Date newDate = new Date();
                try {
                    newDate = format.parse(date.getText().toString());
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                if(eventList.getSize() > 0) {
                    earliestEventDate = eventList.getEvent(0).getDate();
                    for (HabitEvent event :eventList.getList()){
                        if (event.getDate().before(earliestEventDate)){
                            earliestEventDate = event.getDate();
                        }
                    }
                }
                Boolean duplicateFlag = Boolean.FALSE;
                for(int i = 0; i < habitList.getSize();i++){
                    String valueID = habitList.getHabit(i).getTitle();
                    String ID = habitList.getHabit(i).getId();
                    if(valueID.equals(name.getText().toString()) && ! habitController.getID().equals(ID)){
                        duplicateFlag = Boolean.TRUE;
                    }
                }

                try {
                    Date startDate = format.parse(date.getText().toString());
                }
                catch (Exception e){
                    date.setError("Valid date required");
                }
                if (name.getText().toString().trim().isEmpty()){
                    name.setError("Habit name required");
                }
                else if (name.getText().toString().length() > 20){
                    name.setError("Habit name too long");
                }
                else if (reason.getText().toString().length() > 30){
                    reason.setError("Reason text too long");
                }
                else if (date.getText().toString().isEmpty()){
                    date.setError("Valid date required");
                }
                else if (earliestEventDate != null && newDate.before(earliestEventDate)){
                    date.setError("Date cannot be before completion date of any event");
                }
                else if (duplicateFlag){
                    name.setError("Duplicate Habit name");
                }
                else {
                    editHabitDone();
                }
            }

        });

    }

    private void setDateTimeField() {
        Calendar newCalendar = dateSelected;
        final String pattern1 = "dd-MM-yyyy";
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                date.setText(new SimpleDateFormat(pattern1).format(dateSelected.getTime()));
            }

        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void editHabitDone(){
        try{

            String habitName = name.getText().toString();
            String habitReason = reason.getText().toString();
            Date startDate = format.parse(date.getText().toString());

            habitController.setTitle(habitName);
            habitController.setReason(habitReason);
            habitController.setDays(days);
            habitController.setDate(startDate);
            habitController.saveEditHabit();

            Intent returnToMain = new Intent();
            setResult(RESULT_OK, returnToMain);
            finish();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
