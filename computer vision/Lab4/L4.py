import cv2
import numpy as np
import os
import glob


def compute_histogram(image, bins=(256, 256, 256)):
    """Compute the color histogram for an image."""
    histogram = cv2.calcHist([image], [0, 1, 2], None, bins, [0, 256, 0, 256, 0, 256])
    return cv2.normalize(histogram, histogram).flatten()


def compare_histograms(query_hist, image_hists, metrics):
    """Compare the query histogram with other histograms using specified metrics."""
    results = {metric: [] for metric in metrics}

    for metric in metrics:
        for hist in image_hists:
            score = cv2.compareHist(query_hist, hist, metric)
            results[metric].append(score)

    return results


def normalize_results(results, query_score):
    """Normalize the results against the query score (comp(Q, Q))."""
    normalized_results = {}
    for metric, scores in results.items():
        normalized_results[metric] = [score / query_score if query_score != 0 else 0 for score in scores]
    return normalized_results


def main(image_folder, query_image_path):
    # Load all images
    image_paths = glob.glob(os.path.join(image_folder, "*"))
    images = [cv2.imread(img_path) for img_path in image_paths]
    query_image = cv2.imread(query_image_path)

    # Ensure valid images are loaded
    assert query_image is not None, "Query image could not be loaded."
    assert len(images) > 1, "Not enough images in the dataset."

    # Compute histograms for all images in the dataset
    # Uses Correlation(<-1.0), Chi-Square(0.0), Intersection(ma and Bhattacharyya Distance
    bins_list = [(256, 256, 256), (64, 64, 64), (32, 32, 32)]  # Different bin sizes
    metrics = [cv2.HISTCMP_CORREL, cv2.HISTCMP_CHISQR, cv2.HISTCMP_INTERSECT, cv2.HISTCMP_BHATTACHARYYA]

    for bins in bins_list:
        print(f"\n--- Results for bins: {bins[0]} ---")

        # Compute histograms
        query_hist = compute_histogram(query_image, bins)
        image_hists = [compute_histogram(img, bins) for img in images]

        # Compare histograms
        results = compare_histograms(query_hist, image_hists, metrics)

        # Normalize results and display
        for metric in metrics:
            query_score = results[metric][0]  # comp(Q, Q)
            normalized = normalize_results({metric: results[metric]}, query_score)
            print(f"Metric: {metric}")
            print(f"comp(Q, Q): {query_score:.4f}")
            print("Normalized Scores:")
            for i, score in enumerate(normalized[metric]):
                print(f"I{i + 1}: {score:.4f}")


if __name__ == "__main__":
    # Path to folder containing dataset
    image_folder = "./imagedataset"  # Replace with your dataset folder path
    query_image_path = "./imagedataset/image10.jpg"  # Replace with query image path

    # Run the application
    main(image_folder, query_image_path)
