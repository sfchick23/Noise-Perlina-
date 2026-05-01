package ru.sfchick.AlgoritmPerlin;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int size = sc.nextInt();
        int scale = sc.nextInt();
        int seed = sc.nextInt();
        Visualizer.showImage(Noise.perlin(size, scale, seed));

    }
}
