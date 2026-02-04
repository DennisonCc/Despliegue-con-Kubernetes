package ec.edu.espe.zone_core.zone_core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class IpAddressUtil {

    
    public static String getRealIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                
                if (ni.isLoopback()) continue;
                
                // Filtrar adaptadores virtuales
                String name = ni.getDisplayName().toLowerCase();
                if (name.contains("vmware") || name.contains("virtualbox") || 
                    name.contains("hyper-v") || name.contains("vethernet")) {
                    continue;
                }
                
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // Solo IPv4
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(':') == -1) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "No disponible";
    }

   
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "No disponible";
        }
    }

    public static void main(String[] args) {
        System.out.println("Host: " + getHostName());
        System.out.println("IP Real: " + getRealIpAddress());
    }
}
