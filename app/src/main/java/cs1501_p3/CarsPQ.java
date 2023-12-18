package cs1501_p3;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.NoSuchElementException;


public class CarsPQ implements CarsPQ_Inter
{
    private static final int INITIAL_CAPACITY = 100;
    private Car[] priceHeap;
    private Car[] mileageHeap;
    private CustomHashMap<String, Car> vinToCar;
    private CustomHashMap<String, Integer> vinToIndex;
    private int size;

    public CarsPQ(String fileName)
    {
        priceHeap = new Car[INITIAL_CAPACITY];
        mileageHeap = new Car[INITIAL_CAPACITY];
        vinToCar = new CustomHashMap<>(INITIAL_CAPACITY);
        vinToIndex = new CustomHashMap<>(INITIAL_CAPACITY);
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) 
            {
                String data = myReader.nextLine();
                if(data.trim().isEmpty() || data.trim().startsWith("#"))
                {
                    continue;
                }
                String [] splitData = data.split(":");
                if(splitData.length < 6)
                {
                    continue;
                }
                Car car = new Car(splitData[0], splitData[1], splitData[2], 
                                Integer.parseInt(splitData[3]), 
                                Integer.parseInt(splitData[4]), 
                                splitData[5]);
                add(car);
            }
            myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    /**
     * Add a new Car to the data structure
     * Should throw an `IllegalStateException` if there is already car with the
     * same VIN in the datastructure.
     *
     * @param c Car to be added to the data structure
     */
    public void add(Car c) throws IllegalStateException
    {
        if(vinToCar.contains(c.getVIN()))
        {
            throw new IllegalStateException();
        }

        addToHeap(priceHeap, c, (a, b) -> a.getPrice() - b.getPrice());
        addToHeap(mileageHeap, c, (a, b) -> a.getMileage() - b.getMileage());
        vinToCar.put(c.getVIN(), c);
        size++;
    }

    private void addToHeap(Car[] heap, Car c, Comparator<Car> comparator) 
    {
        // Resize the heap if it's full
        if (size(heap) == heap.length) 
        {
            resizeHeap(heap);
        }

        // Insert the car at the end
        int index = size(heap);
        heap[index] = c;

        // Sift up to maintain heap property
        while (index > 0 && comparator.compare(heap[index], heap[parent(index)]) < 0) 
        {
            swap(heap, index, parent(index));
            index = parent(index);
        }
    }

    private void resizeHeap(Car[] heap) 
    {
        Car[] newHeap = new Car[heap.length * 2];
        for (int i = 0; i < heap.length; i++) 
        {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    private void swap(Car[] heap, int i, int j) 
    {
        Car temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int parent(int index) 
    {
        return (index - 1) / 2;
    }

    private int leftChild(int index) 
    {
        return 2 * index + 1;
    }

    private int rightChild(int index)
    {
        return 2 * index + 2;
    }

    private int size(Car[] heap) 
    {
        int count = 0;
        for (int i = 0; i < heap.length; i++)
        {
            if (heap[i] != null) 
            {
                count++;
            } else 
            {
                break;
            }
        }
        return count;
    }

    /**
     * Retrieve a new Car from the data structure
     * Should throw a `NoSuchElementException` if there is no car with the
     * specified VIN in the datastructure.
     *
     * @param vin VIN number of the car to be updated
     */
    public Car get(String vin) throws NoSuchElementException
    {
        if(!vinToCar.contains(vin))
        {
            throw new NoSuchElementException();
        }
        return vinToCar.get(vin);
    }

    /**
     * Update the price attribute of a given car
     * Should throw a `NoSuchElementException` if there is no car with the
     * specified VIN in the datastructure.
     *
     * @param vin      VIN number of the car to be updated
     * @param newPrice The updated price value
     */
    public void updatePrice(String vin, int newPrice) throws NoSuchElementException
    {
        if(!vinToCar.contains(vin))
        {
            throw new NoSuchElementException();
        }
        Car car = vinToCar.get(vin);
        car.setPrice(newPrice);
        int index = vinToIndex.get(vin);

        siftDown(priceHeap, index, (a, b) -> a.getPrice() - b.getPrice());
        siftUp(priceHeap, index, (a, b) -> a.getPrice() - b.getPrice());
    }

    /**
     * Update the mileage attribute of a given car
     * Should throw a `NoSuchElementException` if there is not car with the
     * specified VIN in the datastructure.
     *
     * @param vin        VIN number of the car to be updated
     * @param newMileage The updated mileage value
     */
    public void updateMileage(String vin, int newMileage) throws NoSuchElementException
    {
        if(!vinToCar.contains(vin))
        {
            throw new NoSuchElementException();
        }
        Car car = vinToCar.get(vin);
        car.setMileage(newMileage);
        int index = vinToIndex.get(vin);

        siftDown(mileageHeap, index, (a, b) -> a.getMileage() - b.getMileage());
        siftUp(mileageHeap, index, (a, b) -> a.getMileage() - b.getMileage());
    }

    /**
     * Update the color attribute of a given car
     * Should throw a `NoSuchElementException` if there is not car with the
     * specified VIN in the datastructure.
     *
     * @param vin      VIN number of the car to be updated
     * @param newColor The updated color value
     */
    public void updateColor(String vin, String newColor) throws NoSuchElementException
    {
        Car car = vinToCar.get(vin);
        if(car == null)
        {
            throw new NoSuchElementException();
        }
        
        car.setColor(newColor);
    }

    private void siftUp(Car[] heap, int index, Comparator<Car> comparator)
    {
        while(index > 0)
        {
            int parentIndex = parent(index);

            if(comparator.compare(heap[index], heap[parentIndex]) < 0)
            {
                swap(heap, index, parentIndex);
                index = parentIndex;
            }
            else
            {
                break;
            }
        }
    }

    private void siftDown(Car[] heap, int index, Comparator<Car> comparator) 
    {
        int leftChildIndex, rightChildIndex, smallerChildIndex;

        while (index < size(heap)) 
        {
            leftChildIndex = leftChild(index);
            rightChildIndex = rightChild(index);
            smallerChildIndex = index;

            if (leftChildIndex < size(heap) && comparator.compare(heap[leftChildIndex], heap[smallerChildIndex]) < 0) 
            {
                smallerChildIndex = leftChildIndex;
            }

            if (rightChildIndex < size(heap) && comparator.compare(heap[rightChildIndex], heap[smallerChildIndex]) < 0) 
            {
                smallerChildIndex = rightChildIndex;
            }

            if (smallerChildIndex != index)
            {
                swap(heap, index, smallerChildIndex);
                index = smallerChildIndex;
            }
            else 
            {
                break;  
            }
        }   
    }   

    /**
     * Remove a car from the data structure
     * Should throw a `NoSuchElementException` if there is not car with the
     * specified VIN in the datastructure.
     *
     * @param vin VIN number of the car to be removed
     */
    public void remove(String vin) throws NoSuchElementException
    {
        Car carToRemove = vinToCar.get(vin);

        if(!vinToCar.contains(vin))
        {
            throw new NoSuchElementException();
        }

        vinToCar.remove(vin);

        removeCarFromHeap(priceHeap, carToRemove, (a, b) -> a.getPrice() - b.getPrice());
        removeCarFromHeap(mileageHeap, carToRemove, (a, b) -> a.getMileage() - b.getMileage());
        size--;
    }


    private void removeCarFromHeap(Car[] heap, Car car, Comparator<Car> comparator)
    {
        int carIndex = -1;
        for(int i = 0; i < size(heap); i++)
        {
            if(heap[i].getVIN().equals(car.getVIN()))
            {
                carIndex = i;
                break;
            }
        }

        if(carIndex == -1)
        {
            return;
        }

        swap(heap, carIndex, size(heap) - 1);
        heap[size(heap) - 1] = null;

        if(carIndex > 0 && comparator.compare(heap[carIndex], heap[parent(carIndex)]) < 0)
        {
            siftUp(heap, carIndex, comparator);
        }
        else
        {
            siftDown(heap, carIndex, comparator);
        }
    }

    /**
     * Get the lowest priced car (across all makes and models)
     * Should return `null` if the data structure is empty
     *
     * @return Car object representing the lowest priced car
     */
    public Car getLowPrice()
    {
        if(size == 0 || priceHeap[0] == null)
        {
            return null;
        }
        return priceHeap[0];
    }

    /**
     * Get the lowest priced car of a given make and model
     * Should return `null` if the data structure is empty
     *
     * @param make  The specified make
     * @param model The specified model
     * 
     * @return Car object representing the lowest priced car
     */
    public Car getLowPrice(String make, String model)
    {
        Car lowPriceCar = null;
        for (int i = 0; i < size; i++) 
        {
            Car current = priceHeap[i];
            if (current.getMake().equalsIgnoreCase(make) && current.getModel().equalsIgnoreCase(model)) 
            {
                if (lowPriceCar == null || current.getPrice() < lowPriceCar.getPrice())
                {
                    lowPriceCar = current;
                }
            }
        }
        return lowPriceCar;
    }

    /**
     * Get the car with the lowest mileage (across all makes and models)
     * Should return `null` if the data structure is empty
     *
     * @return Car object representing the lowest mileage car
     */
    public Car getLowMileage()
    {
        return size > 0 ? mileageHeap[0] : null;
    }

    /**
     * Get the car with the lowest mileage of a given make and model
     * Should return `null` if the data structure is empty
     *
     * @param make  The specified make
     * @param model The specified model
     *
     * @return Car object representing the lowest mileage car
     */
    public Car getLowMileage(String make, String model)
    {
        Car lowMileageCar = null;
        for (int i = 0; i < size; i++) 
        {
            Car current = mileageHeap[i];
            if (current.getMake().equalsIgnoreCase(make) && current.getModel().equalsIgnoreCase(model)) 
            {
                if (lowMileageCar == null || current.getMileage() < lowMileageCar.getMileage()) 
                {
                    lowMileageCar = current;
                }
            }
        }
        return lowMileageCar;
    }
}


