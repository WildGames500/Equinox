package net.equinox.wild.equinox.entities;

import javax.persistence.*;

@Entity
@Table(name = "Horses")
public class DbHorse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "horse_id")
    private Integer id;

    @Column(name = "horse_level")
    private Integer level = 0;

    @Column(name = "horse_xp")
    private Integer xp = 0;

    @Column(name = "horse_mc_uuid")
    private String uuid;

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }
}
