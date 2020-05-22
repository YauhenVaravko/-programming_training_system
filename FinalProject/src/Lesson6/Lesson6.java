public class Lesson6{

    public static void main(String[] args){
        Car car = new Car("BMW X5");
        Car.SteeringWheel steeringWheel= car.new SteeringWheel();
        car.run();
        steeringWheel.left();
        steeringWheel.right();
    }
}
