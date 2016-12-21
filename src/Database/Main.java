package Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import java.util.Timer;

import com.google.gson.Gson;

import PagePhongTro123.CaoPagePhongTro123;

public class Main {
	Timer myTime;

	public Main() {
		myTime = new Timer();
		Baothuc bt = new Baothuc();
		Date current = new Date();
		myTime.schedule(bt, current, 12 * 60 * 60 * 1000);
	}

	public static void main(String[] args) {
		new Main();
		Utils.db = new DatabaseManager();
		if (Utils.db.ConnectDatabase()) {
			System.out.println("Dang tai du lieu...");
		}

		new Server(8012).start();
		while (true) {

		}
	}

	class Baothuc extends TimerTask {

		@Override
		public void run() {

			DatabaseManager dm = new DatabaseManager();
			
			System.out.println("Bat dau lay du lieu Ho Chi Minh");
			/*for (int i = 1; i < 3; i++) {
				String pageLink = "https://phongtro123.com/tim-kiem/page/" + i + "?q&type&tinh=90&quan&phuongxa&duong&price=0&dientich=0&doituong=0";
				CaoPagePhongTro123 page = new CaoPagePhongTro123(pageLink);
				page.start();
			}*/

/*			System.out.println("Bat dau lay du lieu Ha Noi");
			for (int i = 1; i < 3; i++) {
				String pageLink = "https://phongtro123.com/tim-kiem/page/" + i + "?q&type&tinh=41&quan&phuongxa&duong&price=0&dientich=0&doituong=0";
				CaoPagePhongTro123 page = new CaoPagePhongTro123(pageLink);
				page.start();
			}

			System.out.println("Bat dau lay du lieu Da Nang");
			for (int i = 1; i < 3; i++) {
				String pageLink = "https://phongtro123.com/tim-kiem/page/" + i + "?q&type&tinh=72&quan&phuongxa&duong&price=0&dientich=0&doituong=0";
				CaoPagePhongTro123 page = new CaoPagePhongTro123(pageLink);
				page.start();
			}*/
			
			/*for (int i = 1; i < 3; i++) {
				String pageLink = "https://phongtro123.com/tim-kiem/page/" + i + "?q&type&tinh=90&quan&phuongxa&duong&price=0&dientich=0&doituong=0";
				CaoPagePhongTro123 page = new CaoPagePhongTro123(pageLink);
				page.start();
			}*/
			dm.xoaBotNhatro();
		}
	}
}
