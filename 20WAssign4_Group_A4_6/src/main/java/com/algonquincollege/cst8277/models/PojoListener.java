/***************************************************************************f******************u************zz*******y**
 * File: PojoListener.java
 * Course materials (20W) CST 8277
 * Student# 040 811 012
 * @author (original) Mike Norman
 * Group# A4_6
  * @author Tariq Ali - 040811012
 * @author Sirisha Jayampu - 040879561
 * @author Asha Alphonsa kurian - 040921013
 * @author Shahrir Ahmed - 040920464
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PojoListener {

    
   @PrePersist
   public void onPersist(PojoBase p) {
       LocalDateTime now = LocalDateTime.now();
       p.setCREATED_DATE(now);
       p.setUPDATED_DATE(now);
   }
   
   @PreUpdate
   public void onUpdate(PojoBase p) {
       LocalDateTime now = LocalDateTime.now();
        p.setUPDATED_DATE(now);
   }
}