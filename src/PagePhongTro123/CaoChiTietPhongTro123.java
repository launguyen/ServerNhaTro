package PagePhongTro123;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Database.DatabaseManager;
import Maps.GetLatLong;

public class CaoChiTietPhongTro123 extends Thread {
	
	GetLatLong getll = new GetLatLong();
	private String theURL;

	public CaoChiTietPhongTro123(String theURL) {
		this.theURL = theURL;
	}

	public void run() {

		try {
			URL url = new URL(this.theURL);
			URLConnection urlConn = url.openConnection();
			Charset charset = Charset.forName("UTF-8");
			InputStreamReader stream = new InputStreamReader(urlConn.getInputStream(), charset);
			//InputStream is = url.openStream();

			int ptr = 0;
			StringBuffer buffer = new StringBuffer();
			while ((ptr = stream.read()) != -1) {
				buffer.append((char) ptr);
			}

			Document doc = Jsoup.parse(buffer.toString());
			Elements thongtin = doc.getElementsByClass("summary_item_info");
			Elements hinhs = doc.getElementsByTag("img");
			Elements mota = doc.getElementsByClass("block-content-2");
			Element latlong = doc.getElementById("maps_content");

			String Gia_Tien = thongtin.get(8).text();
			String Dien_Tich = thongtin.get(7).text();
			String Address = thongtin.get(0).text();
			String Lat_Long = latlong.attr("data-lat") + "," + latlong.attr("data-long"); 
			String SDT = thongtin.get(4).text();
			String Time = thongtin.get(2).text();
			String Mota = mota.get(0).text();
			String Link_hinh = "";

			for (int i = 0; i < hinhs.size(); i++) {
				String hinhRoot = hinhs.get(i).attr("src");
				if (hinhRoot.indexOf("url=") != -1) {
					String[] linkhinh = hinhRoot.split("url=");
					//System.out.println(hinhRoot);
					Link_hinh += linkhinh[1] + ",";
				}
			}

			/*System.out.println("Gia tien: " + Gia_Tien + "\n" + "Dien tich: " + Dien_Tich + "\n" + "Dia chi: " + Address
					+ "\n" + "SDT: " + SDT + "\n" + "Thoi gian: " + Time + "\n" + "Link hinh: " + Link_hinh + "\n"
					+ "Mo ta: " + Mota + "\n" + "Lat Long: " + Lat_Long + "\n");*/
			DatabaseManager dm = new DatabaseManager();
			dm.updateNhatro(Time, Lat_Long);
			if (dm.kiemTraTonTai(Lat_Long) == false && Time.length() != 13) {
				dm.insertNhatro(Gia_Tien, Dien_Tich, Address, Lat_Long, SDT, Time, Mota, Link_hinh);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
