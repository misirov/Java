import java.net.*;
import java.io.*;
import java.util.*;
 
 
 
public class IP_class {
 
        // always need to handle exceptions
        public static void main(String [] args) throws UnknownHostException, SocketException, IOException {
            InetAddress candidateAddress; // instantiate object
 
            candidateAddress = InetAddress.getLocalHost(); // resolve localhost
            System.out.println("host name: " + candidateAddress.getHostName()); // get localhost name
            System.out.println("loopback address: " + InetAddress.getLoopbackAddress()); // get loopback address
 
            URL url = new URL("http://whatismyip.akamai.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('n');
                }
                urlConnection.disconnect();
                System.out.println("Public IP: "+ total);
            }finally {
                urlConnection.disconnect();
            }
 
            // loop through the device network interfaces
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
                NetworkInterface ni = (NetworkInterface) interfaces.nextElement();
 
                for (Enumeration<InetAddress> inetAddrs = ni.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
 
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            System.out.println("IP address: "+ inetAddr);
 
                        } // Removed error handling for convenience and readability. https://www.codota.com/code/query/java.net@InetAddress+java.net@NetworkInterface
                    }
                }
            }
        }
    }