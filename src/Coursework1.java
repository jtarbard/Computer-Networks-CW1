import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Coursework1 {

    private String filter = "";
    private String hostname = "";
    private InetAddress inet;
    private Boolean match = Boolean.FALSE;


    public Coursework1(String argFilter, String argHostname) {
        //initialise fields
        setFilter(argFilter);
        setHostname(argHostname);
        setInetAddress();
        setMatch();
    }

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

    private void setHostname(String parHostname){
        hostname = parHostname;
    }

    private void setInetAddress(){
        //converts the hostname to an instance of InetAddress and checks hostname resolved to an IPv4 address.
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
