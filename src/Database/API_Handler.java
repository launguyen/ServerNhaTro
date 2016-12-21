package Database;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONObject;

import com.google.gson.Gson;

public class API_Handler extends Thread {
	private Socket socket; // The accepted socket from the Webserver
	//public PrintStream out;
	public OutputStream out;
	public API_Handler(Socket s) {
		socket = s;
		start();
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			out = socket.getOutputStream();

			String s = in.readLine();
			System.out.println(s); // Log the request

			// Attempt to serve the file. Catch FileNotFoundException and
			// return an HTTP error "404 Not Found". Treat invalid requests
			// the same way.
			String filename = "";
			StringTokenizer st = new StringTokenizer(s);
			String command = st.nextToken();
			
			if (command.equalsIgnoreCase("GET")) {
				ArrayList<NhaTro> list = Utils.db.getAllNhaTro();
				Gson gson = new Gson();
				JSONObject newaction = new JSONObject();
				String listnhatro = gson.toJson(list);
				newaction.put("action", "resultgetAllNhaTro");
				newaction.put("data", listnhatro);
				String result = newaction.toString();
				out.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
				out.write("Content-Type: text/html\r\n".getBytes("UTF-8"));

				out.write("\r\n".getBytes("UTF-8"));
				out.write(result.getBytes("UTF-8"));
				out.close();
			}
			if (command.equalsIgnoreCase("POST")) {
				int contentLength = 0;
				while (!(s = in.readLine()).equals("")) {
					final String contentHeader = "Content-Length: ";
					if (s.startsWith(contentHeader)) {
						contentLength = Integer.parseInt(s.substring(contentHeader.length()));
					}
				}

				StringBuilder body = new StringBuilder();
				System.out.println("contentLength : " + contentLength);
				char[] buffer = new char[1024];
				int totallength = 0;
				int chars_read;
				while ((chars_read = in.read(buffer)) != -1) {
					totallength += chars_read;
					String xbody = new String(buffer);
					// System.out.println("xbody: " + totallength + " " +
					// xbody);
					body.append(xbody, 0, chars_read);
					// if (( xbody.indexOf("session_request") > -1 ) ||
					// totallength >= contentLength) break;
					if (totallength >= contentLength) {
						break;
					} else if (xbody.indexOf("session_request") > -1) {
						try // nếu tìm thầy chữ session_request nhưng đằng sau
							// chứ session_request ko có dấu } thì tức là chưa
							// kết thúc body, tiếp tục lặp tiếp
						{
							String endbody = xbody.substring(
									xbody.indexOf("session_request") + "session_request".length() + 8,
									xbody.indexOf("session_request") + "session_request".length() + 9);
							if (endbody.equals("}")) {
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				String body_of_post = body.toString();
				String result = Message(body_of_post);
			
				out.write("HTTP/1.1 200 OK\r\n".getBytes());
				out.write("Content-Type: text/html\r\n".getBytes());

				out.write("\r\n".getBytes());
				out.write(result.getBytes());
				out.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				socket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String Message(String Message) {
		String result = "";
		System.out.println(Message);
		JSONObject js = null;
		try {
			js = new JSONObject(Message);
		} catch (Exception e) {
			e.printStackTrace();

		}

		String action = js.getString("action");

		if (action.equals("getAllNhaTro")) {
			ArrayList<NhaTro> list = Utils.db.getAllNhaTro();
			Gson gson = new Gson();
			JSONObject newaction = new JSONObject();
			String listnhatro = gson.toJson(list);
			newaction.put("action", "resultgetAllNhaTro");
			newaction.put("data", listnhatro);
			result = newaction.toString();
		}
		if (action.equals("getNhatroTheoVitri")) {
			
			String vitri = js.getString("vitri");
			ArrayList<NhaTro> list = Utils.db.getNhatroTheoVitri(vitri);
			Gson gson = new Gson();
			JSONObject newaction = new JSONObject();
			String listnhatro = gson.toJson(list);
			newaction.put("action", "resultgetNhatroTheoVitri");
			newaction.put("data", listnhatro);
			result = newaction.toString();
		}
		if (action.equals("getChitietNhatro")) {
			String vitri = js.getString("vitri");
			NhaTro nhatro = Utils.db.getChitietNhatro(vitri);
			Gson gson = new Gson();
			JSONObject newaction = new JSONObject();
			String nt = gson.toJson(nhatro);
			newaction.put("action", "resultgetChitietNhatro");
			newaction.put("data", nt);
			result = newaction.toString();
		}

		return result;
	}
}
