from PIL import Image
import os

def flip_images_in_folder(folder_path):
    for filename in os.listdir(folder_path):
        if filename.endswith(('.jpg', '.jpeg', '.png', '.gif')):
            image_path = os.path.join(folder_path, filename)
            image = Image.open(image_path)

            # Horizontal flip
            flipped_image = image.transpose(Image.FLIP_LEFT_RIGHT)

            # Save the flipped image, overwrite the original file
            flipped_image.save(image_path)

if __name__ == "__main__":
    current_folder = os.path.dirname(os.path.abspath(__file__))
    flip_images_in_folder(current_folder)
