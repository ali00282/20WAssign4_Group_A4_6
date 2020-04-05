/***************************************************************************f******************u************zz*******y**
 * File: HomePhone.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author Tariq Ali (Secondary)
 * Student# 040 811 012
 * @date 2020 02
 *
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//JPA Annotations here
@Entity
@DiscriminatorValue(value="H")
public class HomePhone extends PhonePojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected String map_coords;
    
    public HomePhone() {
        super();
    }
    
    @Column(name = "MAP_COORDS")
    public String getMap_coords() {
        return map_coords;
    }

    public void setMap_coords(String map_coords) {
        this.map_coords = map_coords;
    }
    
}