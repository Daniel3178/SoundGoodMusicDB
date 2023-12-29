package se.kth.iv1351.daniel.model;

public class RentalInstrument implements RentedInstrumentDTO
{
    private final int rentId;
    private final int instrumentId;
    private final String model;
    private final String brand;
    private String startRentingDate;
    private String endRentingDate;
    private int quantity;
    private final float price;

    public RentalInstrument(int rentId, int instrumentId, int quantity)
    {
        this.rentId = rentId;
        this.instrumentId = instrumentId;
        this.model = null;
        this.brand = null;
        this.startRentingDate = null;
        this.endRentingDate = null;
        this.quantity = quantity;
        this.price = 0;
    }
    public RentalInstrument( int instrumentId, int quantity)
    {
        this.rentId = -1;
        this.instrumentId = instrumentId;
        this.model = null;
        this.brand = null;
        this.startRentingDate = null;
        this.endRentingDate = null;
        this.quantity = quantity;
        this.price = 0;
    }

    public RentalInstrument(int rentId, int instrumentId, String model,
                            String brand, String start_renting_date, float price
    )
    {
        this.rentId = rentId;
        this.instrumentId = instrumentId;
        this.model = model;
        this.brand = brand;
        this.startRentingDate = start_renting_date;
        this.endRentingDate = null;
        this.quantity = -1;
        this.price = price;
    }


    public RentalInstrument(int instrumentId, String model, String brand, float price)
    {
        this.instrumentId = instrumentId;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.rentId = -1;
        this.startRentingDate = null;
        this.endRentingDate = null;
        this.quantity = -1;
    }

    public int getRentId()
    {
        return rentId;
    }

    public String getStartRentingDate()
    {
        return startRentingDate;
    }

    public void setStartRentingDate(String start_renting_date)
    {
        this.startRentingDate = start_renting_date;
    }

    public String getEndRentingDate()
    {
        return endRentingDate;
    }

    public void setEndRentingDate(String end_renting_date)
    {
        this.endRentingDate = end_renting_date;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void increaseQuantityByOne()
    {
        this.quantity++;
    }

    public void decreaseQuantityByOne()
    {
        this.quantity--;
    }

    @Override
    public int getInstrumentId()
    {
        return instrumentId;
    }

    @Override
    public String getModel()
    {
        return model;
    }

    @Override
    public String getBrand()
    {
        return brand;
    }

    @Override
    public float getPrice()
    {
        return price;
    }

}
