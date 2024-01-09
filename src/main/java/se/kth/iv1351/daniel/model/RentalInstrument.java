package se.kth.iv1351.daniel.model;

import se.kth.iv1351.daniel.model.DTO.RentedInstrumentDTO;

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
    
    public RentalInstrument(int rentId, int instrumentId, String model, String brand, 
    String startRentingDate, String endRentingDate, int quantity, float price) {
        this.rentId = rentId;
        this.instrumentId = instrumentId;
        this.model = model;
        this.brand = brand;
        this.startRentingDate = startRentingDate;
        this.endRentingDate = endRentingDate;
        this.quantity = quantity;
        this.price = price;
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
    public int getRentId()
    {
        return rentId;
    }

    @Override
    public String getStartRentingDate()
    {
        return startRentingDate;
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
