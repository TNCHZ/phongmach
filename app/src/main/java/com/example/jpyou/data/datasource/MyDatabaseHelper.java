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

import com.example.jpyou.data.model.Comment;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.data.model.PersonInformation;
import com.example.jpyou.data.model.Patient;
import com.example.jpyou.data.model.Role;
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

        String createLichHenTable = "CREATE TABLE LichHen (" +
                "LichHenID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenLichHen TEXT NOT NULL, " +
                "TrieuChung TEXT, " +
                "NgayKham TEXT NOT NULL, " +
                "SoThuTuKham INTEGER, " +
                "isHen INTEGER, " +
                "isKham INTEGER, " +
                "isHuy INTEGER, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID));";

        String createKetQuaChuanDoanTable = "CREATE TABLE KetQuaChuanDoan (" +
                "LichHenID INTEGER PRIMARY KEY, " +
                "TenKetQuaChuanDoan TEXT NOT NULL, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "ToaThuocID INTEGER, " +
                "FOREIGN KEY(LichhenID) REFERENCES LichHen(LichhenID), " +
                "FOREIGN KEY(BacSiID) REFERENCES BacSi(BacSiID), " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID), " +
                "FOREIGN KEY(ToaThuocID) REFERENCES ToaThuoc(ToaThuocID));";

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

        String createDanhGiaTable = "CREATE TABLE DanhGia (" +
                "DanhGiaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NoiDung TEXT, " +
                "BacSiID INTEGER, " +
                "BenhNhanID INTEGER, " +
                "FOREIGN KEY(BenhNhanID) REFERENCES BenhNhan(BenhNhanID), " +
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
        db.execSQL(createDanhGiaTable);


        String insertTaiKhoan1 = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, NgayThamGia, HoatDong) VALUES ('0938563411', '123', '14/11/2024', 1);";
        String insertTaiKhoan2 = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, NgayThamGia, HoatDong) VALUES ('0938563412', '123', '14/11/2024', 1);";

        db.execSQL(insertTaiKhoan1);
        db.execSQL(insertTaiKhoan2);

        String insertNguoiDung1 = "INSERT INTO NguoiDung (TaiKhoanID, HoTen) VALUES ((SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = '0938563411'), 'Admin User');";
        String insertNguoiDung2 = "INSERT INTO NguoiDung (TaiKhoanID, HoTen) VALUES ((SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = '0938563412'), 'Admin Report');";

        db.execSQL(insertNguoiDung1);
        db.execSQL(insertNguoiDung2);

        String insertAdmin1 = "INSERT INTO Admin (ChucNang, TaiKhoanID) VALUES ('Quản lý đăng nhập', (SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = '0938563411'));";
        String insertAdmin2 = "INSERT INTO Admin (ChucNang, TaiKhoanID) VALUES ('Xem thống kê', (SELECT TaiKhoanID FROM TaiKhoan WHERE TaiKhoan = '0938563412'));";

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
        db.execSQL("DROP TABLE IF EXISTS DanhGia");

        // Gọi lại onCreate để tạo lại các bảng với cấu trúc mới
        onCreate(db);
    }


    public boolean addUser(String username, String password, String name, String gender, String dayOfBirth, String phone, String email, String exp, String who, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues account = new ContentValues();
            account.put("TaiKhoan", username);
            account.put("MatKhau", password);
            account.put("NgayThamGia", utils.getCurrentDate());
            account.put("HoatDong", 1);
            long addAccount = db.insert("TaiKhoan", null, account);
            if (addAccount == -1)
                return false;

            ContentValues userinform = new ContentValues();
            userinform.put("TaiKhoanID", addAccount);
            userinform.put("HoTen", name);
            userinform.put("GioiTinh", gender);
            userinform.put("NgaySinh", dayOfBirth);
            userinform.put("SoDT", phone);
            userinform.put("Email", email);
            long addUserInform = db.insert("NguoiDung", null, userinform);
            if (addUserInform == -1)
                return false;

            if (who.equals("Bệnh nhân")) {
                ContentValues patientInform = new ContentValues();
                patientInform.put("TaiKhoanID", addAccount);
                long addpatientinform = db.insert("BenhNhan", null, patientInform);
                if (addpatientinform == -1)
                    return false;
            } else if (who.equals("Bác sĩ")) {
                ContentValues doctorInform = new ContentValues();
                doctorInform.put("TaiKhoanID", addAccount);
                doctorInform.put("KinhNghiem", exp);
                long addDoctorInform = db.insert("BacSi", null, doctorInform);
                if (addDoctorInform == -1)
                    return false;
            } else if (who.equals("Y tá")) {
                ContentValues nurseInform = new ContentValues();
                nurseInform.put("TaiKhoanID", addAccount);
                long addNurseInform = db.insert("YTa", null, nurseInform);
                if (addNurseInform == -1)
                    return false;
            } else {
                ContentValues adminInform = new ContentValues();
                adminInform.put("TaiKhoanID", addAccount);
                adminInform.put("ChucNang", role);
                long addAdminInform = db.insert("Admin", null, adminInform);
                if (addAdminInform == -1)
                    return false;
            }


        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi thêm người dùng: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return true;
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

        String queryDoctor = "SELECT TaiKhoanID FROM BacSi WHERE TaiKhoanID = ?";
        String queryPatient = "SELECT TaiKhoanID FROM BenhNhan WHERE TaiKhoanID = ?";
        String queryNurse = "SELECT TaiKhoanID FROM YTa WHERE TaiKhoanID = ?";
        String queryAdmin = "SELECT TaiKhoanID FROM Admin WHERE TaiKhoanID = ?";

        Cursor cursor = null;

        try {
            // Check Patient role
            cursor = db.rawQuery(queryPatient, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Benh nhan";
            }

            // Check Doctor role
            if (cursor != null) cursor.close(); // Close previous Cursor
            cursor = db.rawQuery(queryDoctor, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Bac si";
            }

            // Check Nurse role
            if (cursor != null) cursor.close(); // Close previous Cursor
            cursor = db.rawQuery(queryNurse, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Y ta";
            }

            if (cursor != null) cursor.close(); // Close previous Cursor
            cursor = db.rawQuery(queryAdmin, new String[]{id});
            if (cursor != null && cursor.getCount() == 1) {
                return "Admin";
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
            String userID = cursor.getString(cursor.getColumnIndex("TaiKhoanID"));
            String name = cursor.getString(cursor.getColumnIndex("HoTen"));
            String gender = cursor.getString(cursor.getColumnIndex("GioiTinh"));
            String dayOfBirth = cursor.getString(cursor.getColumnIndex("NgaySinh"));
            String phone = cursor.getString(cursor.getColumnIndex("SoDT"));
            String email = cursor.getString(cursor.getColumnIndex("Email"));

            ps = new PersonInformation(userID, name, gender, dayOfBirth, phone, email);
        }

        return ps;
    }

    public void updateInformation(String id, String name, String gender, String dayOfBirth, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE NguoiDung SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, SoDT = ?, Email = ? WHERE TaiKhoanID = ?";

        db.execSQL(query, new String[]{name, gender, dayOfBirth, phone, email, id});

        db.close();
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
    public List<Patient> showPatientForDoctor(String doctorID) {
        List<Patient> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT NguoiDung.HoTen, NguoiDung.TaiKhoanID, LichHen.TrieuChung " +
                "FROM LichHen " +
                "JOIN BenhNhan ON LichHen.BenhNhanID = BenhNhan.BenhNhanID " +
                "INNER JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE LichHen.isHen = 1 AND LichHen.isKham = 0 AND LichHen.BacSiID = ? AND LichHen.NgayKham =?";

        Cursor cursor = db.rawQuery(query, new String[]{doctorID, utils.getCurrentDate()});
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));
                    String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                    String trieuChung = cursor.getString(cursor.getColumnIndexOrThrow("TrieuChung"));
                    ls.add(new Patient(id, hoTen, trieuChung));
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

    public List<Patient> getDaySchedule(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Patient> ls = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Truy vấn dữ liệu
            String query = "SELECT TenLichHen, NgayKham, SoThuTuKham " +
                    "FROM LichHen " +
                    "WHERE BenhNhanID = ? AND isHuy = 0";
            cursor = db.rawQuery(query, new String[]{id});

            // Duyệt qua kết quả
            if (cursor.moveToFirst()) {
                do {
                    String tenLichHen = cursor.getString(cursor.getColumnIndexOrThrow("TenLichHen"));
                    String ngayKham = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));
                    Patient patient = new Patient(Integer.parseInt(id), ngayKham, tenLichHen);
                    ls.add(patient);
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

    public List<Patient> getScheduleAtDay(String id, String day) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Patient> ls = new ArrayList<>();
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
                    Patient patient = new Patient(ngayKham, tenLichHen);
                    ls.add(patient);
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
        String query = "SELECT BacSi.BacSiID, NguoiDung.HoTen, BacSi.KinhNghiem " +
                "FROM NguoiDung " +
                "INNER JOIN BacSi ON NguoiDung.TaiKhoanID = BacSi.TaiKhoanID";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("BacSiID"));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                String kinhNghiem = cursor.getString(cursor.getColumnIndexOrThrow("KinhNghiem"));

                Doctor doctor = new Doctor(id, hoTen, kinhNghiem);
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
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu ở chế độ ghi
        db.beginTransaction();
        try {
            String query = "SELECT LichHenID FROM LichHen WHERE BenhNhanID = ? AND BacSiID = ? AND NgayKham = ?";
            String lichHenID = "null";
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(query, new String[]{patientID, doctorID, utils.getCurrentDate()});
                if (cursor != null && cursor.moveToFirst()) {
                    lichHenID = cursor.getString(cursor.getColumnIndexOrThrow("LichHenID"));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            Log.d("1", lichHenID);


            // Thêm bản ghi vào bảng ToaThuoc
            ContentValues toaThuocValues = new ContentValues();
            toaThuocValues.put("NgayKeToa", utils.getCurrentDate());
            long toaThuocID = db.insert("ToaThuoc", null, toaThuocValues);

            // Thêm các thuốc vào bảng ToaThuoc_Thuoc
            for (Medicine medicine : medicines) {
                ContentValues med = new ContentValues();
                med.put("SoLuong", medicine.getQuantity());
                med.put("HuongDanSuDung", medicine.getUsage());
                med.put("ThuocID", medicine.getId());
                med.put("ToaThuocID", toaThuocID);

                Log.d("MedicineInsert", "SoLuong: " + medicine.getQuantity());
                Log.d("MedicineInsert", "HuongDanSuDung: " + medicine.getUsage());
                Log.d("MedicineInsert", "ThuocID: " + medicine.getId());
                Log.d("MedicineInsert", "ToaThuocID: " + toaThuocID);

                db.insert("ToaThuoc_Thuoc", null, med);
            }

            // Thêm bản ghi vào bảng KetQuaChuanDoan với LichHenID làm khóa chính
            ContentValues result = new ContentValues();
            result.put("LichHenID", lichHenID); // Sử dụng LichHenID làm khóa chính
            result.put("TenKetQuaChuanDoan", symp); // Chèn triệu chứng vào
            result.put("BacSiID", doctorID); // Chèn ID bác sĩ vào
            result.put("BenhNhanID", patientID); // Chèn ID bệnh nhân vào
            result.put("ToaThuocID", toaThuocID); // Chèn ID toa thuốc vào
            db.insert("KetQuaChuanDoan", null, result);

            // Cập nhật trạng thái đã khám trong bảng LichHen
            ContentValues updateValues = new ContentValues();
            updateValues.put("isKham", 1);

            String whereClause = "LichHenID = ?";
            String[] whereArgs = new String[]{String.valueOf(lichHenID)};

            db.update("LichHen", updateValues, whereClause, whereArgs);

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

    public List<Patient> showPatient() {
        List<Patient> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT NguoiDung.HoTen, NguoiDung.TaiKhoanID, LichHen.TrieuChung, LichHen.NgayKham " +
                "FROM LichHen " +
                "JOIN BenhNhan ON LichHen.BenhNhanID = BenhNhan.BenhNhanID " +
                "INNER JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE LichHen.isHen = 0 AND LichHen.isKham = 0 AND LichHen.isHuy = 0";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));
                    String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                    String trieuChung = cursor.getString(cursor.getColumnIndexOrThrow("TrieuChung"));
                    String ngayKham = cursor.getString(cursor.getColumnIndexOrThrow("NgayKham"));
                    ls.add(new Patient(id, hoTen, trieuChung, ngayKham));
                }
            } finally {
                cursor.close();
            }
        }
        return ls;
    }


    public boolean confirmExam(String id, String appointDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String query = "SELECT MAX(SoThuTuKham) FROM LichHen WHERE NgayKham = ?";
            Cursor cursor = db.rawQuery(query, new String[]{appointDay});

            int nextSoThuTuKham = 0;
            if (cursor != null && cursor.moveToFirst()) {
                int currentValue = cursor.getInt(0);
                nextSoThuTuKham = (currentValue == 0) ? 1 : currentValue + 1;
            }
            if (cursor != null) {
                cursor.close();
            }

            String whereClauseLichHen = "BenhNhanID = ? AND NgayKham = ?";
            String[] whereArgsLichHen = new String[]{getPatientID(id), appointDay};
            String bacSiID = getBacSiIDFromLichLamViec(appointDay);
            if (bacSiID == null)
                return false;

            ContentValues updateValues = new ContentValues();
            updateValues.put("BacSiID", bacSiID);
            updateValues.put("isHen", 1);
            updateValues.put("SoThuTuKham", nextSoThuTuKham);
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


    public boolean updateDoctor(String bacSiID, String ngayLam) {
        SQLiteDatabase db = this.getWritableDatabase();  // Mở kết nối với database (quyền ghi)

        ContentValues values = new ContentValues();
        values.put("BacSiID", bacSiID);  // Cập nhật trường BacSiID
        values.put("NgayLamViec", ngayLam);  // Cập nhật trường NgayLamViec
        long result = db.insert("LichLamViec", null, values);


        db.close();
        return result != -1;
    }

    public String getDoctorWorkAtDay(String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        String doctorName = null;

        // Corrected query
        String query = "SELECT NguoiDung.HoTen " +
                "FROM NguoiDung " +
                "JOIN BacSi ON BacSi.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "JOIN LichLamViec ON LichLamViec.BacSiID = BacSi.BacSiID " +
                "WHERE LichLamViec.NgayLamViec = ?";

        Cursor cursor = db.rawQuery(query, new String[]{day});
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) { // Chỉ lấy hàng đầu tiên
                    doctorName = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                }
            } finally {
                cursor.close(); // Đảm bảo đóng con trỏ
            }
        }
        return doctorName; // Trả về null nếu không có kết quả
    }

    public boolean changeDoctorWork(String id, String selectedDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NgayLamViec", selectedDate);

        int rowsAffected = db.update(
                "LichLamViec",
                values,
                "BacSiID = ?",
                new String[]{id}
        );

        db.close();
        return rowsAffected > 0;
    }


    public boolean cancelDay(String id, String day) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isHuy", 1);

        int rowsAffected = db.update(
                "LichHen",
                values,
                "BenhNhanID = ? AND NgayKham = ?",
                new String[]{id, day}
        );

        db.close();
        return rowsAffected > 0;
    }

    public List<Medicine> getResult(String id, String day) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Medicine> rs = new ArrayList<>();

        // Truy vấn SQL để lấy thông tin thuốc và ngày khám
        String query = "SELECT T.TenThuoc, T.DonVi, TT_Thuoc.SoLuong, TT_Thuoc.HuongDanSuDung " +
                "FROM KetQuaChuanDoan KQCD " +
                "JOIN ToaThuoc TT ON KQCD.ToaThuocID = TT.ToaThuocID " +
                "JOIN ToaThuoc_Thuoc TT_Thuoc ON TT.ToaThuocID = TT_Thuoc.ToaThuocID " +
                "JOIN Thuoc T ON TT_Thuoc.ThuocID = T.ThuocID " +
                "JOIN LichHen L ON KQCD.LichHenID = L.LichHenID " +
                "WHERE KQCD.BenhNhanID = ? AND L.NgayKham = ?";  // Thêm điều kiện lọc theo NgayKham

        Cursor cursor = db.rawQuery(query, new String[]{id, day});

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String tenThuoc = cursor.getString(cursor.getColumnIndexOrThrow("TenThuoc"));
                    String donVi = cursor.getString(cursor.getColumnIndexOrThrow("DonVi"));
                    String soLuong = cursor.getString(cursor.getColumnIndexOrThrow("SoLuong"));
                    String huongDan = cursor.getString(cursor.getColumnIndexOrThrow("HuongDanSuDung"));

                    Medicine mc = new Medicine(tenThuoc, donVi, huongDan, soLuong); // Truyền NgayKham vào constructor của Medicine
                    rs.add(mc);
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return rs;
    }


    public List<Role> showInformationForPerson() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Role> rs = new ArrayList<>();

        String query = "SELECT * FROM TaiKhoan ";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));
                Role role = new Role(getInformation(id), getRole(id));
                rs.add(role);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return rs;
    }

    public boolean lockAccount(String patientID) {
        SQLiteDatabase db = this.getWritableDatabase(); // Sử dụng writable database để thực hiện update

        ContentValues values = new ContentValues();
        values.put("HoatDong", 0);

        int rowsAffected = db.update(
                "TaiKhoan",
                values,
                "TaiKhoanID = ?",
                new String[]{patientID}
        );

        db.close();
        return rowsAffected > 0;
    }

    public boolean unlockAccount(String patientID) {
        SQLiteDatabase db = this.getWritableDatabase(); // Sử dụng writable database để thực hiện update

        // Cập nhật giá trị HoatDong thành 1 (mở khóa)
        ContentValues values = new ContentValues();
        values.put("HoatDong", 1);

        int rowsAffected = db.update(
                "TaiKhoan",
                values,
                "TaiKhoanID = ?", // Điều kiện WHERE
                new String[]{patientID} // Giá trị cho điều kiện
        );

        db.close();
        return rowsAffected > 0;
    }

    public boolean isAccountActive(String patientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT HoatDong FROM TaiKhoan WHERE TaiKhoanID = ?",
                new String[]{patientID}
        );
        boolean isActive = false;
        if (cursor.moveToFirst()) {
            int hoatDongValue = cursor.getInt(0);
            if (hoatDongValue == 1) {
                isActive = true;
            }
        }
        cursor.close();

        db.close();

        return isActive;
    }


    @SuppressLint("Range")
    public String getSympton(String patientID, String day) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String symptom = null;

        try {
            // Truy vấn SQL để lấy TenKetQuaChuanDoan dựa vào BenhNhanID và NgayKham
            String query = "SELECT KQCD.TenKetQuaChuanDoan " +
                    "FROM KetQuaChuanDoan KQCD " +
                    "JOIN LichHen LH ON KQCD.LichHenID = LH.LichhenID " +
                    "WHERE KQCD.BenhNhanID = ? AND LH.NgayKham = ?";
            cursor = db.rawQuery(query, new String[]{patientID, day});

            // Kiểm tra và lấy kết quả nếu có
            if (cursor != null && cursor.moveToFirst()) {
                symptom = cursor.getString(cursor.getColumnIndex("TenKetQuaChuanDoan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return symptom;
    }


    public String getIDFromPatientID(String patientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TaiKhoanID " +
                "FROM BenhNhan " +
                "WHERE BenhNhanID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{patientID});
        String tkID = "";
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    // Retrieve BenhNhanID from the first row of the result
                    tkID = cursor.getString(cursor.getColumnIndexOrThrow("TaiKhoanID"));
                }
            } finally {
                cursor.close(); // Ensure the cursor is closed to avoid resource leaks
            }
        }
        return tkID;
    }

    public boolean changePassword(String id, String newPass, String oldPass) {
        SQLiteDatabase db = this.getWritableDatabase(); // Sử dụng writable database để thực hiện update


        ContentValues values = new ContentValues();
        values.put("MatKhau", newPass);

        int rowsAffected = db.update(
                "TaiKhoan",
                values,
                "TaiKhoanID = ? AND MatKhau = ?",
                new String[]{id, oldPass}
        );

        db.close();
        return rowsAffected > 0;
    }

    public List<String> getAllDayDoctorWork(String bacSiID) {
        List<String> ngayLamList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Lấy đối tượng SQLiteDatabase để đọc dữ liệu

        // SQL truy vấn lấy NgayLam từ bảng LichLamViec theo BacSiID
        String query = "SELECT NgayLamViec FROM LichLamViec WHERE BacSiID = ?";

        // Thực hiện truy vấn và lấy con trỏ (Cursor)
        Cursor cursor = db.rawQuery(query, new String[]{bacSiID});

        // Kiểm tra nếu có dữ liệu
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String ngayLam = cursor.getString(cursor.getColumnIndexOrThrow("NgayLamViec"));
                ngayLamList.add(ngayLam);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close(); // Đóng cơ sở dữ liệu
        return ngayLamList; // Trả về danh sách NgayLam
    }

    public List<Comment> getCommentByDoctorID(String doctorID) {
        List<Comment> cms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Define the query
        String query = "SELECT DanhGia.NoiDung, NguoiDung.HoTen " +
                "FROM DanhGia " +
                "JOIN BenhNhan ON BenhNhan.BenhNhanID = DanhGia.BenhNhanID " +
                "JOIN NguoiDung ON BenhNhan.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE BacSiID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{doctorID});


        if (cursor.moveToFirst()) {
            do {
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));
                String noiDung = cursor.getString(cursor.getColumnIndexOrThrow("NoiDung"));

                Comment comment = new Comment(hoTen, noiDung);
                cms.add(comment);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return cms;
    }

    public List<Doctor> getDoctorsByPatientID(String patientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Doctor> doctors = new ArrayList<>();

        String query = "SELECT DISTINCT BacSi.BacSiID, BacSi.KinhNghiem, NguoiDung.HoTen " +
                "FROM LichHen " +
                "JOIN BacSi ON LichHen.BacSiID = BacSi.BacSiID " +
                "JOIN NguoiDung ON BacSi.TaiKhoanID = NguoiDung.TaiKhoanID " +
                "WHERE LichHen.BenhNhanID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{patientID});

        if (cursor.moveToFirst()) {
            do {
                String doctorID = cursor.getString(cursor.getColumnIndexOrThrow("BacSiID"));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"));

                // Assuming the Doctor class has a constructor with these parameters
                Doctor doctor = new Doctor(doctorID, hoTen);
                doctors.add(doctor);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return doctors;
    }


    public boolean commentDoctor(String patientID, Doctor selectedDoctor, String commentContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("NoiDung", commentContent);
        values.put("BacSiID", selectedDoctor.getId());
        values.put("BenhNhanID", Integer.parseInt(patientID));

        long result = db.insert("DanhGia", null, values);

        db.close();

        return result != -1;
    }

    public int countPatient(String timePeriod) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        // Query to count patients based on month or year, adjust for dd/MM/yyyy format
        String query = "SELECT COUNT(BenhNhanID) AS PatientCount " +
                "FROM LichHen " +
                "WHERE strftime('%m', date(substr(NgayKham, 7, 4) || '-' || substr(NgayKham, 4, 2) || '-' || substr(NgayKham, 1, 2))) = ? " +
                "AND isKham = 1";

        Cursor cursor = db.rawQuery(query, new String[]{timePeriod});

        // Retrieve the count from the result
        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndexOrThrow("PatientCount"));
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return count;
    }


}