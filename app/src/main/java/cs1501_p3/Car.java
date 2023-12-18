package cs1501_p3;


public class Car implements Car_Inter
{
    private String VIN_num;
    private String carMake;
    private String carModel;
    private int carPrice;
    private int mileage;
    private String carColor;

    public Car(String VIN, String make, String model, int price, int miles, String color)
    {
        VIN_num = VIN;
        carMake = make;
        carModel = model;
        carPrice = price;
        mileage = miles;
        carColor = color;
    }


    /**
     * Getter for the VIN attribute
     *
     * @return String The VIN
     */
    public String getVIN()
    {
        return VIN_num;
    }

    /**
     * Getter for the make attribute
     *
     * @return String The make
     */
    public String getMake()
    {
        return carMake;
    }

    /**
     * Getter for the model attribute
     *
     * @return String The model
     */
    public String getModel()
    {
        return carModel;
    }

    /**
     * Getter for the price attribute
     *
     * @return String The price
     */
    public int getPrice()
    {
        return carPrice;
    }

    /**
     * Getter for the mileage attribute
     *
     * @return String The mileage
     */
    public int getMileage()
    {
        return mileage;
    }

    /**
     * Getter for the color attribute
     *
     * @return String The color
     */
    public String getColor()
    {
        return carColor;
    }

    /**
     * Setter for the price attribute
     *
     * @param newPrice The new Price
     */
    public void setPrice(int newPrice)
    {
        carPrice = newPrice;
    }

    /**
     * Setter for the mileage attribute
     *
     * @param newMileage The new Mileage
     */
    public void setMileage(int newMileage)
    {
        mileage = newMileage;
    }

    /**
     * Setter for the color attribute
     *
     * @param newColor The new color
     */
    public void setColor(String newColor)
    {
        carColor = newColor;
    }
}