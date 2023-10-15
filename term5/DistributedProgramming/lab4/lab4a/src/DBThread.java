public abstract class DBThread extends Thread {
    Data db;
}

class PhoneByNameSearcher extends DBThread {
    private String toFind;

    public PhoneByNameSearcher(Data db, String toFind) {
        this.db = db;
        this.toFind = toFind;
    }

    @Override
    public void run(){
        // while(true){
        db.lockRead();
        System.out.println("Starting search by last name " + toFind);

        for(PersonInfo info: db.people) {
            if(info.getLastName().equals(toFind)) {
                System.out.println("Found by last name: " + info.toString());
            }
        }
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finishing search by last name " + toFind);
        db.unlockRead();
        //}
    }
}

class NameByPhoneSearcher extends DBThread {
    private String toFind;
    public NameByPhoneSearcher(Data db, String toFind) {
        this.db = db;
        this.toFind = toFind;
    }
    @Override
    public void run(){
        // while(true){
        db.lockRead();
        System.out.println("Starting search by phone " + toFind);

        for(PersonInfo info: db.people) {
            if(info.getPhone().equals(toFind)) {
                System.out.println("Found by phone: " + info.toString());
            }
        }
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finishing search by phone " + toFind);
        db.unlockRead();
        //}
    }
}

class DBEditor extends DBThread {
    private PersonInfo toEdit;
    public DBEditor(Data db, PersonInfo toEdit) {
        this.db = db;
        this.toEdit = toEdit;
    }
    @Override
    public void run(){
        //while(true){
        db.lockWrite();

        System.out.println("Starting editing DB");

        if (db.getIndex(toEdit) != -1) {
            db.people.remove(toEdit);
            System.out.println("Deleted " + toEdit.toString());
        }
        else {
            db.people.add(toEdit);
            System.out.println("Added " + toEdit.toString());
        }

        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finishing editing DB");
        db.unlockWrite();
        //}
    }

}