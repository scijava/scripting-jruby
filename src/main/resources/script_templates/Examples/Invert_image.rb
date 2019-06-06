#@ ImagePlus i
# A basic script that inverts the current image.
# NB: this requires that an image is already open.
# The ImagePlus is populated automatically via the
# scripting framework.
# We explicitly import the java ij.ImagePlus class,
# but this may not be necessary if you already have
# an imagej.rb required into this script.
java_import "ij.ImagePlus"

# Invert the image and redraw it
$i.getProcessor.invert
$i.updateAndDraw
