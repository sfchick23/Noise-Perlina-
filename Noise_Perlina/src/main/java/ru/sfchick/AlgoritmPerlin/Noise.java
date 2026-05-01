package ru.sfchick.AlgoritmPerlin;

import java.util.Random;

public class Noise {
    private static final Random rand = new Random();
    private static Float lerp(float a, float b, double weight) {
        return (float) (a + weight * (b - a));
    }

    private static Double fade(double t) {
        return 6 * Math.pow(t, 5) - 15 * Math.pow(t, 4) + 10 * Math.pow(t, 3);
    }

    private static Float gradient(int cornerX, int cornerY, int[] matrix, float localX, float localY) {
        int randomVal = matrix[(cornerX + matrix[cornerY & 255]) & 255];

        return switch (randomVal % 4) {
            case 0 -> localX + localY;
            case 1 -> -localX + localY;
            case 2 -> localX - localY;
            case 3 -> -localX - localY;
            default -> 0.0f;
        };
    }

    public static Float perlinNumber(float x, float y, int[] matrix) {
        int ceilX = (int) x;
        int ceilY = (int) y;

        float localX = x - ceilX;
        float localY = y - ceilY;

        double u = fade(localX);
        double v = fade(localY);

        float gradBottomLeft = gradient(ceilX, ceilY, matrix, localX, localY);
        float gradBottomRight = gradient(ceilX + 1, ceilY, matrix, localX - 1, localY);

        float gradTopLeft = gradient(ceilX, ceilY + 1, matrix, localX, localY - 1);
        float gradTopRight = gradient(ceilX + 1, ceilY + 1, matrix, localX - 1, localY - 1);

        float bottom_mix = lerp(gradBottomLeft, gradBottomRight, u);
        float top_mix = lerp(gradTopLeft, gradTopRight, u);

        return lerp(bottom_mix, top_mix, v);
    }

    public static double[][] perlin(int size, float scale, int seed){
        rand.setSeed(seed);
        int[] matrix = new int[256];

        for (int i = 0; i < 256; i++) {
            matrix[i] = i;
        }

        for (int i = matrix.length - 1; i > 0 ; i--) {
            int index = rand.nextInt(i + 1);
            int temp = matrix[index];
            matrix[index] = matrix[i];
            matrix[i] = temp;
        }

        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = perlinNumber(i / scale, j / scale, matrix);
            }
        }

        return result;
    }
}
