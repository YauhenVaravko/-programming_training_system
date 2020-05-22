public class Cat extends Pet{
    public Cat(String name){
        this.name = name;
    }
    public void getName(){
        System.out.println("Кот по кличке " + this.name);
    }
}
