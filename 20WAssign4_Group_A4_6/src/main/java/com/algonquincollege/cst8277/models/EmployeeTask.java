/***************************************************************************f******************u************zz*******y**
 * File: EmployeeTask.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author Tariq Ali (Secondary)
 * Student# 040 811 012
 * @date 2020 02
 * @author Tariq Ali - 040811012
 * @author Sirisha Jayampu - 040879561
 * @author Asha Alphonsa kurian - 040921013
 * @author Shahrir Ahmed - 040920464
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeTask {

    protected String description;
    protected LocalDateTime task_start;
    protected LocalDateTime task_end_date;
    protected boolean task_done;
 
    public EmployeeTask() {
    }

   // @Column(name="TASK_DESCRIPTION")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //@Column(name="TASK_START")
    public LocalDateTime getTask_start() {
        return task_start;
    }

    public void setTask_start(LocalDateTime task_start) {
        this.task_start = task_start;
    }

   // @Column(name="TASK_END_DATE")
    public LocalDateTime getTask_end_date() {
        return task_end_date;
    }

    public void setTask_end_date(LocalDateTime task_end_date) {
        this.task_end_date = task_end_date;
    }
    //@Column(name="TASK_DONE")
    public boolean isTask_done() {
        return task_done;
    }

    public void setTask_done(boolean task_done) {
        this.task_done = task_done;
    }
    

}