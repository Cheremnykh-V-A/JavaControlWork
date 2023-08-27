package ControlWork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ToyShop {

    private List<Toy> toys;
    private List<Toy> prizeToys;

    public ToyShop() {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void setToyWeight(int toyId, int weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public void playGame() {
        Random random = new Random();

        int totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        if (totalWeight <= 0) {
            System.out.println("Нет доступных игрушек");
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (totalWeight <= 0) {
                break;
            }

            int randomNumber = random.nextInt(totalWeight) + 1;

            for (Toy toy : toys) {
                randomNumber -= toy.getWeight();

                if (randomNumber <= 0) {
                    prizeToys.add(toy);
                    toy.decreaseQuantity();
                    totalWeight -= toy.getWeight();

                    System.out.println("Выиграна игрушка: " + toy.getName());
                    break;
                }
            }
        }
    }

    public void savePrizeToysToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Toy toy : prizeToys) {
                writer.write(toy.getName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyShop toyShop = new ToyShop();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество игрушек для розыгрыша:");
        int numOfToys = scanner.nextInt();

        for (int i = 0; i < numOfToys; i++) {
            System.out.println("Введите id игрушки:");
            int id = scanner.nextInt();

            System.out.println("Введите название игрушки:");
            String name = scanner.next();

            System.out.println("Введите количество данных игрушек:");
            int quantity = scanner.nextInt();

            System.out.println("Введите частоту выпадения игрушки (вес в % от 100):");
            int weight = scanner.nextInt();

            Toy toy = new Toy(id, name, quantity, weight);
            toyShop.addToy(toy);
        }

        toyShop.playGame();

        System.out.println("Введите имя файла для сохранения призовых игрушек:");
        String filename = scanner.next();

        toyShop.savePrizeToysToFile(filename);
    }
}