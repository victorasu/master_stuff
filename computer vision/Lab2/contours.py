import cv2
import numpy as np

# Step 1: Read the image
image = cv2.imread('contours.png')

# Step 2: Convert to grayscale
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# Step 3: Apply Canny edge detection
edges = cv2.Canny(gray, 100, 200)  # Adjust thresholds as needed

# Step 4: Apply dilation (to make edges thicker)
kernel = np.ones((3, 3), np.uint8)  # 3x3 kernel for dilation
dilated_edges = cv2.dilate(edges, kernel, iterations=1)  # Dilation increases edge thickness

# Step 5: Apply erosion (optional, to refine edges)
eroded_edges = cv2.erode(dilated_edges, kernel, iterations=1)  # Erosion shrinks edges

# Step 6: Find contours from the edge-detected image
contours, _ = cv2.findContours(eroded_edges, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

# Step 7: Create a blank image to draw the contour boundaries (not filled)
contour_image = np.zeros_like(image)  # Create a blank image

# Step 8: Draw only the contour boundaries (without filling the interior)
cv2.drawContours(contour_image, contours, -1, (255, 102, 204), thickness=3)  # Set thickness to 2 for outlines

# Step 9: Show the results
cv2.imshow('Original Image', image)
cv2.imshow('grayscale', gray)
cv2.imshow('edges', edges)
cv2.imshow('dilated_edges', dilated_edges)
cv2.imshow('eroded_edges', eroded_edges)
cv2.imshow('Contour Boundaries', contour_image)  # Only the contour boundaries (edges)
cv2.waitKey(0)
cv2.destroyAllWindows()
