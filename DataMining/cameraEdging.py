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
    laplacian = cv2.Laplacian(src=frame, ddepth=cv2.CV_64F)
    laplacian = numpy.uint8(laplacian)
    cv2.imshow('Laplacian', laplacian)

    # canny edge detector
    edges = cv2.Canny(image=img_blur, threshold1=40, threshold2=70)
    cv2.imshow('Canny', edges)

    # sobel filter
    sobelx = cv2.Sobel(src=img_blur, ddepth=cv2.CV_64F, dx=1, dy=0, ksize=5)
    sobely = cv2.Sobel(src=img_blur, ddepth=cv2.CV_64F, dx=0, dy=1, ksize=5)
    sobelxy = cv2.Sobel(src=img_blur, ddepth=cv2.CV_64F, dx=1, dy=1, ksize=5)
    cv2.imshow('Sobel X', sobelx)
    cv2.imshow('Sobel Y', sobely)
    cv2.imshow('Sobel X Y', sobelxy)

    if cv2.waitKey(5) == ord('x'):
        break

camera.release()
cv2.destroyAllWindows()
