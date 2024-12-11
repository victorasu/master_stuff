import cv2
import numpy as np

# Load the input image
image2 = "MorpologicalCornerDetection.png"  # Replace with your image path
image1 = "contours.png"
original_img = cv2.imread(image1)
gray_img = cv2.cvtColor(original_img, cv2.COLOR_BGR2GRAY)

# Convert the grayscale image to binary
_, binary_img = cv2.threshold(gray_img, 127, 255, cv2.THRESH_BINARY)

# Define structuring elements
cross = cv2.getStructuringElement(cv2.MORPH_CROSS, (3, 3))
diamond = np.array([[0, 1, 0], [1, 1, 1], [0, 1, 0]], dtype=np.uint8)
xshape = np.array([[1, 0, 1], [0, 1, 0], [1, 0, 1]], dtype=np.uint8)
square = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))

# Step 1: Dilate and erode with cross and diamond
R1 = cv2.dilate(binary_img, cross)
R1 = cv2.erode(R1, diamond)

# Step 2: Dilate and erode with X-shape and square
R2 = cv2.dilate(binary_img, xshape)
R2 = cv2.erode(R2, square)

# Step 3: Compute the absolute difference
R = cv2.absdiff(R2, R1)

# Step 4: Overlay the results on the original image
corners = np.where(R > 0)
for (y, x) in zip(corners[0], corners[1]):
    cv2.circle(original_img, (x, y), radius=5, color=(0, 0, 255), thickness=2)

# Display results
cv2.imshow("Corners", R)
cv2.imshow("Overlay", original_img)
cv2.waitKey(0)
cv2.destroyAllWindows()
