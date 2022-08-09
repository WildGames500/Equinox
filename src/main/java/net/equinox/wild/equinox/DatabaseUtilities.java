package net.equinox.wild.equinox;

import net.equinox.wild.equinox.entities.DbHorse;
import net.equinox.wild.equinox.entities.IllnessColic;
import org.bukkit.entity.Entity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

public class DatabaseUtilities {

    private EntityManager manager;

    public DatabaseUtilities(EntityManager manager) {
        this.manager = manager;
    }

    public int addHorseToDatabase(Entity entity, String ownerId) {
        System.out.println("Preparing to add to DB");
        manager.getTransaction().begin();
        System.out.println("Transaction begun");
        DbHorse horse = new DbHorse();
        horse.setUuid(entity.getUniqueId().toString());
        horse.setOwnerUuid(ownerId);
        horse.setLastWorld(entity.getWorld().getName());
        horse.setLastChunkX(entity.getLocation().getChunk().getX());
        horse.setLastChunkZ(entity.getLocation().getChunk().getZ());

        System.out.println("Persisting to DB");
        manager.persist(horse);
        System.out.printf("Created Horse in DB with ID %s from UUID %s%n", horse.getId(), horse.getUuid());
        manager.getTransaction().commit();
        return horse.getId();
    }

    public void giveColicToHorse(DbHorse horse) {
        manager.getTransaction().begin();
        IllnessColic colic = new IllnessColic();
        manager.persist(colic);
        horse.setColic(colic);
        manager.persist(horse);
        manager.getTransaction().commit();;
    }

    public void removeColicFromHorse(DbHorse horse) {
        manager.getTransaction().begin();
        manager.remove(horse.getColic());
        horse.setColic(null);
        manager.persist(horse);
        manager.getTransaction().commit();
    }

    public void deleteHorseFromDatabase(DbHorse horse) {
        manager.getTransaction().begin();
        manager.remove(horse);
        manager.getTransaction().commit();
    }

    //TODO: Update these two methods to query the database with the specific UUID/ID to prevent lag
    @SuppressWarnings("unchecked")
    public DbHorse getHorseFromDatabase(UUID uuid) throws NoSuchElementException {
        Query query = manager.createQuery("from net.equinox.wild.equinox.entities.DbHorse");
        List<DbHorse> horses = query.getResultList();

        return horses.stream().filter(horse -> horse.getUuid().equalsIgnoreCase(uuid.toString())).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public DbHorse getHorseFromDatabaseById(int id) throws NoSuchElementException {
        Query query = manager.createQuery("from net.equinox.wild.equinox.entities.DbHorse");
        List<DbHorse> horses = query.getResultList();

        return horses.stream().filter(horse -> horse.getId().equals(id)).findFirst().orElseThrow();
    }

    @SuppressWarnings("unchecked")
    public List<DbHorse> getAllHorsesFromDatabase() {
        Query query = manager.createQuery("from net.equinox.wild.equinox.entities.DbHorse");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<DbHorse> getHorsesOwnedByPlayer(String uuid) {
        Query query = manager.createQuery("from net.equinox.wild.equinox.entities.DbHorse");
        List<DbHorse> horses = query.getResultList();

        return horses.stream().filter(horse -> horse.getOwnerUuid().equalsIgnoreCase(uuid)).collect(Collectors.toList());
    }
    public void updateHorseOwnerUUID(String uuid, String h) {
//        System.out.printf("Preparing update transaction (Updating Horse # %s)%n", horse.getId());
        manager.getTransaction().begin();
        DbHorse horse = getHorseFromDatabase(UUID.fromString(h));
        horse.setOwnerUuid(uuid);
        manager.persist(horse);
        manager.getTransaction().commit();

//        System.out.println("Update transaction completed");
    }
    public void updateHorseInDatabase(DbHorse horse) {
//        System.out.printf("Preparing update transaction (Updating Horse # %s)%n", horse.getId());
        manager.getTransaction().begin();
        manager.persist(horse);
        manager.getTransaction().commit();
        if(horse.getColic() != null) {
            updateColicInDatabase(horse.getColic());
        }
//        System.out.println("Update transaction completed");
    }

    private void updateColicInDatabase(IllnessColic colic) {
//        System.out.printf("Preparing update transaction (Updating Horse # %s)%n", horse.getId());
        manager.getTransaction().begin();
        manager.persist(colic);
        manager.getTransaction().commit();
//        System.out.println("Update transaction completed");
    }

}
