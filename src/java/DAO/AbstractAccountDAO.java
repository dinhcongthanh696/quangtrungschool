/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractAccountDAO extends BaseDAO{
    public abstract List<Account> getAll();
    public abstract Account getById(String username);
    public abstract void getGroupOfNews(Account account);
    
}
