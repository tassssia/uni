public class Customer {
    private int id;
    private int account;
    public int action; 
//    0 - withdraw
//    1 - top up
//    2 - transfer
//    3 - pay
//    4 - exchange
    
    public Customer(int id, int account, int action) {
        this.id = id;
        this.account = account;
        this.action = action;
    }
    
    public void withdraw(int amount) {
        if (account - amount >= 0) {
            account -= amount;
            System.out.println("Customer " + id + " has successfully withdrawn " + amount);
        }
    }
    public void topUp(int amount) {
        account += amount;
        System.out.println("Customer " + id + " has successfully topped up their account for " + amount);
    }
    public void transfer(int amount) {
        if (account - amount >= 0) {
            account -= amount;
            System.out.println("Customer " + id + " has successfully transfered " + amount);
        }
    }
    public void pay(int amount) {
        if (account - amount >= 0) {
            account -= amount;
            System.out.println("Customer " + id + " has successfully payed " + amount);
        }
    }
    public void exchange(int amount) {
        if (account >= amount) {
            System.out.println("Customer " + id + " has successfully exchanged " + amount);
        }
    }
}
