package ControlWork;

import ControlWork.Toy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToyStore {
    private List<Toy> toys;
    private List<Toy> prizeToys;

    public void ToyShop() {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateToyFrequency(int toyId, int newFrequency) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setFrequency(newFrequency);
                break;
            }
        }
    }

    public void initiateRaffle() {
        Random random = new Random();

        for (Toy toy : toys) {
            int randomNumber = random.nextInt(100) + 1;
            if (randomNumber <= toy.getFrequency()) {
                prizeToys.add(toy);
                toy.decreaseQuantity();
            }
        }
    }

    public boolean hasPrizeToys() {
        return !prizeToys.isEmpty();
    }

    public Toy getPrizeToy() {
        Toy prizeToy = prizeToys.get(0);
        prizeToys.remove(0);
        return prizeToy;
    }

    public void writePrizeToyToFile(Toy prizeToy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prize_toys.txt", true))) {
            writer.write(prizeToy.getId() + "," + prizeToy.getName());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
