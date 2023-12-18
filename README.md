# CS 1501 Project 3: Car Selection Helper

## Goal
The objective of this project is to create a priority queue-based application to assist users in selecting a car to purchase, prioritizing by either price or mileage, with efficient updating and retrieval capabilities.

## High-level Description
This application uses a priority queue-based data structure to store car objects, which can be sorted by two attributes: price and mileage. The data structure is indexed to allow for efficient updates. Users can enter details about cars they are considering and retrieve cars with the lowest price or mileage, overall or by make and model.

## Specifications

### Car Class
- Implemented according to `Car_Inter` interface.
- Tracks VIN, make, model, price, mileage, and color.
- Constructor accepts details in the specified order.

### CarsPQ Data Structure
- Built around heaps with indirection for indexability.
- Implements `CarsPQ_Inter` interface.
- Supports operations for adding, retrieving, updating, and removing cars.
- Initialized with a list of cars from a given file.
- Operations have a O(lg n) runtime, with resizing allowed at O(n) time.

### Provided Resources
- Test data available in `./app/src/test/resources/cars.txt`.
- Initial list of cars can be loaded from a file.

## Additional Notes/Hints
- You may use code from the textbook authors as needed, except for JCL classes (exceptions apply).
- Select your indirection data structure carefully to meet key storage and operational requirements.
- Be mindful of the efficiency of operations, particularly with heap-backed data structures.