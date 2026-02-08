package dongyang.ac.kr;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IpPrintServlet
 */
@WebServlet("/IpPrintServlet")
public class IpPrintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IpPrintServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface netint = nets.nextElement();
                
                // 무시할 인터페이스 예: loopback, 비활성화된 것 등
                if (!netint.isUp() || netint.isLoopback() || netint.isVirtual()) {
                    continue;
                }

                out.println("Interface: " + netint.getDisplayName());

                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!(inetAddress instanceof Inet4Address)) continue; // IPv4만 출력
                    out.println("  IP Address: " + inetAddress.getHostAddress());
                }
            }
        } catch (SocketException e) {
            out.println("Error retrieving network interfaces: " + e.getMessage());
        }

        out.close();
        
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
