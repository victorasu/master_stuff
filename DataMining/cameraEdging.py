import cv2
import numpy

camera = cv2.VideoCapture(0)

while True:
    _, frame = camera.read()

    cv2.imshow('Camera', frame)

    img_gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    img_blur = cv2.GaussianBlur(img_gray, (3, 3), 0)

    cv2.imshow('Blurred', img_blur)

    # laplacian image filter
    laplacian = cv2.Laplacian(frame, cv2.CV_64F)
    laplacian = numpy.uint8(laplacian)
    cv2.imshow('Laplacian', laplacian)

    # canny edge detection
    edges = cv2.Canny(img_blur, 40, 70)
    cv2.imshow('Canny', edges)

    if cv2.waitKey(5) == ord('x'):
        break

camera.release()
cv2.destroyAllWindows()
