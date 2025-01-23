package models;

public class Student extends Person{
    private String user_id;
    private int barcode;

    public Student(){

    }

    public Student(String name,String surname,String user_id,int barcode){
        super(name,surname);
        setUser_id(user_id);
        setBarcode(barcode);
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }
}
