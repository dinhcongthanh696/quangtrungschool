/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.News;

/**
 *
 * @author My Computer
 */
public abstract class AbstractNewsDAO extends BaseDAO{
    public abstract void insert(News news);
    
}
