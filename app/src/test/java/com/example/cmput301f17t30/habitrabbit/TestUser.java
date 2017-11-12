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


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arankin on 20/10/17.
 */

public class TestUser  {

    public TestUser(){
        super ();
    }
    @Test
    public void testGetId(){
        User user = new User("bob");
        assertEquals("bob",user.getUserId());
    }
    @Test
    public void testSetId(){
        User user = new User("bob");
        user.setUserId("timmy");
        assertEquals("timmy",user.getUserId());
    }
}
