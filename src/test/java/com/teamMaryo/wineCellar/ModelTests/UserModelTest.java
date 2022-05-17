package com.teamMaryo.wineCellar.ModelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.teamMaryo.wineCellar.models.UserModel;

import org.junit.jupiter.api.Test;

public class UserModelTest {
    @Test
    public void ControlUserName(){
        UserModel errors = new UserModel("user_.","user@email.com","qwerty");
        assertEquals(errors.getUsername(),"noname");
    }

    @Test
    public void ControlEmail(){
        UserModel errors = new UserModel("user1","user.1email.com","qwerty");
        assertEquals(errors.getEmail(),"nomail@no.com");
    }
    
    @Test
    public void ControlNameEmail(){
        UserModel errors = new UserModel("user.","user.1email.com","qwerty");
        assertEquals(errors.getEmail(),"nomail@no.com");
        assertEquals(errors.getUsername(),"noname");
    }

    @Test
    public void TestOk(){
        UserModel correct = new UserModel("mariaoli","moli@email.com","qwerty");
        assertEquals(correct.getUsername(),"mariaoli");
        assertEquals(correct.getEmail(),"moli@email.com");
    }
    
}
