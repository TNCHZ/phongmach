package com.example.jpyou.data.datasource;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.data.model.PersonInformation;
import com.example.jpyou.data.model.UserInformation;
import com.example.jpyou.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                "HinhAnh BLOB, " +
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
                "KinhNghiem TEXT NOT NULL, " +
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
                "NgayKham TEXT NOT NULL, " +
                "SoThuTuKham INTEGER, " +
                "isHen INTEGER, " +
                "isKham INTEGER, " +
                "isHuy INTEGER, " +
                "KetQuaChuanDoanID INTEGER, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "FOREIGN KEY(KetQuaChuanDoanID) REFERENCES KetQuaChuanDoan(KetQuaChuanDoanID), " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID)); ";

        // Tạo bảng ketQuaChuanDoan
        String createKetQuaChuanDoanTable = "CREATE TABLE KetQuaChuanDoan (" +
                "KetQuaChuanDoanID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenKetQuaChuanDoan TEXT NOT NULL, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "ToaThuocID INTEGER, " +
                "LichHenID INTEGER, " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(ToaThuocID) REFERENCES ToaThuoc(ToaThuocID), " +
                "FOREIGN KEY(LichHenID) REFERENCES LichHen(LichHenID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID));";


        // Tạo bảng Thuoc
        String createThuocTable = "CREATE TABLE Thuoc (" +
                "ThuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenThuoc TEXT NOT NULL, " +
                "DonVi TEXT);";

        // Tạo bảng ToaThuoc
        String createToaThuocTable = "CREATE TABLE ToaThuoc (" +
                "ToathuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NgayKeToa TEXT);";

        // Tạo bảng ToaThuoc_Thuoc
        String createToaThuocThuocTable = "CREATE TABLE ToaThuoc_Thuoc (" +
                "ToaThuocThuocID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SoLuong TEXT, " +
                "HuongDanSuDung TEXT, " +
                "ThuocID INTEGER, " +
                "ToaThuocID INTEGER, " +
                "FOREIGN KEY(ThuocID) REFERENCES Thuoc(ThuocID), " +
                "FOREIGN KEY(ToaThuocID) REFERENCES ToaThuoc(ToaThuocID));";

        String createLichLamViecTable = "CREATE TABLE LichLamViec (" +
                "LichLamViecID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NgayLamViec TEXT UNIQUE, " +
                "BacSiID INTEGER, " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID));";

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
        db.execSQL(createLichLamViecTable);


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
        ContentValues values = new ContentValues();

        values.put("TenThuoc", "Paracetamol");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Aspirin");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Amoxicillin");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Ibuprofen");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Vitamin C");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Loratadine");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Omeprazole");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Cetirizine");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Diphenhydramine");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

        values.put("TenThuoc", "Metformin");
        values.put("DonVi", "Viên");
        db.insert("Thuoc", null, values);

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
        db.execSQL("DROP TABLE IF EXISTS LichLamViec");

        // Gọi lại onCreate để tạo lại các bảng với cấu trúc mới
        onCreate(db);
    }


    public void addUser(String username, String password, String name, String gender, String dayOfBirth, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues account = new ContentValues();
            account.put("TaiKhoan", username);
            account.put("MatKhau", password);
            account.put("NgayThamGia", utils.getCurrentDate());
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
    public String verifyPassword(String username, String plainPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = null;

        query = "SELECT MatKhau, TaiKhoanID, HoatDong " +
                "FROM TaiKhoan " +
                "WHERE TaiKhoan = ? AND HoatDong = 1";

        Cursor cursor = db.rawQuery(query, new String[]{username});

        String taiKhoanID = "-1";
        if (cursor.moveToFirst()) {

            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("MatKhau"));
            String storedId = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));

            if (storedPassword != null && storedPassword.equals(plainPassword)) {
                taiKhoanID = storedId;
            }
        }

        cursor.close();
        db.close();

        return taiKhoanID;
    }

    public String getRole(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL queries for checking role in respective tables
        String queryDoctor = "SELECT TaiKhoanID FROM BacSi WHERE TaiKhoanID = ?";
        String queryPatient = "SELECT TaiKhoanID FROM BenhNhan WHERE TaiKhoanID = ?";
        String queryNurse = "SELECT TaiKhoanID FROM YTa WHERE TaiKhoanID = ?";

        Cursor cursor = null; // Declare a single Cursor to reuse

        try {
            // Check Patient role
            cursor = db.rawQuery(queryPatient, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Benh nhan"; // Patient role found
            }

            // Check Doctor role
            if (cursor != null) cursor.close(); // Close previous Cursor
            cursor = db.rawQuery(queryDoctor, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Bac si"; // Doctor role found
            }

            // Check Nurse role
            if (cursor != null) cursor.close(); // Close previous Cursor
            cursor = db.rawQuery(queryNurse, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Y ta"; // Nurse role found
            }
        } catch (Exception e) {
            Log.e("getRole", "Error while getting role", e);
        } finally {
            // Ensure Cursor is closed
            if (cursor != null) cursor.close();
        }

        return "Unknown"; // Default role if none found
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
            String userID = cursor.getString(cursor.getColumnIndex("TaiKhoanID"));
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


    public boolean registerExamination(String id, String dayRegis, String symptom) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra nếu dayRegis đã tồn tại với id
        String query = "SELECT COUNT(*) FROM LichHen WHERE BenhNhanID = ? AND NgayKham = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id, dayRegis});

        boolean exists = false;
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0; // Nếu kết quả đếm lớn hơn 0, thì tồn tại
        }
        cursor.close();

        if (exists) {
            db.close();
            return false; // Trả về false nếu đã tồn tại
        }

        String today = utils.getCurrentDate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                LocalDate regisDate = LocalDate.parse(dayRegis, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate currentDate = LocalDate.parse(today, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                if (regisDate.isBefore(currentDate)) {
                    db.close();
                    return false;
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                db.close();
                return false;
            }
        }
        Log.d("a", ":");
        // Thêm bản ghi mới vào LichHen
        ContentValues regis = new ContentValues();
        regis.put("BenhNhanID", id);
        regis.put("TenLichHen", "Khám bệnh");
        regis.put("NgayKham", dayRegis);
        regis.put("isKham", 0);
        regis.put("isHuy", 0);
        regis.put("TrieuChung", symptom);
        regis.put("isHen", 0);

        long addRegis = db.insert("LichHen", null, regis);

        db.close();
        return addRegis != -1; // Trả về true nếu thêm thành công
    }


    @SuppressLint("Recycle")
    public List<UserInformation> showPatientForDoctor(String doctorID) {
        List<UserInformation> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT NguoiDung.HoTen, NguoiDung.TaiKhoanID, LichHen.TrieuChung " +
                "FROM LichHen " +
                "JOIN BenhNhan ON LichHen.BenhNhanID = BenhNhan.BenhNhanID " +
                "INNER JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE LichHen.isHen = 0 AND LichHen.isKham = 0 AND LichHen.BacSiID = ? AND LichHen.NgayKham =?";

        Cursor cursor = db.rawQuery(query, new String[]{doctorID, utils.getCurrentDate()});
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));
                    String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                    String trieuChung = cursor.getString(cursor.getColumnIndexOrThrow("TrieuChung"));
                    ls.add(new UserInformation(id, hoTen, trieuChung));
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

    public List<Doctor> getDoctors() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Doctor> ls = new ArrayList<>();

        // Query lấy dữ liệu từ hai bảng
        String query = "SELECT NguoiDung.HoTen, BacSi.KinhNghiem " +
                "FROM NguoiDung " +
                "INNER JOIN BacSi ON NguoiDung.TaiKhoanID = BacSi.TaiKhoanID";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                String kinhNghiem = cursor.getString(cursor.getColumnIndexOrThrow("KinhNghiem"));

                Doctor doctor = new Doctor(hoTen, kinhNghiem);
                ls.add(doctor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ls;
    }

    public List<Medicine> getMedicines() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Medicine> ls = new ArrayList<>();

        // Query lấy dữ liệu từ bảng Thuoc
        String query = "SELECT ThuocID, TenThuoc, DonVi FROM Thuoc";

        Cursor cursor = db.rawQuery(query, null);

        // Duyệt qua kết quả trả về
        if (cursor.moveToFirst()) {
            do {
                // Lấy từng cột từ Cursor
                String thuocID = cursor.getString(cursor.getColumnIndexOrThrow("ThuocID"));
                String tenThuoc = cursor.getString(cursor.getColumnIndexOrThrow("TenThuoc"));
                String donVi = cursor.getString(cursor.getColumnIndexOrThrow("DonVi"));

                // Tạo đối tượng Thuoc và thêm vào danh sách
                Medicine thuoc = new Medicine(thuocID, tenThuoc, donVi);
                ls.add(thuoc);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ls;
    }

    public void addMedicine(String patientID, String doctorID, String symp, List<Medicine> medicines) {
        SQLiteDatabase db = this.getWritableDatabase();  // Mở cơ sở dữ liệu ở chế độ ghi

        // Bắt đầu giao dịch để chèn nhiều bản ghi cùng lúc
        db.beginTransaction();

        try {
            ContentValues toaThuocValues = new ContentValues();
            toaThuocValues.put("NgayKeToa", utils.getCurrentDate());

            long toaThuocID = db.insert("ToaThuoc", null, toaThuocValues);

            for (Medicine medicine : medicines) {
                ContentValues med = new ContentValues();
                med.put("SoLuong", medicine.getQuantity());
                med.put("HuongDanSuDung", medicine.getUsage());
                med.put("ThuocID", medicine.getId());
                med.put("ToaThuocID", toaThuocID);
                db.insert("ToaThuoc_Thuoc", null, med);
            }

            ContentValues result = new ContentValues();
            result.put("TenKetQuaChuanDoan", symp);
            result.put("BacSiID", doctorID);
            result.put("BenhNhanID", patientID);
            result.put("ToaThuocID", toaThuocID);
            long ketQuaChuanDoanID = db.insert("KetQuanChuanDoan", null, result);

            ContentValues updateValues = new ContentValues();
            updateValues.put("KetQuaChuanDoanID", ketQuaChuanDoanID);

            String whereClause = "BenhNhanID = ? AND BacSiID = ? AND NgayKham = ?";
            String[] whereArgs = new String[]{patientID, doctorID, utils.getCurrentDate()};

            // Cập nhật bảng LichHen
            int rowsUpdated = db.update("LichHen", updateValues, whereClause, whereArgs);


            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public String getDoctorID(String userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT BacSi.BacSiID " +
                "FROM BacSi " +
                "JOIN NguoiDung ON BacSi.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE NguoiDung.TaiKhoanID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userID});

        String doctorID = null; // Default value in case no result is found
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    // Retrieve BenhNhanID from the first row of the result
                    doctorID = cursor.getString(cursor.getColumnIndexOrThrow("BacSiID"));
                }
            } finally {
                cursor.close(); // Ensure the cursor is closed to avoid resource leaks
            }
        }
        return doctorID;
    }

    public List<UserInformation> showPatient() {
        List<UserInformation> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT NguoiDung.HoTen, NguoiDung.TaiKhoanID, LichHen.TrieuChung, LichHen.NgayKham " +
                "FROM LichHen " +
                "JOIN BenhNhan ON LichHen.BenhNhanID = BenhNhan.BenhNhanID " +
                "INNER JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE LichHen.isHen = 0 AND LichHen.isKham = 0";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));
                    String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                    String trieuChung = cursor.getString(cursor.getColumnIndexOrThrow("TrieuChung"));
                    String ngayKham = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));
                    ls.add(new UserInformation(id, hoTen, trieuChung, ngayKham));
                }
            } finally {
                cursor.close(); // Ensure the Cursor is closed to prevent resource leaks
            }
        }
        return ls;
    }


    public boolean confirmExam(String id, String appointDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String whereClauseLichHen = "BenhNhanID = ? AND NgayKham = ?";
            String[] whereArgsLichHen = new String[]{id, appointDay};
            String bacSiID = getBacSiIDFromLichLamViec(appointDay);
            if (bacSiID == null)
                return false;

            ContentValues updateValues = new ContentValues();
            updateValues.put("BacSiID", bacSiID);
            updateValues.put("isHen", 1);
            db.update("LichHen", updateValues, whereClauseLichHen, whereArgsLichHen);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return true;
    }

    @SuppressLint("Range")
    public String getBacSiIDFromLichLamViec(String appointDay) {
        SQLiteDatabase db = this.getReadableDatabase();

        String bacSiID = null;
        String query = "SELECT BacSiID FROM LichLamViec WHERE NgayLamViec = ?";
        Cursor cursor = db.rawQuery(query, new String[]{appointDay});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                bacSiID = cursor.getString(cursor.getColumnIndex("BacSiID"));
            }
            cursor.close();
        }
        return bacSiID;
    }

    public String confirm(String id, String appointDay) {
        SQLiteDatabase db = this.getReadableDatabase();
        String confirm = "";
        String query = "SELECT isHen FROM LichHen WHERE BenhNhanID = ? AND NgayKham = ?";
        Cursor cursor = db.rawQuery(query, new String[] { id, appointDay });
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int isHen = cursor.getInt(cursor.getColumnIndex("isHen"));
            if (isHen == 1) {
                confirm = "Đã xác nhận đăng kí";
            } else {
                confirm = "Chưa xác nhận";
            }
        }
        // Đóng con trỏ sau khi sử dụng xong
        if (cursor != null) {
            cursor.close();
        }

        // Trả về kết quả xác nhận
        return confirm;
    }
}