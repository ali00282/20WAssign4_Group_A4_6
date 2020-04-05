/***************************************************************************f******************u************zz*******y**
 * File: WorkPhone.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author Tariq Ali (Secondary)
 * Student# 040 811 012
 * @date 2020 02
 *
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//JPA Annotations here
@Entity
@DiscriminatorValue(value = "W")
public class WorkPhone extends PhonePojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    protected String department;
    
    public WorkPhone() {
        super();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    
}