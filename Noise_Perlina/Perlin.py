import time

import numpy as np
from matplotlib import pyplot as plt


def valid_input(prompt, default):
    while True:
        text = input(prompt)

        if not text:
            print(f"Применено значение по умолчанию: {default}")
            return default

        try:
            return int(text)
        except ValueError:
            print("ERROR!!!; Пожалуйста, введите число (например, 2 или -2.2).")

def linear_interpolation(a, b, weight):
    return a + weight * (b - a)

def smootherstep(t):

    return 6 * t**5 - 15 * t**4 + 10 * t**3

def gradient(x, y, rand_array, local_x, local_y):
    random_val = rand_array[(x + rand_array[y % 256]) % 256]

    if random_val % 4 == 0: return local_x + local_y
    if random_val % 4 == 1: return -local_x + local_y
    if random_val % 4 == 2: return local_x - local_y
    if random_val % 4 == 3: return -local_x - local_y

def perlin(x: float, y: float, array):

    ceil_x, ceil_y = int(x), int(y)

    local_x, local_y = x - ceil_x, y - ceil_y

    fade_x, fade_y = smootherstep(local_x), smootherstep(local_y)

    bottom_left = gradient(ceil_x, ceil_y, array, local_x, local_y)
    bottom_right = gradient(ceil_x + 1, ceil_y, array, local_x - 1, local_y)

    top_left = gradient(ceil_x, ceil_y + 1, array, local_x, local_y - 1)
    top_right = gradient(ceil_x + 1, ceil_y + 1, array, local_x - 1, local_y - 1)

    x1 = linear_interpolation(bottom_left, bottom_right, fade_x)
    y1 = linear_interpolation(top_left, top_right, fade_x)
    return linear_interpolation(x1, y1, fade_y)

seed = valid_input("Enter a seed: ", 0)
if seed == 0:
    seed = int(time.time() * 1000000) % (2**32)

np.random.seed(seed)
random_array = np.arange(256, dtype=int)
np.random.shuffle(random_array)

size = valid_input("Enter a size: ", 100)
image = np.zeros((size, size))
scale = valid_input("Enter a scale: ", 10)

for i in range(size):
    for j in range(size):
        image[i, j] = perlin(i / scale, j / scale, random_array)

print(image)
plt.imshow(image, cmap='gray')
plt.show()






