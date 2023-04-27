
import java.util.ArrayList;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        Random rnd = new Random();
        Apple apple = new Apple();
        Orange orange = new Orange();

        ArrayList<Apple> appleListOne = new ArrayList<>();
        ArrayList<Orange> orangeListOne = new ArrayList<>();
        Box<Apple> appleBoxOne= new Box<>(appleListOne, apple);
        Box<Orange> orangeBoxOne = new Box<>(orangeListOne, orange);
        ArrayList<Apple> appleListTwo = new ArrayList<>();
        ArrayList<Orange> orangeListTwo = new ArrayList<>();
        Box<Apple> appleBoxTwo = new Box<>(appleListTwo, apple);
        Box<Orange> orangeBoxTwo = new Box<>(orangeListTwo, orange);

        for (int i = 0; i < 40; i++) {

            int fruitIndex = rnd.nextInt(1,5);

            if(fruitIndex == 1) appleBoxOne.addFruit();
            if(fruitIndex == 2) orangeBoxOne.addFruit();
            if(fruitIndex == 3) appleBoxTwo.addFruit();
            if(fruitIndex == 4) orangeBoxTwo.addFruit();
        }

        System.out.printf("Количество %s в коробке номер 1: %d, вес коробки: %.2f \n",
                apple, appleBoxOne.getBoxSize(), appleBoxOne.getBoxWeight());
        System.out.printf("Количество %s в коробке номер 2: %d, вес коробки: %.2f \n",
                orange, orangeBoxOne.getBoxSize(), orangeBoxOne.getBoxWeight());
        System.out.printf("Количество %s в коробке номер 3: %d, вес коробки: %.2f \n",
                apple, appleBoxTwo.getBoxSize(), appleBoxTwo.getBoxWeight());
        System.out.printf("Количество %s в коробке номер 4: %d, вес коробки: %.2f \n",
                orange, orangeBoxTwo.getBoxSize(), orangeBoxTwo.getBoxWeight());

        System.out.printf("Результат сравнения веса коробок: %b \n", appleBoxOne.compare(appleBoxOne, orangeBoxOne));

        System.out.println("*** Попытаемся переложить яблоки из одной коробки с яблоками в другую коробку с яблоками *** \n");
        BoxReloader firstReloaderOfBoxes = new BoxReloader(appleBoxOne, appleBoxTwo, 5);
        firstReloaderOfBoxes.reloaderOfBoxes();

        System.out.println("*** Попытаемся переложить яблоки в коробку с апельсинами *** \n");
        BoxReloader secondReloaderOfBoxes = new BoxReloader(appleBoxOne, orangeBoxOne, 5);
        secondReloaderOfBoxes.reloaderOfBoxes();
    }
}

abstract class Fruit {

    private final float weight;

    public Fruit(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }


}
class Apple extends Fruit{
    public Apple() {
        super(1.0f);
    }

    @Override
    public String toString() {
        return "Яблок";
    }

}

class Orange extends Fruit{
    public Orange() {
        super(1.5f);
    }

    @Override
    public String toString() {
        return "Апельсинов";
    }

}



class Box<T extends Fruit> {

    public Box(ArrayList<T> fruits, T fruit) {
        this.fruits = fruits;
        this.fruit = fruit;
    }

    ArrayList<T> fruits;

    T fruit;
    public void addFruit(){
        fruits.add(fruit);
    }

    public ArrayList<T> getFruits() {
        return fruits;
    }

    public int getBoxSize(){
        return fruits.size();
    }

    public double getBoxWeight(){
        return getBoxSize()*fruit.getWeight();
    }

    public boolean compare(Box o1, Box o2) {
        if (Double.compare(o1.getBoxWeight(), o2.getBoxWeight()) == 0) return true;
        else return false;
    }

}


class BoxReloader<T extends Fruit>{
    public Box<T> reloadFrom;
    public Box<T> reloadTo;
    public int amountToReload;
    public BoxReloader(Box<T> reloadFrom, Box<T> reloadTo, int amountToReload) {
        this.reloadFrom = reloadFrom;
        this.reloadTo = reloadTo;
        this.amountToReload = amountToReload;
    }

    public void reloaderOfBoxes(){

        if (reloadTo.fruits.get(0).getClass() == reloadFrom.fruits.get(0).getClass()) {
            int reloadingAmount;
            if(amountToReload > reloadFrom.fruits.size()) reloadingAmount = reloadFrom.fruits.size();
            else reloadingAmount = amountToReload;
            for (int i = 0; i < reloadingAmount; i++) {
                reloadTo.fruits.add(reloadFrom.fruits.get(0));
                reloadFrom.fruits.remove(reloadFrom.fruits.get(0));
            }

            System.out.printf("В коробке было фруктов: %d. Удалили фруктов : %d.  Осталось фруктов: %d \n",
                    reloadFrom.getBoxSize()+reloadingAmount, reloadingAmount, reloadFrom.getBoxSize());
            System.out.printf("В коробке было фруктов: %d. Переложили в коробку фруктов: %d. Теперь в коробке стало фруктов: %d \n",
                    reloadTo.getBoxSize()-reloadingAmount, reloadingAmount, reloadTo.getBoxSize());
        }

        else System.out.println("Нельзя смешивать разные фрукты в одной коробке \n");

    }

}