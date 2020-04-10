/***************************************************************************f******************u************zz*******y**
 * File: SomeBean.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 * (Modified) @date 2020 03
 * Group# A4_6
 * @author Tariq Ali - 040811012
 * @author Sirisha Jayampu - 040879561
 * @author Asha Alphonsa kurian - 040921013
 * @author Shahrir Ahmed - 040920464
 */
package com.algonquincollege.cst8277.ejb;

import static com.algonquincollege.cst8277.models.EmployeePojo.ALL_EMPLOYEES_QUERY_NAME;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import com.algonquincollege.cst8277.models.EmployeePojo;

/**
 * TODO - rename and add necessary behaviours to access EmployeeSystem entities
 *
 */
/* This is the test of my code commit in github */
@Stateless
public class EmployeeBean {
    @PersistenceContext(unitName = "assignment4-PU")
    protected EntityManager em;

    public List<EmployeePojo> findAllEmployees() {
        return em.createNamedQuery(ALL_EMPLOYEES_QUERY_NAME, EmployeePojo.class).getResultList();
    }

    public EmployeePojo findEmployeeById(Integer id) {
        return (EmployeePojo)em.createQuery("select e from Employee e where e.id=" + id).getSingleResult();
    }

    public void addEmployee(EmployeePojo emp) {
        em.persist(emp);
    }

    public void deleteEmployeeById(int empId) {
        EmployeePojo emp = (EmployeePojo)em.createQuery("select e from Employee e where e.id=" + empId)
            .getSingleResult();
        if (emp != null) {
            em.remove(emp);
        }
    }

    public void updateEmployee(EmployeePojo emp) {
        try {
            EmployeePojo dbEmp = (EmployeePojo)em.createQuery("select e from Employee e where e.id=" + emp.getId())
                .getSingleResult();
                if (dbEmp != null) {
                    dbEmp.setFirstName(emp.getFirstName());
                    dbEmp.setLastName(emp.getLastName());
                    dbEmp.setEmail(emp.getEmail());
                    dbEmp.setTitle(emp.getTitle());
                    dbEmp.setSalary(emp.getSalary());
                    dbEmp.setAddress(emp.getAddress());
                    dbEmp.setPhones(emp.getPhones());
                    em.persist(dbEmp);
                }
        }
        catch (Exception e) {
            
            // if the employee does not exist, create him
            em.persist(emp);
        }
    }
}