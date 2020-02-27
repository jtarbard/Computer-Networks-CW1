import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Coursework1 {

    public Coursework1(String filter, String hostname) {
        validateFilter(filter);
        InetAddress host = validateHostname(hostname);
        System.out.println(compareFilterAndHostname(filter, host));
    }

    private void validateFilter(String filter){
        String ip[] = filter.split("\\.");
        //validates IPv4 address
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

    private InetAddress validateHostname(String hostname){
        //converts the hostname to an instance of InetAddress and checks hostname resolved to an IPv4 address.
        try {
            InetAddress inet = InetAddress.getByName(hostname);
            if (inet instanceof Inet4Address) {
                return inet;
            }
            else {
                throw new java.lang.RuntimeException("Hostname resolved to IPv6 address: IPv4 address required.");
            }
        }
        catch(UnknownHostException e){
            throw new java.lang.RuntimeException("IPv4 addressed could not be resolved: unknown hostname.");
        }
    }

    private boolean compareFilterAndHostname(String filter, InetAddress inet){
        //Returns true if the hostname either matches/belongs to the filter returns false otherwise.
        String ipv4 = inet.getHostAddress();
        if(ipv4.matches(filter)){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            Coursework1 lookup = new Coursework1(args[0], args[1]);
        }
        catch(ArrayIndexOutOfBoundsException e){
            throw new java.lang.RuntimeException("Expected arguments not provided: IPv4 filter and hostname expected.");
        }
    }
}
