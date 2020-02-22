import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Coursework1 {

    private String filter;
    private InetAddress inet;

    public Coursework1(String filter, String hostname) {
        validateFilter(filter);
        InetAddress host = validateHostname(hostname);
        System.out.println(compareFilterAndHostname(filter, host));
    }

    private void validateFilter(String filter){
        //checks that the filter is a valid IPv4 address, or range of addresses with wildcards (‘*’)
        String ipv4Regex = "" +
                "(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)))|" +
                "(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(\\*))|" +
                "(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){2}(\\*\\.\\*))|" +
                "(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){1}(\\*\\.){2}\\*)";
        if(!filter.matches(ipv4Regex)){
            throw new java.lang.RuntimeException("Invalid ipv4 address.");
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
                throw new java.lang.RuntimeException("Hostname resolved to IPv6 address.");
            }
        }
        catch(UnknownHostException e){
            throw new java.lang.RuntimeException("Unknown hostname.");
        }
    }

    private boolean compareFilterAndHostname(String filter, InetAddress inet){
        //Returns true if the hostname either matches the filter, or belongs to the range of addresses specified by the filter false otherwise.
        String byteRegex = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        String ipv4 = inet.getHostAddress();
        ipv4 = ipv4.replace(".", "\\.");
        ipv4 = ipv4.replace("*",byteRegex);
        if(filter.matches(ipv4)){
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
            throw new java.lang.RuntimeException("Expected arguments not provided.");
        }
    }
}