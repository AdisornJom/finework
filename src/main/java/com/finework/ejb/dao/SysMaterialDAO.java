/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.core.ejb.to.ReportStockI105TO;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
   
   public List<ReportStockI105TO> findReportSummaryI105(SysMaterial material, SysContractor contractor, int[] range) throws Exception {
        StringBuilder sb = new StringBuilder();        
        sb.append("select  material_id,contractor_id,material_desc,contractor_name_th,contractor_nickname, " +
                    "	sum(income) as income,sum(recive) recive,sum(return_) returnstock,sum(repair_in) repair_in," +
                    "	sum(outcome) outcome,sum(take) take,sum(borrow) borrow,sum(repair_out) repair_out," +
                    "	sum(income)-sum(outcome) as total from (" +
                    "  SELECT g.material_id,f.contractor_id,g.material_desc, f.contractor_name_th,f.contractor_nickname,sum(c.quantity) as income," +
                    "         sum(CASE WHEN c.status = 'P' THEN c.quantity ELSE 0 END) AS recive," +
                    "         sum(CASE WHEN c.status = 'R' THEN c.quantity ELSE 0 END) AS return_," +
                    "         sum(CASE WHEN c.status = 'Y' THEN c.quantity ELSE 0 END) AS repair_in," +
                    "         0 outcome,0 take,0 borrow,0 repair_out" +
                    "    FROM sys_material_receipts c,sys_contractor f,sys_material g " +
                    "    where c.contractor_id=f.contractor_id" +
                    "    and c.material_id=g.material_id" );
                if (contractor != null) {
                   sb.append(" and c.contractor_id  = '").append(contractor.getContractorId()).append("'");
                }
                if (material != null) {
                   sb.append(" and g.material_id  = '").append(material.getMaterialId()).append("'");
                }
         sb.append(" GROUP BY g.material_desc,f.contractor_name_th,f.contractor_nickname" +
                    "  union ALL" +
                    "  SELECT g.material_id,f.contractor_id,g.material_desc,f.contractor_name_th,f.contractor_nickname,0 income," +
                    "   0 recive,0 return_,0 repair_in,sum(b.quantity) as outcome," +
                    "   sum(CASE WHEN a.status = 'Y' THEN b.quantity ELSE 0 END) AS take," +
                    "   sum(CASE WHEN a.status = 'B' THEN b.quantity ELSE 0 END) AS borrow," +
                    "   sum(CASE WHEN a.status = 'P' THEN b.quantity ELSE 0 END) AS repair_out" +
                    "  FROM sys_material_expenses a,sys_material_expenses_detail b,sys_contractor f,sys_material g" +
                    "   where a.contractor_id=f.contractor_id" +
                    "	and b.material_id=g.material_id" +
                    "	and a.expenses_id=b.expenses_id ");
                if (contractor != null) {
                    sb.append(" and a.contractor_id  = '").append(contractor.getContractorId()).append("'");
                }
                if (material != null) {
                   sb.append(" and g.material_id = '").append(material.getMaterialId()).append("'");
                }
        sb.append(" GROUP BY g.material_desc,f.contractor_name_th,f.contractor_nickname)");
        sb.append(" x GROUP BY material_desc,contractor_name_th,contractor_nickname");     
        Query q = em.createNativeQuery(sb.toString(), ReportStockI105TO.class);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
   
   public BigInteger count_reportSummaryI105(SysMaterial material, SysContractor contractor) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ( ");
        sb.append("select  material_id,contractor_id,material_desc,contractor_name_th,contractor_nickname, " +
                    "	sum(income) as income,sum(recive) recive,sum(return_) returnstock,sum(repair_in) repair_in," +
                    "	sum(outcome) outcome,sum(take) take,sum(borrow) borrow,sum(repair_out) repair_out," +
                    "	sum(income)-sum(outcome) as total from (" +
                    "  SELECT g.material_id,f.contractor_id,g.material_desc, f.contractor_name_th,f.contractor_nickname,sum(c.quantity) as income," +
                    "         sum(CASE WHEN c.status = 'P' THEN c.quantity ELSE 0 END) AS recive," +
                    "         sum(CASE WHEN c.status = 'R' THEN c.quantity ELSE 0 END) AS return_," +
                    "         sum(CASE WHEN c.status = 'Y' THEN c.quantity ELSE 0 END) AS repair_in," +
                    "				 0 outcome,0 take,0 borrow,0 repair_out" +
                    "    FROM sys_material_receipts c,sys_contractor f,sys_material g " +
                    "    where c.contractor_id=f.contractor_id" +
                    "    and c.material_id=g.material_id" );
                if (contractor != null) {
                   sb.append(" and c.contractor_id  = '").append(contractor.getContractorId()).append("'");
                }
                if (material != null) {
                   sb.append(" and g.material_id  = '").append(material.getMaterialId()).append("'");
                }
         sb.append(" GROUP BY g.material_desc,f.contractor_name_th,f.contractor_nickname" +
                    "  union ALL" +
                    "  SELECT g.material_id,f.contractor_id,g.material_desc,f.contractor_name_th,f.contractor_nickname,0 income," +
                    "   0 recive,0 return_,0 repair_in,sum(b.quantity) as outcome," +
                    "   sum(CASE WHEN a.status = 'Y' THEN b.quantity ELSE 0 END) AS take," +
                    "   sum(CASE WHEN a.status = 'B' THEN b.quantity ELSE 0 END) AS borrow," +
                    "   sum(CASE WHEN a.status = 'P' THEN b.quantity ELSE 0 END) AS repair_out " +
                    "  FROM sys_material_expenses a,sys_material_expenses_detail b,sys_contractor f,sys_material g" +
                    "   where a.contractor_id=f.contractor_id" +
                    "	and b.material_id=g.material_id" +
                    "	and a.expenses_id=b.expenses_id ");
                if (contractor != null) {
                    sb.append(" and a.contractor_id  = '").append(contractor.getContractorId()).append("'");
                }
                if (material != null) {
                   sb.append(" and g.material_id = '").append(material.getMaterialId()).append("'");
                }
        sb.append(" GROUP BY g.material_desc,f.contractor_name_th,f.contractor_nickname)");
        sb.append(" x GROUP BY material_desc,contractor_name_th,contractor_nickname");    
        sb.append(" ) as x");
        Query q = em.createNativeQuery(sb.toString());
        try {
            BigInteger result = (BigInteger) q.getSingleResult();
            return (result == null) ? BigInteger.ZERO : result;
        } catch (NoResultException noe) {
            return BigInteger.ZERO;
        }
    }
     
}
