public class PersonInfo {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;

    public PersonInfo(String firstName, String middleName, String lastName, String phone) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName() {return firstName;}
    public void setFirstName(String name) {this.firstName = firstName;}

    public String getMiddleName() {return firstName;}
    public void setMiddleName(String middleName) {this.middleName = middleName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonInfo toCheck = (PersonInfo) o;
        return firstName.equals(toCheck.firstName) &&
                middleName.equals(toCheck.middleName) &&
                lastName.equals(toCheck.lastName) &&
                phone.equals(toCheck.phone);
    }

    @Override
    public String toString(){
        return "Name: " + firstName + " " + middleName + " " + lastName + ", Phone number: " + phone;
    }
}