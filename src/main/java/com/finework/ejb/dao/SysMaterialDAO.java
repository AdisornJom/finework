/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysMaterialDAO extends AbstractDAO<SysMaterial> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysMaterialDAO() {
        super(SysMaterial.class);
    }
    
    
    public List<SysMaterial> findSysMaterialList() {
        Query q = em.createQuery("select o from SysMaterial o where o.status ='Y' order by  materialDesc asc");
        return q.getResultList();
    }
    
    public SysMaterial findSysMaterialById(Integer detailId) {
        Query q = em.createQuery("select o from SysMaterial o where o.status ='Y' and o.materialId =:materialId  order by  materialDesc asc");
        q.setParameter("materialId", detailId );
        
        return (SysMaterial)q.getSingleResult();
    }
     
     public List<SysMaterial> findSysMaterialListByCriteria(String classifyName,String itemname,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysMaterial o ");
        sb.append("where 1=1 ");
        if(null != classifyName && classifyName.length() > 0){
            sb.append("and o.classifyId.classifyDesc = :classifyName ");
        }
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.materialDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
        
        sb.append(" order by materialDesc asc");

        Query q = em.createQuery(sb.toString());
        if(null != classifyName && classifyName.length() > 0){
            q.setParameter("classifyName", classifyName);
        }
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
       
        return q.getResultList();
    }
     
    public List<SysMaterial> findSysMaterialListByCriteria(String classifyName,String itemname,String status, int[] range) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysMaterial o ");
        sb.append("where 1=1 ");
        if(null != classifyName && classifyName.length() > 0){
            sb.append("and o.classifyId.classifyDesc = :classifyName ");
        }
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.materialDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
        
        sb.append(" order by materialDesc asc");

        Query q = em.createQuery(sb.toString());
        if(null != classifyName && classifyName.length() > 0){
            q.setParameter("classifyName", classifyName);
        }
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
       
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
     
    public int countSysMaterialListByCriteria(String classifyName,String itemname,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(o) FROM SysMaterial o ");
        sb.append("where 1=1 ");
        if(null != classifyName && classifyName.length() > 0){
            sb.append("and o.classifyId.classifyDesc = :classifyName ");
        }
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.materialDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
        
        sb.append(" order by materialDesc asc");

        Query q = em.createQuery(sb.toString());
        if(null != classifyName && classifyName.length() > 0){
            q.setParameter("classifyName", classifyName);
        }
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
       
        return ((Number) q.getSingleResult()).intValue();
    }
     
    public List<SysMaterial> findSysMaterialListNotmoving(SysMaterialClassify classify,String itemname,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysMaterial o ");
        sb.append("where 1=1 ");
        if(null != classify){
            sb.append("and o.classifyId.classifyId = :classifyId ");
        }
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.materialDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
        sb.append("and (o.receiptsLastDate is not null or o.expensesLastDate is not null) ");
        sb.append(" order by o.receiptsLastDate,o.expensesLastDate asc");
        

        Query q = em.createQuery(sb.toString());
        if(null != classify ){
            q.setParameter("classifyId", classify.getClassifyId());
        }
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
       
        return q.getResultList();
    }
     
   public List<SysMaterial> findSysMaterialListNotmoving(String classifyName,String itemname,String status, int[] range) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysMaterial o ");
        sb.append("where 1=1 ");
        if(null != classifyName && classifyName.length() > 0){
            sb.append("and o.classifyId.classifyDesc = :classifyName ");
        }
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.materialDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
        sb.append("and (o.receiptsLastDate is not null or o.expensesLastDate is not null) ");
        
        sb.append(" order by o.receiptsLastDate,o.expensesLastDate asc");

        Query q = em.createQuery(sb.toString());
        if(null != classifyName && classifyName.length() > 0){
            q.setParameter("classifyName", classifyName);
        }
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
       
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
   
   public int countSysMaterialListNotmoving(String classifyName,String itemname,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(o) FROM SysMaterial o ");
        sb.append("where 1=1 ");
        if(null != classifyName && classifyName.length() > 0){
            sb.append("and o.classifyId.classifyDesc = :classifyName ");
        }
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.materialDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
         sb.append("and (o.receiptsLastDate is not null or o.expensesLastDate is not null ) ");
        
        sb.append(" order by o.receiptsLastDate, o.expensesLastDate asc");

        Query q = em.createQuery(sb.toString());
        if(null != classifyName && classifyName.length() > 0){
            q.setParameter("classifyName", classifyName);
        }
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
       
        return ((Number) q.getSingleResult()).intValue();
    }
     
}
