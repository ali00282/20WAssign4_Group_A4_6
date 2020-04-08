/***************************************************************************f******************u************zz*******y**
 * File: SomeBean.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 * (Modified) @date 2020 03
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
        em.persist(emp);
    }
}