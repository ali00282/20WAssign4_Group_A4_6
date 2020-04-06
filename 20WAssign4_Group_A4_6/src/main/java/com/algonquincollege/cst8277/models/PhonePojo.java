/***************************************************************************f******************u************zz*******y**
 * File: PhonePojo.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author Tariq Ali (Secondary)
 * Student# 040 811 012
 * (Modified) @date 2020 02
 *
 * Copyright (c) 1998, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Original @authors dclarke, mbraeuer
 */
package com.algonquincollege.cst8277.models;


import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Phone class
 * 
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "phoneType")
  @JsonSubTypes({
    @Type(value = HomePhone.class, name = "H"),
    @Type(value = WorkPhone.class, name = "W"),
    @Type(value = MobilePhone.class, name = "M")
  })
@Entity(name="Phone")
@Table(name="PHONE")
@AttributeOverride(name="id", column=@Column(name="PHONE_ID"))
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="PHONE_TYPE", length=1)
public abstract class PhonePojo extends PojoBase {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    protected String areacode;
    protected String phoneNumber;
    protected String phone_type;
    protected EmployeePojo owingEmployee;
    // TODO - persistent properties

    // JPA requires each @Entity class have a default constructor
    public PhonePojo() {
        super();
    }

    @Column(name="AREACODE")
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Column(name="PHONENUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name="PHONE_TYPE")
    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }
   
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="OWNING_EMP_ID")
    public EmployeePojo getOwingEmployee() {
        return owingEmployee;
    }

    public void setOwingEmployee(EmployeePojo owingEmployee) {
        this.owingEmployee = owingEmployee;
    }
    
    

}