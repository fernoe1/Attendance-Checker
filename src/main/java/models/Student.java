package models;

public class Student extends Person{
    private int user_id;
    private int barcode;

    public Student(){

    }

    public Student(String name, String surname, int user_id, int barcode){
        super(name,surname);
        setUser_id(user_id);
        setBarcode(barcode);
    }

    public int getUser_id(){
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }
}
