package shop.pitstopauto.android.models;

public class Car {

    private int id;
    private String uuid;
    private String vehicle_make;
    private String vehicle_model;
    private String vehicle_year;
    private String vehicle_trim;
    private String vehicle_style;
    private String chasis_number;
    private String next_service;
    private String milage;
    private String created_at;
    private String updated_at;
    private int created_by_id;
    public boolean expanded = false;
    public boolean parent = false;

    public Car(int id,  String uuid, String vehicle_make, String vehicle_model, String vehicle_year, String vehicle_trim, String vehicle_style, String chasis_number,String next_service, String milage, String created_at, String updated_at, int created_by_id) {

        this.id = id;
        this.uuid = uuid;
        this.vehicle_make = vehicle_make;
        this.vehicle_model = vehicle_model;
        this.vehicle_year = vehicle_year;
        this.vehicle_trim = vehicle_trim;
        this.vehicle_style = vehicle_style;
        this.chasis_number = chasis_number;
        this.next_service = next_service;
        this.milage = milage;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by_id = created_by_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getVehicle_year() {
        return vehicle_year;
    }

    public void setVehicle_year(String vehicle_year) {
        this.vehicle_year = vehicle_year;
    }

    public String getVehicle_trim() {
        return vehicle_trim;
    }

    public void setVehicle_trim(String vehicle_trim) {
        this.vehicle_trim = vehicle_trim;
    }

    public String getVehicle_style() {
        return vehicle_style;
    }

    public void setVehicle_style(String vehicle_style) {
        this.vehicle_style = vehicle_style;
    }

    public String getChasis_number() {
        return chasis_number;
    }

    public void setChasis_number(String chasis_number) {
        this.chasis_number = chasis_number;
    }

    public String getNext_service() {
        return next_service;
    }

    public void setNext_service(String next_service) {
        this.next_service = next_service;
    }

    public String getMilage() {
        return milage;
    }

    public void setMilage(String milage) {
        this.milage = milage;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCreated_by_id() {
        return created_by_id;
    }

    public void setCreated_by_id(int created_by_id) {
        this.created_by_id = created_by_id;
    }


}

