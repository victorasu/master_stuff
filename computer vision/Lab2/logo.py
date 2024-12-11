import cv2

videoCapture = cv2.VideoCapture(0)
frame_width = int(videoCapture.get(cv2.CAP_PROP_FRAME_WIDTH))
frame_height = int(videoCapture.get(cv2.CAP_PROP_FRAME_HEIGHT))
print(f"Width: {frame_width}, Height: {frame_height}")

# Load the logo with alpha channel and resize
logo = cv2.imread("opencv.png", cv2.IMREAD_UNCHANGED)
logo = cv2.resize(logo, (frame_width, frame_height))

# Extract the alpha mask of the logo (non-transparent pixels)
mask = logo[:, :, 3] != 0
logo_rgb = logo[:, :, :3]  # Get RGB channels of the logo (excluding alpha)

while True:
    ret, frame = videoCapture.read()

    if not ret:
        break

    # Overlay the logo on the top-left corner of the frame, applying the mask
    frame[0:frame_height, 0:frame_width][mask] = logo_rgb[mask]

    # Show the modified frame
    cv2.imshow('test', frame)

    # Break the loop on ESC key press
    if cv2.waitKey(1) == 27:
        print("break")
        break

# Release resources and close windows
videoCapture.release()
cv2.destroyAllWindows()
