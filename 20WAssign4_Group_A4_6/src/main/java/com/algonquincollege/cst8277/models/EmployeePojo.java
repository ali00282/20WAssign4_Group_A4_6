/***************************************************************************f******************u************zz*******y**
 * File: EmployeePojo.java
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static com.algonquincollege.cst8277.models.EmployeePojo.ALL_EMPLOYEES_QUERY_NAME;

import com.algonquincollege.cst8277.rest.HollowProjectsSerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The Employee class demonstrates several JPA features:
 * <ul>
 * <li>OneToOne relationship
 * <li>OneToMany relationship
 * <li>ManyToMany relationship
 * </ul>
 */
@Entity(name = "Employee")
@Table(name = "EMPLOYEE")
@AttributeOverride(name = "id", column = @Column(name = "EMP_ID"))
@NamedQueries(@NamedQuery(name = ALL_EMPLOYEES_QUERY_NAME, query = "select e from Employee e"))
public class EmployeePojo extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    public static final String ALL_EMPLOYEES_QUERY_NAME = "allEmployees";
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String title;
    protected Double salary;
    protected AddressPojo Address;
    protected List<EmployeeTask> employeeTasks = new ArrayList<>();
    protected List<PhonePojo> phones = new ArrayList<>();
    protected Set<ProjectPojo> projects = new HashSet<>();

    public EmployeePojo() {
        super();
    }

    /**
     * @return the value for firstName
     */
    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */
    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the value for email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the value for title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            new value for title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the value for salary
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * @param salary
     *            new value for salary
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /*
     * This is a one to one relation in database between Employee and Address
     * tables.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "ADDR_ID")
    public AddressPojo getAddress() {
        return Address;
    }

    public void setAddress(AddressPojo address) {
        Address = address;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "owingEmployee", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "PHONE_ID")
    public List<PhonePojo> getPhones() {
        return phones;
    }

    public void setPhones(List<PhonePojo> phones) {
        this.phones = phones;
    }

    @ManyToMany
    @JoinTable(name = "EMP_PROJ", joinColumns = @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID"), inverseJoinColumns = @JoinColumn(name = "PROJ_ID", referencedColumnName = "PROJ_ID"))
    @JsonSerialize(using = HollowProjectsSerializer.class)
    public Set<ProjectPojo> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectPojo> projects) {
        this.projects = projects;
    }

    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_TASKS", joinColumns = @JoinColumn(name = "OWNING_EMP_ID"))
    @AttributeOverrides({
        @AttributeOverride(name = "description", column = @Column(name = "TASK_DESCRIPTION")),
        @AttributeOverride(name = "task_start", column = @Column(name = "TASK_START")),
        @AttributeOverride(name = "task_end_date", column = @Column(name = "TASK_END_DATE")),
        @AttributeOverride(name = "task_done", column = @Column(name = "TASK_DONE"))})
    public List<EmployeeTask> getEmployeeTask() {
        return employeeTasks;
    }

    public void setEmployeeTask(List<EmployeeTask> employeeTasks) {
        this.employeeTasks = employeeTasks;
    }
}