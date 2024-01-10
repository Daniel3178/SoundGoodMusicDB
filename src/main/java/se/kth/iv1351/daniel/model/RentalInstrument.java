package se.kth.iv1351.daniel.model;

import se.kth.iv1351.daniel.model.dto1.RentedInstrumentDTO;

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

    public static class Builder
    {

        private int rentId;
        private int instrumentId;
        private String model;
        private String brand;
        private String startRentingDate;
        private String endRentingDate;
        private int quantity;
        private float price;

        public Builder()
        {
            this.rentId = -1;
            this.instrumentId = -1;
            this.model = null;
            this.brand = null;
            this.startRentingDate = null;
            this.endRentingDate = null;
            this.quantity = -1;
            this.price = -1;
        }

        public Builder setRentId(int rentId)
        {
            this.rentId = rentId;
            return this;
        }

        public Builder setInstrumentId(int instrumentId)
        {
            this.instrumentId = instrumentId;
            return this;
        }

        public Builder setModel(String model)
        {
            this.model = model;
            return this;
        }

        public Builder setBrand(String brand)
        {
            this.brand = brand;
            return this;
        }

        public Builder setStartRentingDate(String startRentingDate)
        {
            this.startRentingDate = startRentingDate;
            return this;
        }

        public Builder setEndRentingDate(String endRentingDate)
        {
            this.endRentingDate = endRentingDate;
            return this;
        }

        public Builder setQuantity(int quantity)
        {
            this.quantity = quantity;
            return this;
        }

        public Builder setPrice(float price)
        {
            this.price = price;
            return this;
        }

        public RentalInstrument build()
        {
            return new RentalInstrument(this);
        }
    }

    public RentalInstrument(Builder builder)
    {
        this.rentId = builder.rentId;
        this.instrumentId = builder.instrumentId;
        this.model = builder.model;
        this.brand = builder.brand;
        this.startRentingDate = builder.startRentingDate;
        this.endRentingDate = builder.endRentingDate;
        this.quantity = builder.quantity;
        this.price = builder.price;
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
