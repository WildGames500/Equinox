package net.equinox.wild.equinox.entities;

import javax.persistence.*;

@Entity
@Table(name = "Structures")
public class DbStructures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id")
    private Integer id;

    @Column(name = "player_uuid")
    private String uuid;

    @Column(name = "small_barn")
    private Integer amount = 0;

    @Column(name = "med_barn")
    private Integer amount2 = 0;

    @Column(name = "large_barn")
    private Integer amount3 = 0;

    @Column(name = "small_pasture")
    private Integer amount4 = 0;

    @Column(name = "med_pasture")
    private Integer amount5 = 0;

    @Column(name = "large_pasture")
    private Integer amount6 = 0;

    @Column(name = "flat_arena")
    private Integer amount7 = 0;

    @Column(name = "sj_arena")
    private Integer amount8 = 0;

    @Column(name = "dressage_arena")
    private Integer amount9 = 0;

    @Column(name = "western_arena")
    private Integer amount10 = 0;

    @Column(name = "xc_course")
    private Integer amount11 = 0;

    @Column(name = "race_track")
    private Integer amount12 = 0;

    @Column(name = "steeple_track")
    private Integer amount13 = 0;

    @Column(name = "round_pen")
    private Integer amount14 = 0;

    public Integer getId() {
        return id;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getSmallBarn() {
        return amount;
    }

    public void setSmallBarn(Integer amount) {
        this.amount = amount;
    }

    public Integer getMedBarn() {
        return amount2;
    }

    public void setMedBarn(Integer amount2) {
        this.amount2 = amount2;
    }

    public Integer getLargeBarn() {
        return amount3;
    }

    public void setLargeBarn(Integer amount3) {
        this.amount3 = amount3;
    }


    public Integer getSmallPasture() {
        return amount4;
    }

    public void setSmallPasture(Integer amount4) {
        this.amount4 = amount4;
    }

    public Integer getMedPasture() {
        return amount5;
    }

    public void setMedPasture(Integer amount5) {
        this.amount5 = amount5;
    }

    public Integer getLargePasture() {
        return amount6;
    }

    public void setLargePasture(Integer amount6) {
        this.amount6 = amount6;
    }

    public Integer getFlatArena() {
        return amount7;
    }

    public void setFlatArena(Integer amount7) {
        this.amount7 = amount7;
    }


    public Integer getSjArena() {
        return amount8;
    }

    public void setSjArena(Integer amount8) {
        this.amount8 = amount8;
    }

    public Integer getDressageArena() {
        return amount9;
    }

    public void setDressageArena(Integer amount9) {
        this.amount9 = amount9;
    }

    public Integer getWesternArena() {
        return amount10;
    }

    public void setWesternArena(Integer amount10) {
        this.amount10 = amount10;
    }

    public Integer getXcCourse() {
        return amount11;
    }

    public void setXcCourse(Integer amount11) {
        this.amount11 = amount11;
    }

    public Integer getRaceTrack() {
        return amount12;
    }

    public void setRaceTrack(Integer amount12) {
        this.amount12 = amount12;
    }

    public Integer getSteepleTrack() {
        return amount13;
    }

    public void setSteepleTrack(Integer amount13) {
        this.amount13 = amount13;
    }

    public Integer getRoundPen() {
        return amount14;
    }

    public void setRoundPen(Integer amount14) {
        this.amount14 = amount14;
    }
}


