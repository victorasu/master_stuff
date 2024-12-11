import cv2
import numpy as np
import matplotlib.pyplot as plt


def hough_transform_methods(image_path):
    # Load image
    img = cv2.imread(image_path)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # Edge detection
    edges = cv2.Canny(gray, 50, 150, apertureSize=3)

    # Standard Hough Transform for line detection
    lines = cv2.HoughLines(edges, rho=1, theta=np.pi / 180, threshold=50)
    img_lines = img.copy()
    if lines is not None:
        for rho, theta in lines[:, 0]:
            a = np.cos(theta)
            b = np.sin(theta)
            x0 = a * rho
            y0 = b * rho
            x1 = int(x0 + 1000 * (-b))
            y1 = int(y0 + 1000 * (a))
            x2 = int(x0 - 1000 * (-b))
            y2 = int(y0 - 1000 * (a))
            cv2.line(img_lines, (x1, y1), (x2, y2), (0, 255, 0), 2)

    # Probabilistic Hough Transform for line segment detection
    segments = cv2.HoughLinesP(edges, rho=1, theta=np.pi / 180, threshold=50, minLineLength=50, maxLineGap=10)
    img_segments = img.copy()
    if segments is not None:
        for x1, y1, x2, y2 in segments[:, 0]:
            cv2.line(img_segments, (x1, y1), (x2, y2), (255, 0, 0), 2)

    # Hough Circle Transform for circle detection
    circles = cv2.HoughCircles(gray, cv2.HOUGH_GRADIENT, dp=1, minDist=20, param1=50, param2=30, minRadius=5,
                               maxRadius=100)
    img_circles = img.copy()
    if circles is not None:
        circles = np.uint16(np.around(circles))
        for i in circles[0, :]:
            cv2.circle(img_circles, (i[0], i[1]), i[2], (0, 255, 255), 2)
            cv2.circle(img_circles, (i[0], i[1]), 2, (0, 0, 255), 3)

    # Plot results
    plt.figure(figsize=(15, 10))
    plt.subplot(221), plt.imshow(cv2.cvtColor(img, cv2.COLOR_BGR2RGB)), plt.title('Original Image')
    plt.subplot(222), plt.imshow(cv2.cvtColor(img_lines, cv2.COLOR_BGR2RGB)), plt.title('Lines Detected')
    plt.subplot(223), plt.imshow(cv2.cvtColor(img_segments, cv2.COLOR_BGR2RGB)), plt.title('Segments Detected')
    plt.subplot(224), plt.imshow(cv2.cvtColor(img_circles, cv2.COLOR_BGR2RGB)), plt.title('Circles Detected')
    plt.show()


# Apply the function to an example image
hough_transform_methods("splitcircle_cut.png")
