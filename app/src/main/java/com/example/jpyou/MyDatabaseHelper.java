package com.example.jpyou;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "phongmach.db";
    public static final int DATABASE_VERSION = 1;


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng TaiKhoan
        String createTaiKhoanTable = "CREATE TABLE TaiKhoan (" +
                "TaiKhoanID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaiKhoan TEXT NOT NULL UNIQUE, " +
                "MatKhau TEXT NOT NULL, " +
                "NgayThamGia TEXT NOT NULL, " +
                "HoatDong INTEGER NOT NULL);";

        // Tạo bảng NguoiDung
        String createNguoiDungTable = "CREATE TABLE NguoiDung (" +
                "TaiKhoanID INTEGER PRIMARY KEY, " +
                "HoTen TEXT NOT NULL, " +
                "GioiTinh TEXT, " +
                "NamSinh TEXT, " +
                "DiaChi TEXT, " +
                "CCCD TEXT UNIQUE, " +
                "SoDT TEXT UNIQUE, " +
                "Email TEXT UNIQUE, " +
                "VaiTro TEXT, " +
                "FOREIGN KEY(TaiKhoanID) REFERENCES TaiKhoan(TaiKhoanID));";


        // Tạo bảng Admin
        String createAdminTable = "CREATE TABLE Admin (" +
                "AdminID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ChucNang TEXT NOT NULL, " +
                "TaiKhoanID INTEGER, " +
                "FOREIGN KEY(TaiKhoanID) REFERENCES TaiKhoan(TaiKhoanID));";

        // Tạo bảng BacSi
        String createBacSiTable = "CREATE TABLE BacSi (" +
                "BacSiID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ChuyenKhoa TEXT NOT NULL, " +
                "TaiKhoanID INTEGER, " +
                "FOREIGN KEY(TaiKhoanID) REFERENCES TaiKhoan(TaiKhoanID));";

        // Tạo bảng YTa
        String createYTaTable = "CREATE TABLE Yta (" +
                "YtaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "KhoaPhuTrach TEXT NOT NULL, " +
                "TaiKhoanID INTEGER, " +
                "FOREIGN KEY(TaiKhoanID) REFERENCES TaiKhoan(TaiKhoanID));";

        // Tạo bảng BenhNhan
        String createBenhNhanTable = "CREATE TABLE BenhNhan (" +
                "BenhNhanID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaiKhoanID INTEGER, " +
                "FOREIGN KEY(TaiKhoanID) REFERENCES TaiKhoan(TaiKhoanID));";

        // Tạo bảng LichHen
        String createLichHenTable = "CREATE TABLE LichHen (" +
                "lichhenID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "bacsiID INTEGER, " +
                "benhnhanID INTEGER, " +
                "YtaID INTEGER, " +
                "ngayKham TEXT NOT NULL, " +
                "gioKham TEXT NOT NULL," +
                "soThuTuKham INTEGER, " +
                "tinhTrangHen INTEGER," +
                "khoaChon TEXT NOT NULL," +
                "FOREIGN KEY(bacsiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(benhnhanID) REFERENCES BenhNhan(BenhNhanID), " +
                "FOREIGN KEY(YtaID) REFERENCES Yta(YtaID));";

        // Tạo bảng ketQuaChuanDoan
        String createKetQuaChuanDoanTable = "CREATE TABLE ketQuaChuanDoan (" +
                "ketquachuandoanID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenKetQuaChuanDoan TEXT NOT NULL, " +
                "trieuChung TEXT, " +
                "ngayKeToa TEXT, " +
                "bacsiID INTEGER, " +
                "benhnhanID INTEGER, " +
                "FOREIGN KEY(bacsiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(benhnhanID) REFERENCES BenhNhan(BenhNhanID));";

        // Tạo bảng Thuoc
        String createThuocTable = "CREATE TABLE Thuoc (" +
                "thuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenThuoc TEXT NOT NULL);";

        // Tạo bảng ToaThuoc
        String createToaThuocTable = "CREATE TABLE ToaThuoc (" +
                "toathuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ketquachuandoanID INTEGER, " +
                "FOREIGN KEY(ketquachuandoanID) REFERENCES ketQuaChuanDoan(ketquachuandoanID));";

        // Tạo bảng ToaThuoc_Thuoc
        String createToaThuocThuocTable = "CREATE TABLE ToaThuoc_Thuoc (" +
                "ThuocID INTEGER, " +
                "ToaThuocID INTEGER, " +
                "LieuDung TEXT, " +
                "HuongDanSuDung TEXT, " +
                "PRIMARY KEY(ThuocID, ToaThuocID), " +
                "FOREIGN KEY(ThuocID) REFERENCES Thuoc(thuocID), " +
                "FOREIGN KEY(ToaThuocID) REFERENCES ToaThuoc(toathuocID));";

        // Thực thi các lệnh tạo bảng
        db.execSQL(createTaiKhoanTable);
        db.execSQL(createNguoiDungTable);
        db.execSQL(createAdminTable);
        db.execSQL(createBacSiTable);
        db.execSQL(createYTaTable);
        db.execSQL(createBenhNhanTable);
        db.execSQL(createLichHenTable);
        db.execSQL(createKetQuaChuanDoanTable);
        db.execSQL(createThuocTable);
        db.execSQL(createToaThuocTable);
        db.execSQL(createToaThuocThuocTable);

        String insertTaiKhoan1 = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, NgayThamGia, HoatDong) VALUES ('admin_user', 'user123', '14/11/2024', 1);";
        String insertTaiKhoan2 = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, NgayThamGia, HoatDong) VALUES ('admin_report', 'report123', '14/11/2024', 1);";

        db.execSQL(insertTaiKhoan1);
        db.execSQL(insertTaiKhoan2);

        String insertNguoiDung1 = "INSERT INTO NguoiDung (TaiKhoanID, HoTen, VaiTro) VALUES ((SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = 'admin_user'), 'Admin User', 'Admin');";
        String insertNguoiDung2 = "INSERT INTO NguoiDung (TaiKhoanID, HoTen, VaiTro) VALUES ((SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = 'admin_report'), 'Admin Report', 'Admin');";

        db.execSQL(insertNguoiDung1);
        db.execSQL(insertNguoiDung2);

        String insertAdmin1 = "INSERT INTO Admin (ChucNang, TaiKhoanID) VALUES ('Quản lý đăng nhập', (SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = 'admin_user'));";
        String insertAdmin2 = "INSERT INTO Admin (ChucNang, TaiKhoanID) VALUES ('Xem thống kê', (SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = 'admin_report'));";

        db.execSQL(insertAdmin1);
        db.execSQL(insertAdmin2);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng nếu chúng tồn tại
        db.execSQL("DROP TABLE IF EXISTS ToaThuoc_Thuoc");
        db.execSQL("DROP TABLE IF EXISTS ToaThuoc");
        db.execSQL("DROP TABLE IF EXISTS Thuoc");
        db.execSQL("DROP TABLE IF EXISTS ketQuaChuanDoan");
        db.execSQL("DROP TABLE IF EXISTS LichHen");
        db.execSQL("DROP TABLE IF EXISTS BenhNhan");
        db.execSQL("DROP TABLE IF EXISTS Yta");
        db.execSQL("DROP TABLE IF EXISTS BacSi");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS NguoiDung");
        db.execSQL("DROP TABLE IF EXISTS TaiKhoan");

        // Gọi lại onCreate để tạo lại các bảng với cấu trúc mới
        onCreate(db);
    }


    public void addNguoiDung(String username, String password, String tenNguoiDung, String gioiTinh, String ngaySinh, String diaChi, String email, String soDT, String cccd, String chuyenKhoa, String chucVu) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues taiKhoan = new ContentValues();
            taiKhoan.put("TaiKhoan", username);
            taiKhoan.put("MatKhau", password);
            taiKhoan.put("NgayThamGia", Setting.getCurrentDate());
            taiKhoan.put("HoatDong", 1);
            long addTK = db.insert("TaiKhoan", null, taiKhoan);
            if (addTK == -1) {
                Toast.makeText(context, "Không thêm được tài khoản", Toast.LENGTH_SHORT).show();
                return;
            }

            // Bước 1: Thêm người dùng vào bảng NguoiDung
            ContentValues NguoiDung = new ContentValues();
            NguoiDung.put("TaiKhoanID", addTK);
            NguoiDung.put("HoTen", tenNguoiDung);  // Updated column name
            NguoiDung.put("GioiTinh", gioiTinh);
            NguoiDung.put("NamSinh", ngaySinh);
            NguoiDung.put("DiaChi", diaChi);
            NguoiDung.put("CCCD", cccd);
            NguoiDung.put("SoDT", soDT);
            NguoiDung.put("Email", email);
            NguoiDung.put("VaiTro", chucVu);
            long addNguoiDung = db.insert("NguoiDung", null, NguoiDung);

            if (addNguoiDung == -1) {
                Toast.makeText(context, "Không thêm được người dùng", Toast.LENGTH_SHORT).show();
            }

            switch (chucVu) {
                case "Bác sĩ": {
                    ContentValues bacSi = new ContentValues();
                    bacSi.put("ChuyenKhoa", chuyenKhoa);
                    bacSi.put("TaiKhoanID", addTK);  // Use the same TaiKhoanID
                    long addBacSi = db.insert("BacSi", null, bacSi);
                    if (addBacSi == -1) {
                        Toast.makeText(context, "Không thêm được bác sĩ", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case "Y tá": {
                    ContentValues yTa = new ContentValues();
                    yTa.put("KhoaPhuTrach", chuyenKhoa);  // Use chuyenKhoa as KhoaPhuTrach for YTa
                    yTa.put("TaiKhoanID", addTK);  // Use the same TaiKhoanID
                    long addYTa = db.insert("Yta", null, yTa);
                    if (addYTa == -1) {
                        Toast.makeText(context, "Không thêm được y tá", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default: {
                    ContentValues benhNhan = new ContentValues();
                    benhNhan.put("TaiKhoanID", addTK);
                    long addYTa = db.insert("BenhNhan", null, benhNhan);
                    if (addYTa == -1) {
                        Toast.makeText(context, "Không thêm được y tá", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            }
            Toast.makeText(context, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi thêm người dùng: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }




    @SuppressLint("Range")
    public String verifyPassword(String username, String plainPassword, String vaiTro) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn JOIN để lấy cả mật khẩu, vai trò và TaiKhoanID
        String query = "SELECT TaiKhoan.MatKhau, TaiKhoan.TaiKhoanID, NguoiDung.VaiTro " +
                "FROM TaiKhoan " +
                "INNER JOIN NguoiDung ON TaiKhoan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE TaiKhoan.TaiKhoan = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username});

        String taiKhoanID = "-1";
        if (cursor.moveToFirst()) {
            // Lấy mật khẩu, vai trò và TaiKhoanID từ kết quả truy vấn
            String storedPassword = cursor.getString(cursor.getColumnIndex("MatKhau"));
            String storedVaiTro = cursor.getString(cursor.getColumnIndex("VaiTro"));
            String storedTaiKhoanID = cursor.getString(cursor.getColumnIndex("TaiKhoanID"));

            // So sánh mật khẩu và vai trò
            if (storedPassword != null && storedPassword.equals(plainPassword)) {
                if (storedVaiTro != null && storedVaiTro.equals(vaiTro)) {
                    // Đăng nhập thành công, gán TaiKhoanID cho biến kết quả
                    taiKhoanID = storedTaiKhoanID;
                }
            }
        }

        cursor.close();
        db.close();

        return taiKhoanID;
    }

}