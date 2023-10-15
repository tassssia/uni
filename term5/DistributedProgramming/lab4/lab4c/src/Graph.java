import java.security.SecureRandom;
import java.util.ArrayList;

public class Graph {
    private ArrayList<ArrayList<Integer>> costs;
    private ArrayList<ArrayList<ReadWriteLock>> locks;

    public Graph(int size) {
        locks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<ReadWriteLock> tmp = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                tmp.add(new ReadWriteLock());
            }
            locks.add(tmp);
        }

        SecureRandom random = new SecureRandom();
        costs = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> tmp = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                tmp.add(0);
            }
            costs.add(tmp);
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int cost = (random.nextInt(100) + 1) * (random.nextInt(3) % 2);
                costs.get(i).set(j, cost);
                costs.get(j).set(i, cost);
            }
        }
    }

    public int getSize() {
        return costs.size();
    }

    public int getCost(int i, int j) throws Exception {
        if (i < 0 || j < 0) throw new Exception();
        return costs.get(i).get(j);
    }
    public int setCost(int i, int j, int value) throws Exception {
        if (i < 0 || j < 0) throw new Exception();
        return costs.get(i).set(j, value);
    }

    public void addCity() {
        SecureRandom random = new SecureRandom();
        int t;
        ArrayList<Integer> tmpCost = new ArrayList<>(getSize() + 1);
        ArrayList<ReadWriteLock> tmpLock = new ArrayList<>(getSize() + 1);

        for (int i = 0; i < getSize(); i++) {
            t = (random.nextInt(100) + 1) * (random.nextInt(3) % 2);
            costs.get(i).add(t);
            locks.get(i).add(new ReadWriteLock());
            tmpCost.add(t);
            tmpLock.add(new ReadWriteLock());
        }
        costs.add(tmpCost);
        locks.add(tmpLock);
    }
    public void removeCity(int city) {
        if (city >= getSize()) { return; }

        costs.remove(city);
        locks.remove(city);

        for (int i = 0; i < getSize(); i++) {
            costs.get(i).remove(city);
            locks.get(i).remove(city);
        }
    }

    public void LockRead(int i, int j) {
        locks.get(i).get(j).ReadLock();
    }
    public void UnlockRead(int i, int j) {
        locks.get(i).get(j).ReadUnlock();
    }
    public void LockWrite(int i, int j) {
        locks.get(i).get(j).WriteLock();
    }
    public void UnlockWrite(int i, int j) {
        locks.get(i).get(j).WriteUnlock();
    }

    public void LockRead() {
        for (int i = 0; i < locks.size(); i++)
            for (int j = 0; j < locks.get(0).size(); j++)
                LockRead(i ,j);
    }
    public void UnlockRead() {
        for (int i = 0; i < locks.size(); i++)
            for (int j = 0; j < locks.get(0).size(); j++)
                UnlockRead(i ,j);
    }
    public void LockWrite() {
        for (int i = 0; i < locks.size(); i++)
            for (int j = 0; j < locks.get(0).size(); j++)
                LockWrite(i ,j);
    }
    public void UnlockWrite() {
        for (int i = 0; i < locks.size(); i++)
            for (int j = 0; j < locks.get(0).size(); j++)
                UnlockWrite(i ,j);
    }

    public void matrixOutput() {
        try {
            for (int i = 0; i < getSize(); i++){
                for (int j = 0; j < getSize(); j++) {
                    System.out.print(getCost(i, j) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {e.printStackTrace();}
    }
}