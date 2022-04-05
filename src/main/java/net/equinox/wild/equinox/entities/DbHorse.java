package net.equinox.wild.equinox.entities;

import javax.persistence.*;

@Entity
@Table(name = "Horses")
public class DbHorse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "horse_id")
    private Integer id;

    @Column(name = "horse_name")
    private String name;

    @Column(name = "horse_level")
    private Integer level = 0;

    @Column(name = "horse_xp")
    private Integer xp = 0;

    @Column(name = "horse_mc_uuid")
    private String uuid;

    @Column(name = "horse_owner_uuid")
    private String ownerUuid;

    @Column(name = "last_world")
    private String lastWorld;

    @Column(name = "last_chunk_x")
    private Integer lastChunkX = 0;

    @Column(name = "last_chunk_z")
    private Integer lastChunkZ = 0;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public String getLastWorld() {
        return lastWorld;
    }

    public void setLastWorld(String lastWorld) {
        this.lastWorld = lastWorld;
    }

    public Integer getLastChunkX() {
        return lastChunkX;
    }

    public void setLastChunkX(Integer lastChunkX) {
        this.lastChunkX = lastChunkX;
    }

    public Integer getLastChunkZ() {
        return lastChunkZ;
    }

    public void setLastChunkZ(Integer lastChunkZ) {
        this.lastChunkZ = lastChunkZ;
    }
}
