package com.example.jpyou;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
                "HoTen TEXT, " +
                "GioiTinh TEXT, " +
                "NgaySinh TEXT, " +
                "SoDT TEXT UNIQUE, " +
                "Email TEXT UNIQUE, " +
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
                "LichhenID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "YtaID INTEGER, " +
                "NgayKham TEXT NOT NULL, " +
                "GioKham TEXT NOT NULL," +
                "SoThuTuKham INTEGER, " +
                "TinhTrangHen INTEGER," +
                "KhoaChon TEXT NOT NULL," +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID), " +
                "FOREIGN KEY(YtaID) REFERENCES Yta(YtaID));";

        // Tạo bảng ketQuaChuanDoan
        String createKetQuaChuanDoanTable = "CREATE TABLE KetQuaChuanDoan (" +
                "KetQuaChuanDoanID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenKetQuaChuanDoan TEXT NOT NULL, " +
                "TrieuChung TEXT, " +
                "NgayKeToa TEXT, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID));";


        // Tạo bảng Thuoc
        String createThuocTable = "CREATE TABLE Thuoc (" +
                "ThuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenThuoc TEXT NOT NULL);";

        // Tạo bảng ToaThuoc
        String createToaThuocTable = "CREATE TABLE ToaThuoc (" +
                "ToathuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "KetQuaChuanDoanID INTEGER, " +
                "FOREIGN KEY(KetquachuandoanID) REFERENCES KetQuaChuanDoan(KetQuaChuanDoanID));";

        // Tạo bảng ToaThuoc_Thuoc
        String createToaThuocThuocTable = "CREATE TABLE ToaThuoc_Thuoc (" +
                "ThuocID INTEGER, " +
                "ToaThuocID INTEGER, " +
                "LieuDung TEXT, " +
                "HuongDanSuDung TEXT, " +
                "PRIMARY KEY(ThuocID, ToaThuocID), " +
                "FOREIGN KEY(ThuocID) REFERENCES Thuoc(ThuocID), " +
                "FOREIGN KEY(ToaThuocID) REFERENCES ToaThuoc(ToaThuocID));";

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

        String insertNguoiDung1 = "INSERT INTO NguoiDung (TaiKhoanID, HoTen) VALUES ((SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = 'admin_user'), 'Admin User');";
        String insertNguoiDung2 = "INSERT INTO NguoiDung (TaiKhoanID, HoTen) VALUES ((SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = 'admin_report'), 'Admin Report');";

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


    public void addUser(String username, String password, String name, String gender, String dayOfBirth, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues account = new ContentValues();
            account.put("TaiKhoan", username);
            account.put("MatKhau", password);
            account.put("NgayThamGia", Setting.getCurrentDate());
            account.put("HoatDong", 1);
            long addAccount = db.insert("TaiKhoan", null, account);

            ContentValues userinform = new ContentValues();
            userinform.put("TaiKhoanID", addAccount);
            userinform.put("HoTen", name);
            userinform.put("GioiTinh", gender);
            userinform.put("NgaySinh", dayOfBirth);
            userinform.put("SoDT", phone);
            userinform.put("Email", email);
            long addUserInform = db.insert("NguoiDung", null, userinform);

            ContentValues patientinform = new ContentValues();
            patientinform.put("TaiKhoanID", addAccount);
            long addpatientinform = db.insert("BenhNhan", null, patientinform);



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
        String query = null;

        switch (vaiTro) {
            case "Bệnh nhân":
                query = "SELECT TaiKhoan.MatKhau, TaiKhoan.TaiKhoanID, TaiKhoan.HoatDong " +
                        "FROM TaiKhoan " +
                        "JOIN BenhNhan ON TaiKhoan.TaiKhoanID = BenhNhan.TaiKhoanID " +
                        "WHERE TaiKhoan.TaiKhoan = ? AND TaiKhoan.HoatDong = 1";
                break;
        }
        Cursor cursor = db.rawQuery(query, new String[]{username});

        String taiKhoanID = "-1";
        if (cursor.moveToFirst()) {

            String storedPassword = cursor.getString(cursor.getColumnIndex("MatKhau"));
            String storedId = cursor.getString(cursor.getColumnIndex("TaiKhoanID"));

            if (storedPassword != null && storedPassword.equals(plainPassword)) {
                taiKhoanID = storedId;
            }
        }

        cursor.close();
        db.close();

        return taiKhoanID;
    }

    @SuppressLint("Range")
    public Person getInformation(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Person ps = null;
        String query = "SELECT * " +
                "FROM NguoiDung " +
                "WHERE TaiKhoanID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor.moveToFirst()) {
            // Lấy dữ liệu từ Cursor và tạo đối tượng Person
            int taiKhoanID = cursor.getInt(cursor.getColumnIndex("TaiKhoanID"));
            String hoTen = cursor.getString(cursor.getColumnIndex("HoTen"));
            String gioiTinh = cursor.getString(cursor.getColumnIndex("GioiTinh"));
            String ngaySinh = cursor.getString(cursor.getColumnIndex("NgaySinh"));
            String soDT = cursor.getString(cursor.getColumnIndex("SoDT"));
            String email = cursor.getString(cursor.getColumnIndex("Email"));

            // Khởi tạo đối tượng Person với các giá trị lấy từ database
            ps = new Person(taiKhoanID, hoTen, gioiTinh, ngaySinh, soDT, email);
        }

        return ps;
    }

}