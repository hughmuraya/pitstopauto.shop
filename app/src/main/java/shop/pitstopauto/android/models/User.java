package shop.pitstopauto.android.models;

public class User {
    private String auth_token;
    private String first_name;
    private String surname;
    private String email;
    private String msisdn;
    private int id;
    private int role_id;
    private String role_name;
    private int code;


    public User(String auth_token, String first_name, String surname, String email, String msisdn, int id, int role_id, String role_name,int code) {
        this.auth_token = auth_token;
        this.first_name = first_name;
        this.surname = surname;
        this.email = email;
        this.msisdn = msisdn;
        this.id = id;
        this.role_id = role_id;
        this.role_name = role_name;
        this.code = code;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
