package smart.parking.solution;

public class User
{
    String Email;
    String Password;

    public User(String email, String password)
    {
        this.Email = email;
        this.Password = password;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {
        Password = password;
    }
}

