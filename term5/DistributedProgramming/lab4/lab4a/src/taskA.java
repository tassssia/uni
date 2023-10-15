public class taskA {
    public static void main(String[] args){
        Data data = new Data();
        PersonInfo info = new PersonInfo("Johnathan", "Christofer","Herondale","+380444444444");
        DBThread thread1 = new PhoneByNameSearcher(data, info.getLastName());
        DBThread thread2 = new NameByPhoneSearcher(data, info.getPhone());
        DBThread thread3 = new DBEditor(data, info);

        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread3.setDaemon(true);

        try {
            thread1.start();
            Thread.sleep(500);
            thread2.start();
            Thread.sleep(500);
            thread3.start();

            thread1.join();
            thread2.join();
            thread3.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
