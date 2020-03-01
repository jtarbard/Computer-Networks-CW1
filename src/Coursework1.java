import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Coursework1 Class.
 * Reads in two arguments via the constructor: filter and hostname.
 * Provides methods that check the filter and hostname's validity against IPv4 standard.
 * Provides a method that returns true if the hostname either matches the filter or belongs to the range of addresses
 * specified by the filter.
 */
public class Coursework1 {

    private String filter = ""; //user inputted value that represents the IPv4 filter
    private String hostname = ""; //user inputted value that represents the hostname
    private InetAddress inet; //IPv4 InetAddress object resolved from the hostname field
    private Boolean match = Boolean.FALSE; //value that indicates whether the filter field and inet field host address match

    /**
     * Class constructor that initialises the Coursework1 fields filter,
     * hostname, inet and match.
     * @param argFilter     user string value that represents the IPv4 filter
     * @param argHostname   user string value that represents the hostname
     */
    public Coursework1(String argFilter, String argHostname) {
        //initialise fields
        setFilter(argFilter);
        setHostname(argHostname);
        setInetAddress();
        setMatch();
    }

    /**
     * Setter for the filter field.
     * Rejects invalid IPv4/wildcard addresses.
     * @param pFilter   user string value that represents the IPv4 filter
     */
    private void setFilter(String pFilter){
        //set filter
        filter = pFilter;

        //validate filter
        String ip[] = filter.split("\\.");

        if(ip.length > 4){
            throw new java.lang.RuntimeException("IPv4 address is too long: format should be 'a.b.c.d' where a, b, c, d are integers from 0 to 255 or wildcards '*'.");
        }
        else if(ip.length < 4){
            throw new java.lang.RuntimeException("IPv4 address is too short: format should be 'a.b.c.d' where a, b, c, d are integers from 0 to 255 or wildcards '*'.");
        }
        else if(filter.matches(".*[^\\d.\\*].*")){
            throw new java.lang.RuntimeException("IPv4 address contains invalid character(s): format should be 'a.b.c.d' where a, b, c, d are integers from 0 to 255 or wildcards '*'.");
        }
        else if(filter.matches(".*\\*\\.\\d.*")){
            throw new java.lang.RuntimeException("IPv4 address has invalid format: wildcard followed by an integer not permitted.");
        }
        else{
            for(String i : ip){
                if(!i.contains("*")){
                    if (Integer.parseInt(i) > 255) {
                        throw new java.lang.RuntimeException("IPv4 address has over-sized integer(s): integers should range from 0 to 255.");
                    }
                }
            }
        }
    }

    /**
     * Setter for the hostname field.
     * @param pHostname   user string value that represents the hostname
     */
    private void setHostname(String pHostname){
        hostname = pHostname;
    }

    /**
     * Setter for the inet field.
     * Utilises the field hostname to obtain the desired InetAddress.
     * Rejects hostnames that resolve to IPv6 addresses.
     */
    private void setInetAddress(){
        try {
            inet = InetAddress.getByName(hostname);
            if (inet instanceof Inet6Address) {
                throw new java.lang.RuntimeException("Hostname resolved to IPv6 address: IPv4 address required.");
            }
        }
        catch(UnknownHostException e){
            throw new java.lang.RuntimeException("IPv4 addressed could not be resolved: unknown hostname.");
        }
    }

    /**
     * Setter for the match field.
     */
    private void setMatch(){
        String ipv4 = inet.getHostAddress();
        //Returns true if the hostname either matches/belongs to the filter returns false otherwise.
        if(filter.matches("^(\\*).*")){
            match = true;
        }
        else if(ipv4.matches(filter)){
            match = true;
        }
        else{
            match = false;
        }
    }

    /**
     * Getter for the match field.
     * @return  boolean value that indicates whether the filter and inet host address match
     */
    public boolean getMatch(){
        return match;
    }

    public static void main(String[] args) {
        try {
            Coursework1 lookup = new Coursework1(args[0], args[1]);
            System.out.println(lookup.getMatch());
        }
        catch(ArrayIndexOutOfBoundsException e){
            throw new java.lang.RuntimeException("Expected arguments not provided: IPv4 filter and hostname expected.");
        }
    }
}
