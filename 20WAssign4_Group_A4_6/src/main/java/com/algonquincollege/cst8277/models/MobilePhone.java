/***************************************************************************f******************u************zz*******y**
 * File: MobilePhone.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author Tariq Ali - 040811012
 * @author Sirisha Jayampu - 040879561
 * @author Asha Alphonsa kurian - 040921013
 * @author Shahrir Ahmed - 040920464
 * @date 2020 02
 *
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//JPA Annotations here
@Entity
@DiscriminatorValue(value = "M")
public class MobilePhone extends PhonePojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    protected String provider;
    
    public MobilePhone() {
        super();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    
    // TODO - additional properties to match MPHONE table

}