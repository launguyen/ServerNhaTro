package Database;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author launguyen
 */
public class DatabaseManager extends MySQLHelper {

	//static final String DB_URL = "jdbc:mysql://localhost/nhatro?useUnicode=true&characterEncoding=utf-8";
	static final String DB_URL = "jdbc:mysql://27.118.28.160:3306/nhatro?useUnicode=true&characterEncoding=utf-8";

	// Database credentials
	static final String USER = "remotedz";
	static final String PASS = "o8rXJD1eilByzLwj";

	public DatabaseManager(String DB_URL1, String USER1, String PASS1) {
		super(DB_URL1, USER1, PASS1);

	}

	public DatabaseManager() {
		super(DB_URL, USER, PASS);
	}

	public boolean ConnectDatabase() {
		return Connect();
	}

	//codeGet
	public ArrayList<NhaTro> getAllNhaTro() {
		ArrayList<NhaTro> listNhaTro = new ArrayList<NhaTro>();
		String sql = "select * from tblnhatro";
		ResultSet rs;
		try {
			rs = Select(sql);
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String Gia_Tien = rs.getString("GIA_TIEN");
				String Dien_Tich = rs.getString("DIEN_TICH");
				String Address = rs.getString("ADDRESS");
				String Lat_Long = rs.getString("LAT_LONG");
				String SDT = rs.getString("SDT");
				String Time = rs.getString("TIME");
				String Mota = rs.getString("MOTA");
				String Link_hinh = rs.getString("LINK_HINH");

				NhaTro NT = new NhaTro(ID, Gia_Tien, Dien_Tich, Address, Lat_Long, SDT, Time, Mota, Link_hinh);
				listNhaTro.add(NT);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
		return listNhaTro;
	}

	public ArrayList<NhaTro> getNhatroTheoVitri(String latlong) {
		double lat1 = 0;
		double lat2 = 0;
		double lng1 = 0;
		double lng2 = 0;

		String[] arr = latlong.split(",");
		lat1 = Double.parseDouble(arr[0]);
		lng1 = Double.parseDouble(arr[1]);

		ArrayList<NhaTro> listNhaTro = new ArrayList<NhaTro>();
		String sql = "select * from tblnhatro";
		ResultSet rs;
		try {
			rs = Select(sql);
			while (rs.next()) {
				int ID;
				String Gia_Tien, Dien_Tich, Address, Lat_Long, SDT, Time, Mota, Link_hinh;
				try {
					ID = rs.getInt("ID");
					Gia_Tien = rs.getString("GIA_TIEN");
					Dien_Tich = rs.getString("DIEN_TICH");
					Address = new String(rs.getString("ADDRESS").getBytes("ISO-8859-1"), "UTF-8") ;
					Lat_Long = rs.getString("LAT_LONG");
					SDT = rs.getString("SDT");
					Time = rs.getString("TIME");
					Mota = rs.getString("MOTA");
					Link_hinh = rs.getString("LINK_HINH");

					String[] a = Lat_Long.split(",");
					lat2 = Double.parseDouble(a[0]);
					lng2 = Double.parseDouble(a[1]);

					NhaTro NT = new NhaTro(ID, Gia_Tien, Dien_Tich, Address, Lat_Long, SDT, Time, Mota, Link_hinh);
					if (distance(lat1, lng1, lat2, lng2) < 1) {
						listNhaTro.add(NT);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
		return listNhaTro;
	}

	public NhaTro getChitietNhatro(String latlong) {
		NhaTro nhatro = null;
		String sql = "select * from tblnhatro where LAT_LONG='" + latlong + "'";
		ResultSet rs;
		try {
			rs = Select(sql);
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String Gia_Tien = rs.getString("GIA_TIEN");
				String Dien_Tich = rs.getString("DIEN_TICH");
				String Address = rs.getString("ADDRESS");
				String Lat_Long = rs.getString("LAT_LONG");
				String SDT = rs.getString("SDT");
				String Time = rs.getString("TIME");
				String Mota = rs.getString("MOTA");
				String Link_hinh = rs.getString("LINK_HINH");

				nhatro = new NhaTro(ID, Gia_Tien, Dien_Tich, Address, Lat_Long, SDT, Time, Mota, Link_hinh);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
		return nhatro;
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		return (dist);
	}

	public boolean kiemTraTonTai(String latlong) {
		boolean datontai = false;
		String sql = "select * from tblnhatro where LatLong='" + latlong + "'";
		ResultSet rs;
		try {
			rs = Select(sql);
			if (rs != null) {
				datontai = true;
			} else {
				datontai = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return datontai;
	}

	//codeDelete
	public void xoaBotNhatro() {
		String sql = "select * from tblnhatro";
		ResultSet rs;
		try {
			rs = Select(sql);
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String Time = rs.getString("TIME");
				if (Time.length() == 13) {
					deleteNhatro(ID);
				}
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
	}
	
	//codeInsert
	public void insertNhatro(String giatien, String dientich, String address, String latlong, String sdt,
			String thoigiandang, String mota, String linkhinh) {
		String sqlInsert = "insert into tblnhatro values (" + null + "," + "'" + giatien + "'," + "'" + dientich + "',"
				+ "'" + address + "'," + "'" + latlong + "'," + "'" + sdt + "'," + "'" + thoigiandang + "'," + "'"
				+ mota + "'," + "'" + linkhinh + "')";
		Insert(sqlInsert);
	}

	//codeUpdate
	public void updateNhatro(String time, String latlong) {
		try {
			String sqlUpdate = "update tblnhatro set TIME='" + time + "' where LAT_LONG='" + latlong + "'";
			Update(sqlUpdate);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void deleteNhatro(int id) {
		try {
			String sqlDelete = "delete from tblnhatro where ID='" + id + "'";
			Delete(sqlDelete);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
