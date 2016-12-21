package PagePhongTro123;
import java.net.MalformedURLException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.stylesheets.DocumentStyle;
import org.jsoup.Jsoup;

import java.lang.Exception;

public class CaoPagePhongTro123 extends Thread{
	
	private String theURL;
	
	public CaoPagePhongTro123(String theURL){
		this.theURL = theURL;
	}
	
	public void run(){
		
		try
		{
			URL url = new URL(this.theURL);
			InputStream is = url.openStream();

			int ptr = 0;
			StringBuffer buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
			
			Document doc = Jsoup.parse(buffer.toString());
			Elements postLink = doc.getElementsByClass("post-link");
			for(int i = 0;i < postLink.size(); i++){
				if (i%2 == 0) {
					String link = postLink.get(i).attr("href");
					//System.out.println(link);
					CaoChiTietPhongTro123 cao = new CaoChiTietPhongTro123(link);
					cao.start();
					//break;
				}
			}
		}
		catch(Exception e){
			
		}
	}
}
