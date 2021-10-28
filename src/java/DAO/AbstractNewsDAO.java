/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.News;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractNewsDAO extends BaseDAO{
    public abstract void insert(News news);
    public abstract int getTotalNews(String query);
    public abstract List<News> search(String query,int offset,int limit);
    public abstract News getById(int no);
    public abstract News getById(int no,Account account);
    public abstract void update (News news);
    public abstract void delete(int no);
}
