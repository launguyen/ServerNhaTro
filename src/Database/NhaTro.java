package Database;

public class NhaTro {
	public int ID;
	public String Gia_Tien;
	public String Dien_Tich;
	public String Address;
	public String Lat_Long;
	public String SDT;
	public String Time;
	public String Mota;
	public String Link_hinh;

	public NhaTro(int iD, String gia_Tien, String dien_Tich, String address, String lat_Long, String sDT, String time,
			String mota, String link_hinh) {
		ID = iD;
		Gia_Tien = gia_Tien;
		Dien_Tich = dien_Tich;
		Address = address;
		Lat_Long = lat_Long;
		SDT = sDT;
		Time = time;
		Mota = mota;
		Link_hinh = link_hinh;
	}

}
