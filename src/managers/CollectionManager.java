package managers;

import models.FuelType;
import models.Vehicle;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionManager {

    private long nextId = 1;
    private LinkedList<Long> match = new LinkedList<>();
    private final DumpManager dumpManager;
    private List<Long> sortList = new LinkedList<Long>();

    private final LinkedList<Vehicle> collection = new LinkedList<Vehicle>();

    public LinkedList<Vehicle> getCollection(){
        return collection;
    }

    public CollectionManager(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }

    public boolean add(Vehicle e) {
        if (isContain(e)) return false;
        sortList.add(e.getId());
        collection.add(e);
        return true;
    }

    public String uniqueType() {
        Set<FuelType> fuelTypes = new HashSet<>();
        for (var e : collection)
            fuelTypes.add(e.getFuelType());
        StringBuilder answer = new StringBuilder();
        for (var a : fuelTypes)
            answer.append(a != null ? a.toString() : "null").append(" ");
        return answer.toString();
    }

    public boolean isContain(Vehicle e) {
        return getById((long) e.getId()) != null;
    }

    public boolean remove(long key) {
        for (Vehicle e : collection)
            if (e.getId() == key) {
                sortList.remove(key);
                collection.remove(e);
                return true;
            }
        return false;
    }

    public Long getFreeId() {
        while (getById(nextId) != null) {
            if (++nextId < 0) nextId = 1;
        }
        return nextId;
    }

    public Vehicle getById(Long id) {
        for (Vehicle e : collection)
            if (e.getId() == id)
                return e;
        return null;
    }

    public void clearCollection() {
        this.collection.clear();
    }


    public void saveCollection() {
        dumpManager.writeCollection(collection);
    }

    public boolean loadCollection() {
        collection.clear();
        dumpManager.readCollection(collection);
        for (Vehicle e : collection)
            if (getById(e.getId()) == null) {
                collection.clear();
                return false;
            } else {
                if (e.getId() > nextId) nextId = e.getId();
                sortList.add((long) e.getId());
            }
        return true;
    }

    public String toString(Function<Vehicle, Boolean> validator) {
        if (collection.isEmpty()) return "Коллекция пуста!";
        StringBuilder info = new StringBuilder();
        for(var e: collection.stream().filter(e -> validator.apply(e)).sorted(new VehicleComparator()).toList())
            info.append(e.getId()).append(" ").append(e.toString()).append("\n");
        if (info.isEmpty()) return "В коллекции таких элементов не существует!";
        return info.toString().trim();
    }
}
class VehicleComparator implements Comparator<Vehicle> {
    public int compare(Vehicle a, Vehicle b) {
        return a.getName().toUpperCase().compareTo(b.getName().toUpperCase());
    }
}
