import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Data {
    public ArrayList<PersonInfo> people;
    private ReadWriteLock readWriteLock;

    public Data(){
        people = startAccountsInit();
        readWriteLock = new ReentrantReadWriteLock();
    }

    private ArrayList<PersonInfo> startAccountsInit() {
        ArrayList<PersonInfo> example = new ArrayList<>();
        example.add(new PersonInfo("Clarissa", "Adele","Fairchild","+380000000000"));
        example.add(new PersonInfo("Isabelle", "Sophia","Lightwood","+380111111111"));
        example.add(new PersonInfo("Alexander", "Gideon","Lightwood","+380222222222"));
        example.add(new PersonInfo("Johnathan", "Christofer","Morgenstern","+380333333333"));
        example.add(new PersonInfo("Johnathan", "Christofer","Herondale","+380444444444"));
        example.add(new PersonInfo("Magnus", "", "Bane", "+380555555555"));
        return example;
    }

    public int getIndex(PersonInfo toFind) {
        int res = -1;
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).equals(toFind)) {
                res = i;
                break;
            }
        }
        return res;
    }

    public void lockRead() {
        readWriteLock.readLock().lock();
    }
    public void lockWrite() {
        readWriteLock.writeLock().lock();
    }

    public void unlockRead() {
        readWriteLock.readLock().unlock();
    }
    public void unlockWrite() {
        readWriteLock.writeLock().unlock();
    }
}