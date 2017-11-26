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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.cmput301f17t30.habitrabbit.MainActivity.userController;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginListener);
        userController.clearUser();
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        public void onClick(View v) {

            EditText usernameText = (EditText)findViewById(R.id.userName);
            String name = usernameText.getText().toString();

            if (name.trim().length() == 0) {
                Toast.makeText(LoginActivity.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
            }

            else if (name.trim().length() > 20) {
                Toast.makeText(LoginActivity.this, "Please enter a name of 20 characters or less", Toast.LENGTH_SHORT).show();
            }

            else {
                 Intent returnIntent = getIntent();
                 userController.setUser(name);
                 setResult(RESULT_OK, returnIntent);
                 finish();
             }


        }
    };
}