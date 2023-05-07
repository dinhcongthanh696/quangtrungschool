/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quangtrunghd.dao;

import DAO.AccountDAO;
import org.junit.Test;
import static org.junit.Assert.*;
import Model.Account;
import Model.Group;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author My Computer
 */
public class AccountTest {
    
    @Test
    public void checkGetAccountByIdGivenRightArguementReturnsWell(){
        AccountDAO accountDAO = new AccountDAO();
        Account actual = accountDAO.getById("huyennt5");
        List<Group> groupsExpected = new ArrayList<>();
        groupsExpected.add(new Group(2));
        groupsExpected.add(new Group(3));
        groupsExpected.add(new Group(4));
        assertEquals("huyennt5" , actual.getUsername());
        assertEquals("12345", actual.getPassword());
        for(int i = 0 ; i < actual.getGroups().size() ; i++){
            assertEquals(groupsExpected.get(i).getGid(), actual.getGroups().get(i).getGid());
        }
    }
}
