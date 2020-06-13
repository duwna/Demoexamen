package database.entities;

public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthday;
    private String registrationDate;
    private String email;
    private String phone;
    private int genderCode;
    private String photoPath;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday='" + birthday + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", genderCode=" + genderCode +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }

    public Client(int id, String firstName, String lastName, String patronymic, String birthday,
                  String registrationDate, String email, String phone, int genderCode, String photoPath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
        this.email = email;
        this.phone = phone;
        this.genderCode = genderCode;
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(int genderCode) {
        this.genderCode = genderCode;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String[] toArray() {
        return new String[]{
                String.valueOf(id),
                firstName,
                lastName,
                patronymic,
                birthday,
                registrationDate,
                email,
                phone,
                String.valueOf(genderCode),
                photoPath,
        };
    }

    public static final String ID = "ID";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String Patronymic = "Patronymic";
    public static final String Birthday = "Birthday";
    public static final String RegistrationDate = "RegistrationDate";
    public static final String Email = "Email";
    public static final String Phone = "Phone";
    public static final String GenderCode = "GenderCode";
    public static final String PhotoPath = "PhotoPath";

}
