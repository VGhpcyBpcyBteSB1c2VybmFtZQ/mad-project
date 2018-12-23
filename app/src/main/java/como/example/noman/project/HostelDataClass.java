package como.example.noman.project;

public class HostelDataClass {
    public String hostelName;
    public String hostelAddress;
    public String hostelCity;
    public String hostelExtras;
    public int no_rooms;
    public int no_floors;
    public String owner_email;

    HostelDataClass(String _name, String _address, String _city, String _extras, int _rooms, int _floors, String _owner)
    {
        hostelName = _name;
        hostelAddress = _address;
        hostelCity = _city;
        hostelExtras = _extras;
        no_rooms = _rooms;
        no_floors = _floors;
        owner_email = _owner;
    }

}
