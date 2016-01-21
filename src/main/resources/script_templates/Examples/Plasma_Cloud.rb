# Manually create and display a color ImagePlus
java_import "ij.process.ColorProcessor"
java_import "ij.ImagePlus"

w = 800
h = 600

# Create an ImagePlus
cp = ColorProcessor.new(w, h)
i = ImagePlus.new "Plasma Cloud", cp

# Set Image's pixels
pixels = cp.getPixels;
java.util.Arrays.fill(pixels, 0, w*h/2 - 1, 0x0000FF00)

# Display the image
i.show
