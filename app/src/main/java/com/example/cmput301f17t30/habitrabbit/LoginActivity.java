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

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import static com.example.cmput301f17t30.habitrabbit.MainActivity.eventController;
import static com.example.cmput301f17t30.habitrabbit.MainActivity.habitController;
import static com.example.cmput301f17t30.habitrabbit.MainActivity.userController;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static elasticDoneBoolean elasticDoneL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginListener);
        userController.clearUser();
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        public void onClick(View v) {

            EditText usernameText = (EditText)findViewById(R.id.userName);
            final String name = usernameText.getText().toString().toLowerCase();

            if (name.trim().length() == 0) {
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.profile_layout),
                        R.string.enter_valid_username, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
            else if (name.trim().length() > 20) {
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.profile_layout),
                        R.string.too_long_username, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }

            else {

                ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();
                getUserTask.execute(name);
                elasticDoneL = new elasticDoneBoolean();
                elasticDoneL.setListener(new elasticDoneBoolean.ChangeListener() {
                    @Override
                    public void onChange() {
                        // if the username doesnt exist on elasticsearch, create a new user with that username
                        if (userController.checkUserExist() == Boolean.FALSE){
                            ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
                            User user = new User(name);
                            user.setJoinDate(new Date());
                            addUserTask.execute(user);
                        }
                        habitController.clearHabits();

                        //store the current user's username in phone preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", name);
                        editor.apply();

                        //go to main activity
                        Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainActivityIntent);
                        finish();
                    }
                });
        }
        }
    };

    //override back button so user cannot go back to previous activity without logging in
    @Override
    public void onBackPressed() {
    }
}
