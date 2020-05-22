public class Lesson5{

    public static void main(String[] args){
        Pet pet1 = new Pet();
        Pet pet2 = new Cat("Васька");
        Pet pet3 = new Dog("Мухтар");
        Pet[] pets = new Pet[3];
        pets[0] = pet1;
        pets[1] = pet2;
        pets[2] = pet3;
        for(int i=0; i<pets.length; i++){
            pets[i].getName();
        }
    }
}
