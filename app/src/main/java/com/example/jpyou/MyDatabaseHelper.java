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

import com.example.jpyou.User.UserInformation;

import java.util.ArrayList;
import java.util.List;

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
        String createYTaTable = "CREATE TABLE YTa (" +
                "YTaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
                "TenLichHen TEXT NOT NULL, " +
                "TrieuChung TEXT, " +
                "GioDatKham TEXT, " +
                "NgayKham TEXT NOT NULL, " +
                "SoThuTuKham INTEGER, " +
                "TinhTrangHen INTEGER, " +
                "Khoa TEXT," +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "YTaID INTEGER, " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID), " +
                "FOREIGN KEY(YTaID) REFERENCES YTa(YTaID));";

        // Tạo bảng ketQuaChuanDoan
        String createKetQuaChuanDoanTable = "CREATE TABLE KetQuaChuanDoan (" +
                "KetQuaChuanDoanID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenKetQuaChuanDoan TEXT NOT NULL, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "ToaThuocID INTEGER, " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(ToaThuocID) REFERENCES ToaThuoc(ToaThuocID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID));";


        // Tạo bảng Thuoc
        String createThuocTable = "CREATE TABLE Thuoc (" +
                "ThuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenThuoc TEXT NOT NULL);";

        // Tạo bảng ToaThuoc
        String createToaThuocTable = "CREATE TABLE ToaThuoc (" +
                "ToathuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NgayKeToa TEXT);";

        // Tạo bảng ToaThuoc_Thuoc
        String createToaThuocThuocTable = "CREATE TABLE ToaThuoc_Thuoc (" +
                "ToaThuocThuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "LieuDung TEXT, " +
                "HuongDanSuDung TEXT, " +
                "ThuocID INTEGER, " +
                "ToaThuocID INTEGER, " +
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
            account.put("NgayThamGia", init.getCurrentDate());
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
            case "Y tá":
                query = "SELECT TaiKhoan.MatKhau, TaiKhoan.TaiKhoanID, TaiKhoan.HoatDong " +
                        "FROM TaiKhoan " +
                        "JOIN YTa ON TaiKhoan.TaiKhoanID = YTa.TaiKhoanID " +
                        "WHERE TaiKhoan.TaiKhoan = ? AND TaiKhoan.HoatDong = 1";
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
    public PersonInformation getInformation(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        PersonInformation ps = null;
        String query = "SELECT * " +
                "FROM NguoiDung " +
                "WHERE TaiKhoanID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor.moveToFirst()) {
            // Lấy dữ liệu từ Cursor và tạo đối tượng PersonInformation
            int userID = cursor.getInt(cursor.getColumnIndex("TaiKhoanID"));
            String name = cursor.getString(cursor.getColumnIndex("HoTen"));
            String gender = cursor.getString(cursor.getColumnIndex("GioiTinh"));
            String dayOfBirth = cursor.getString(cursor.getColumnIndex("NgaySinh"));
            String phone = cursor.getString(cursor.getColumnIndex("SoDT"));
            String email = cursor.getString(cursor.getColumnIndex("Email"));

            // Khởi tạo đối tượng PersonInformation với các giá trị lấy từ database
            ps = new PersonInformation(userID, name, gender, dayOfBirth, phone, email);
        }

        return ps;
    }

    public void updateInformation(String id, String name, String gender, String dayOfBirth, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE NguoiDung SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, SoDT = ?, Email = ? WHERE TaiKhoanID = ?";

        // Sử dụng SQLiteStatement hoặc phương thức execSQL với các tham số
        db.execSQL(query, new String[]{name, gender, dayOfBirth, phone, email, id});

        db.close(); // Đóng kết nối với cơ sở dữ liệu
    }

    public int getOrderNumber(String ngayKham) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int orderNumber = 0;

        try {
            String query = "SELECT COUNT(*) AS SoBenhNhan FROM LichHen WHERE NgayKham = ?";
            cursor = db.rawQuery(query, new String[]{ngayKham});

            if (cursor.moveToFirst()) {
                orderNumber = cursor.getInt(cursor.getColumnIndexOrThrow("SoBenhNhan")) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng con trỏ và cơ sở dữ liệu
            if (cursor != null) cursor.close();
            db.close();
        }

        return orderNumber;
    }

    public void registerExamination(String id, String dayRegis, String symptom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues regis = new ContentValues();
        regis.put("BenhNhanID", id);
        regis.put("TenLichHen", "Khám bệnh");
        regis.put("NgayKham", dayRegis);
        regis.put("GioDatKham", init.getCurrentTime());
        regis.put("TrieuChung", symptom);
        regis.put("TinhTrangHen", 0);
        long addRegis = db.insert("LichHen", null, regis);

        db.close();
    }

    public void registerVaccinate(String id, String dayRegis, String symptom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues regis = new ContentValues();
        regis.put("BenhNhanID", id);
        regis.put("TenLichHen", "Vaccine");
        regis.put("TrieuChung", symptom);
        regis.put("GioDatKham", init.getCurrentTime());
        regis.put("NgayKham", dayRegis);
        regis.put("TinhTrangHen", 0);
        long addRegis = db.insert("LichHen", null, regis);

        db.close();
    }

    @SuppressLint("Recycle")
    public List<UserInformation> showPatient() {
        List<UserInformation> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT NguoiDung.HoTen " +
                "FROM LichHen " +
                "JOIN BenhNhan ON LichHen.BenhNhanID = BenhNhan.BenhNhanID " +
                "INNER JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE LichHen.TinhTrangHen = 0";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    // Assuming UserInformation has a constructor that takes HoTen as a parameter
                    String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                    Log.d("alo", hoTen);
                    ls.add(new UserInformation(hoTen));
                }
            } finally {
                cursor.close(); // Ensure the Cursor is closed to prevent resource leaks
            }
        }

        return ls;
    }

    public String getPatientID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT BenhNhan.BenhNhanID " +
                "FROM BenhNhan " +
                "JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE NguoiDung.TaiKhoanID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{id});

        String patientID = null; // Default value in case no result is found
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    // Retrieve BenhNhanID from the first row of the result
                    patientID = cursor.getString(cursor.getColumnIndexOrThrow("BenhNhanID"));
                }
            } finally {
                cursor.close(); // Ensure the cursor is closed to avoid resource leaks
            }
        }
        return patientID;
    }

    public List<UserInformation> getDaySchedule(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<UserInformation> ls = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Truy vấn dữ liệu
            String query = "SELECT TenLichHen, NgayKham, SoThuTuKham " +
                    "FROM LichHen " +
                    "WHERE BenhNhanID = ?";
            cursor = db.rawQuery(query, new String[]{id});

            // Duyệt qua kết quả
            if (cursor.moveToFirst()) {
                do {
                    String tenLichHen = cursor.getString(cursor.getColumnIndexOrThrow("TenLichHen"));
                    String ngayKham = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));
                    UserInformation userInformation = new UserInformation(ngayKham, tenLichHen);
                    ls.add(userInformation);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng Cursor và cơ sở dữ liệu
            if (cursor != null) cursor.close();
            db.close();
        }

        return ls;
    }

    public List<UserInformation> getScheduleAtDay(String id, String day) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<UserInformation> ls = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Truy vấn dữ liệu
            String query = "SELECT TenLichHen, NgayKham, SoThuTuKham " +
                    "FROM LichHen " +
                    "WHERE BenhNhanID = ? AND NgayKham = ?";
            cursor = db.rawQuery(query, new String[]{id, day});

            // Duyệt qua kết quả
            if (cursor.moveToFirst()) {
                do {
                    String tenLichHen = cursor.getString(cursor.getColumnIndexOrThrow("TenLichHen"));
                    String ngayKham = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));

                    // Ghép thông tin
                    UserInformation userInformation = new UserInformation(ngayKham, tenLichHen);
                    ls.add(userInformation);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng Cursor và cơ sở dữ liệu
            if (cursor != null) cursor.close();
            db.close();
        }

        return ls;
    }

}