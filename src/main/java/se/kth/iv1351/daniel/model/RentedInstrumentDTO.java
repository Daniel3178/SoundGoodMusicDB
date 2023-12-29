package se.kth.iv1351.daniel.model;

public interface RentedInstrumentDTO extends InstrumentDTO
{
    int getRentId();
    String getStartRentingDate();
}
