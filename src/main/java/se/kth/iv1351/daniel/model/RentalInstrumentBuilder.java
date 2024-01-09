package se.kth.iv1351.daniel.model;

public class RentalInstrumentBuilder {

    private int rentId;
    private int instrumentId;
    private String model;
    private String brand;
    private String startRentingDate;
    private String endRentingDate;
    private int quantity;
    private float price;

    public RentalInstrumentBuilder(){
        this.rentId = -1;
        this.instrumentId = -1;
        this.model = null;
        this.brand = null;
        this.startRentingDate = null;
        this.endRentingDate = null;
        this.quantity = -1;
        this.price = -1;
    }

    public RentalInstrumentBuilder setRentId(int rentId) {
        this.rentId = rentId;
        return this;
    }

    public RentalInstrumentBuilder setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
        return this;
    }

    public RentalInstrumentBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public RentalInstrumentBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public RentalInstrumentBuilder setStartRentingDate(String startRentingDate) {
        this.startRentingDate = startRentingDate;
        return this;
    }

    public RentalInstrumentBuilder setEndRentingDate(String endRentingDate) {
        this.endRentingDate = endRentingDate;
        return this;
    }

    public RentalInstrumentBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public RentalInstrumentBuilder setPrice(float price) {
        this.price = price;
        return this;
    }

    public RentalInstrument buildRentalInstrument() {
        return new RentalInstrument(this.rentId,
                this.instrumentId, this.model, this.brand,
                this.startRentingDate, this.endRentingDate,
                this.quantity, this.price);
    }

}
