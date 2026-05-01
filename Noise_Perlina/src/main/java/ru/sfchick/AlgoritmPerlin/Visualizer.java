package ru.sfchick.AlgoritmPerlin;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Visualizer {
    public static void showImage(double[][] data) {
        int height = data.length;
        int width = data[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = data[y][x];
                int brightness = (int) (((value + 1.0) / 2.0) * 255);

                brightness = Math.max(0, Math.min(255, brightness));

                int rgb = (brightness << 16) | (brightness << 8) | brightness;
                image.setRGB(x, y, rgb);
            }
        }

        JFrame frame = new JFrame("Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
