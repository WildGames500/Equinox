package net.equinox.wild.equinox.entities;

import javax.persistence.*;

@Entity
@Table(name = "ColicInstances")
public class IllnessColic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "instance_id")
    private Integer id;

    // Symptoms
    @Column(name = "constipation", columnDefinition = "boolean default 0")
    private boolean hasConstipation = false;

    @Column(name = "anxiety", columnDefinition = "boolean default 0")
    private boolean hasAnxiety;

    @Column(name = "sweating", columnDefinition = "boolean default 0")
    private boolean hasSweating;

    @Column(name = "restlessness", columnDefinition = "boolean default 0")
    private boolean hasRestlessness;

    @Column(name = "gi_issues", columnDefinition = "boolean default 0")
    private boolean hasGiIssues;

    @Column(name = "diagnosis_state", columnDefinition = "boolean default 0")
    private boolean isDiagnosed;

    @Column(name = "time_undiagnosed", columnDefinition = "int(11) default 0")
    private int timeUndiagnosed;

    @Column(name = "sickness_time", columnDefinition = "int(11) default 0")
    private int sicknessTime;

    // Treatments
    public void handWalking() {
        // Do something here, such as set a symptom to false
    }

    public Integer getId() {
        return id;
    }

    public boolean hasConstipation() {
        return hasConstipation;
    }

    public void setHasConstipation(boolean hasConstipation) {
        this.hasConstipation = hasConstipation;
        update();
    }

    public boolean hasAnxiety() {
        return hasAnxiety;
    }

    public void setHasAnxiety(boolean hasAnxiety) {
        this.hasAnxiety = hasAnxiety;
        update();
    }

    public boolean isSweating() {
        return hasSweating;
    }

    public void setHasSweating(boolean hasSweating) {
        this.hasSweating = hasSweating;
        update();
    }

    public boolean hasRestlessness() {
        return hasRestlessness;
    }

    public void setHasRestlessness(boolean hasRestlessness) {
        this.hasRestlessness = hasRestlessness;
        update();
    }

    public boolean hasGiIssues() {
        return hasGiIssues;
    }

    public void setHasGiIssues(boolean hasGiIssues) {
        this.hasGiIssues = hasGiIssues;
        update();
    }

    public boolean isDiagnosed() {
        return isDiagnosed;
    }

    public void setDiagnosed(boolean diagnosed) {
        isDiagnosed = diagnosed;
        update();
    }

    public int getSicknessTime() {
        return sicknessTime;
    }

    public void setSicknessTime(int sicknessTime) {
        this.sicknessTime = sicknessTime;
        update();
    }

    public int getTimeUndiagnosed() {
        return timeUndiagnosed;
    }

    public void setTimeUndiagnosed(int timeUndiagnosed) {
        this.timeUndiagnosed = timeUndiagnosed;
    }

    @Override
    public String toString() {
        return "IllnessColic{" +
                "id=" + id +
                ", hasConstipation=" + hasConstipation +
                ", hasAnxiety=" + hasAnxiety +
                ", hasSweating=" + hasSweating +
                ", hasRestlessness=" + hasRestlessness +
                ", hasGiIssues=" + hasGiIssues +
                ", isDiagnosed=" + isDiagnosed +
                ", timeUndiagnosed=" + timeUndiagnosed +
                ", sicknessTime=" + sicknessTime +
                '}';
    }

    private void update() {
        if(sicknessTime > 20) {
            hasGiIssues = true;
        }
    }
}
