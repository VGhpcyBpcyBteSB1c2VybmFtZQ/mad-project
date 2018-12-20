package como.example.noman.project;

public class Person {
    String UserName;
    String Email;
    String Password;

    public Person(String userName, String email, String password) {
        UserName = userName;
        Email = email;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}


