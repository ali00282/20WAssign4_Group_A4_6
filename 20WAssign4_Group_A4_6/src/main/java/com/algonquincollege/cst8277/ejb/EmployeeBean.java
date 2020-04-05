/***************************************************************************f******************u************zz*******y**
 * File: SomeBean.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 * (Modified) @date 2020 03
 */
package com.algonquincollege.cst8277.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TODO - rename and add necessary behaviours to access EmployeeSystem entities
 *
 */

/* This is the test of my code commit in github  */
@Stateless
public class EmployeeBean {

    @PersistenceContext(unitName = "assignment4-PU")
    protected EntityManager em;
}