/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Model.Account;
import Model.Feature;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
public abstract class BaseAuthorization extends HttpServlet{
    
    public abstract void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    public abstract void processPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public int authenticate(HttpServletRequest req){
        Account account = (Account) req.getSession().getAttribute("account");
        if(account == null) return -1;
        else{
            for(Feature feature : account.getFeatures()){
                if(feature.getUrl().equals(req.getServletPath())){
                    return 1;
                }
            }
        }
        
        return 0;
    } 
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch(authenticate(req)){
            case -1 :
                resp.sendError(401);
                break;
            case 0 :
                resp.sendError(403);
                break;
            case 1 :
                processGet(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch(authenticate(req)){
            case -1 :
                resp.sendError(401);
                break;
            case 0 :
                resp.sendError(403);
                break;
            case 1 :
                processPost(req, resp);
                break;
        }
    }
    
    
}
