public class Dog extends Pet{
    public Dog(String name){
       this.name = name;
    }
    public void getName(){
        System.out.println("Пес по кличке " + this.name);
    }
}
