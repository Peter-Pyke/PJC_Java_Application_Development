package Password;

/**
 * This is the Password class and contains the getPassword method which provides a way to pass the password to the
 * database during login.
 * */
public class Password {
    private static String password = "53688888427";

    public Password(String password) {
        this.password = password;
    }
    public void setPassword(String password) {this.password = password;}

    /**
     * This is the getPassword method and returns the private static String variable from the password class.
     * */
    public static String getPassword() {return password;}

}