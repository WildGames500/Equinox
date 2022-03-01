package net.equinox.wild.equinox;

import net.equinox.wild.equinox.entities.DbHorse;
import org.bukkit.entity.Entity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class DatabaseUtilities {

    private EntityManager manager;

    public DatabaseUtilities(EntityManager manager) {
        this.manager = manager;
    }

    public int addHorseToDatabase(Entity entity) {
        System.out.println("Preparing to add to DB");
        manager.getTransaction().begin();
        System.out.println("Transaction begun");
        DbHorse horse = new DbHorse();
        horse.setUuid(entity.getUniqueId().toString());
        System.out.println("Persisting to DB");
        manager.persist(horse);
        System.out.printf("Created Horse in DB with ID %s from UUID %s%n", horse.getId(), horse.getUuid());
        manager.getTransaction().commit();
        return 0;
    }

    @SuppressWarnings("unchecked")
    public DbHorse getHorseFromDatabase(UUID uuid) throws NoSuchElementException {
        Query query = manager.createQuery("from net.equinox.wild.equinox.entities.DbHorse");
        List<DbHorse> horses = query.getResultList();

        return horses.stream().filter(horse -> horse.getUuid().equalsIgnoreCase(uuid.toString())).findFirst().orElseThrow();
    }

    public void updateHorseInDatabase(DbHorse horse) {
        manager.getTransaction().begin();
        manager.persist(horse);
        manager.getTransaction().commit();
    }

}
