public class Car {
    public String model;

    public Car(String model){
        this.model = model;
    }
    public void run(){
        System.out.println("Машина " + this.model +" поехала.");
    }

    public class SteeringWheel {

        public void right() {
            System.out.println("Руль вправо!");
        }

        public void left() {
            System.out.println("Руль влево!");
        }
    }
}
