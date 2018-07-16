# Database

## 1. Chuẩn hóa cơ sở dữ liệu (Database Normalization)
### 1.1. Định nghĩa
> Chuẩn hóa cơ sở dữ liệu là tiến trình tổ chức cơ sở dữ liệu thành các bảng và các cột theo 1 số quy tắc để mang lại một số lợi ích.

### 1.2. Tại sao cần chuẩn hóa
#### Chuẩn hóa CSDL sẽ giúp:
* Giảm thiểu sự trùng lặp dữ liệu (tiết kiệm bộ nhớ)
* Giảm thiểu và tránh các vấn đề nảy sinh khi chỉnh sửa dữ liệu
* Tạo sự đơn giản khi truy vấn
#### Xét ví dụ CSDL không được chuẩn hóa để nhận ra những vấn đề
![Example for non normalization database](./Images/Example_Database_NonNormalization.png)
*Khóa chính là EmployeeID*
**Nhận xét: Bảng này phục vụ cho 3 mục đích:**

* Định danh nhân viên kinh doanh
* Chứa thông tin miêu tả văn phòng
* Liệt kê các khách hàng của họ

**Thông thường khi thiết kế CSDL tốt thì mỗi bảng chỉ phục vụ cho 1 mục đích**

#### Các vấn đề gặp phải với bảng SalesStaff trên:
* Vấn đề khi mở rộng dữ liệu
    * Nếu nhân viên có nhiều khách hàng hơn thì ta cần mở rộng CSDL theo chiều ngang (tăng số lượng cột). Điều này khó hơn mở rộng theo chiều dọc (thêm số bản ghi) bởi vì ta phải thay đổi cấu trúc bảng
* Trùng lặp (dư thừa) dữ liệu
    * Với mỗi cặp văn phòng và số điện thoại văn phòng được lưu nhiều lần
    * Sự trùng lặp này khiến tốn bộ nhớ, giảm hiệu năng và dẫn đến các vấn đề bên dưới
* Vấn đề khi thêm bản ghi
    * Khi thêm 1 văn phòng mới thì ta không thêm được vào bảng SalesStaff do chứa có thông tin khóa chính EmployeeID. Nếu có bảng chỉ phục vụ cho mục đích quản lý văn phòng thì ta dễ dàng thêm được bản ghi mới
    ![Insert Anomaly](./Images/InsertAnomaly.png)
* Vấn đề khi cập nhật bản ghi
    * Khi cập nhật số điện thoại cho 1 văn phòng thì do trùng lặp dữ liệu nên ta cần cập nhật trên nhiều bản ghi (tốn thời gian, nếu 1 trong số sự cập nhật không thành công thì CSDL trờ nên không thống nhất)
    ![Update Anomaly](./Images/UpdateAnomaly.png)
* Vấn đề khi xóa bản ghi
    * Khi xóa thông tin về 1 nhân viên (John - 1004) thì dẫn đến việc xóa mất thông tin về văn phòng New York)
    ![Delete Anomaly](./Images/DeleteAnomaly.png)
* Vấn đề khi truy vấn
    * Khi cần tìm những nhân viên có khách hàng tên là Ford thì ta cần truy vấn như sau
    ```
    Select SalesPerson
    From SalesStaff
    Where Customer1 = 'Ford' Or
          Customer2 = 'Ford' Or
          Customer3 = 'Ford'
    ```
    Truy vấn sẽ trở nên *dài* khi số khách hàng tăng
    * Khi cần sắp xếp các bản ghi theo cột khách hàng thì rất khó để truy vấn


### 1.3. Các dạng chuẩn
#### Các dạng chuẩn thường gặp và điều kiện phải thỏa mãn
* Chuẩn 1NF:
    * Mỗi cột chứa giá trị nguyên tử (atomic value)
    * Không có các cột biểu diễn các thông tin tương tự nhau
* Chuẩn 2NF:
    * CSDL thỏa mãn dạng chuẩn 1NF
    * Mọi cột trong 1 bảng phải phụ thuộc toàn phần vào khóa chính (không được phụ thuộc vào chỉ một số cột trong khóa chính)
* Chuẩn 3NF:
    * CSDL thỏa mãn dạng chuẩn 2NF
    * Mọi cột trong 1 bảng không được phụ thuộc bắc cầu vào khóa chính
* Chuẩn BoyceCodd BCNF:
* Chuẩn 4NF:

## 2. Một số mệnh đề cơ bản

## 3. Các loại Join

## 4. View

## 5. Store Procedure

## 6. Trigger

## 7. Index

## Tài liệu tham khảo
* [Database Normalization Explained in Simple English](https://www.essentialsql.com/get-ready-to-learn-sql-database-normalization-explained-in-simple-english/) 
* 