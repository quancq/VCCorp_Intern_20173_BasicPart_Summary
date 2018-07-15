# Java Core


# 1. Concurrency
## 1.1. Khái niệm
> *Concurrency là khả năng chạy nhiều chương trình hoặc nhiều đoạn chương trình song song*

## 1.2. Tổng quan lập trình song song
### Tại sao cần thực hiện song song ?
* Giảm thời gian thực hiện
* Tận dụng được tối đa tài nguyên máy tính (Mặc định chương trình chỉ được chạy trên 1 thread, khi đó không sử dụng được các core khác trong máy tính nhiều core).
### Các vấn đề chính
* Process (tiến trình) và Thread (luồng)
    * Process
        * Mỗi process chạy độc lập với nhau
        * Các process không chia sẻ dữ liệu trực tiếp (mỗi process có không gian địa chỉ vùng nhớ riêng biệt do hệ điều hành quản lý, cấp phát). Vì vậy cần có *cơ chế truyền thông* giữa các process để trao đổi dữ liệu
    * Thread
        * Các thread của cùng 1 process có thể chia sẻ trực tiếp dữ liệu (có chung không gian địa chỉ vùng nhớ), không cần truyền thông trao đổi dữ liệu, nhưng cần xử lý vấn đề *tranh chấp tài nguyên*
        * **Ứng dụng Java sử dụng 1 process. Nhiệm vụ của lập trình viên là khai thác được việc lập trình song song ở mức thread !**
* Tranh chấp tài nguyên giữa các thread
    * Khi 1 vùng nhớ được đọc - ghi bởi nhiều thread, cần đảm bảo mỗi thread luôn đọc nội dung mới nhất của vùng nhớ
    * Khi 1 thread đang thực hiện ghi vào vùng nhớ thì các thread khác không được đọc, ghi. *Cần tránh trường hợp deadlock - khi mà mỗi thread nắm giữ 1 tài nguyên mà để thực hiện tiếp công việc thì lại cần tài nguyên mà thread khác đang nắm giữ*
* Cần có cơ chế truyền thông giữa các process
* **Chú ý: Không phải cứ sử dụng thật nhiều thread là tối ưu. Số thread phù hợp được quyết định dựa trên đặc điểm chương trình và tài nguyên hiện có**
## 1.3. Mục đích sử dụng package java.util.concurrent

### So sánh với các giải pháp hiện có
* Trước đây để lập trình song song trong Java ta sử dụng Thread, Runnable.
    * Sơ lược về cách sử dụng Thread và Runnable
        * Để mô tả các task cần thực hiện song song: ta implement interface Runnable, override Run method (nội dung công việc được cài đặt trong hàm Run).
        * Khởi tạo 1 đối tượng Thread với constructor nhận tham số là 1 đối tượng Runnable. Sau đó gọi hàm start của thread để thực hiện công việc
```java
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					// do something
					String threadName = Thread.currentThread().getName();
					System.out.println("Thread name : " + threadName);
					TimeUnit.SECONDS.sleep(1);
					System.out.println("Thread name : " + threadName);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
```
    
   * Nhược điểm của Thread
       * Khó quản lý số lượng thread. Việc sử dụng quá nhiều thread sẽ làm giảm hiệu năng, có nguy cơ tràn bộ nhớ.
###*Concurrency API ra đời với nhiều tính năng hỗ trợ lập trình song song*

## 1.4. Tổng quan kiến trúc
### 1.4.1. Executor
### 1.4.2. Callable và Future
## 1.5. Tài liệu tham khảo
* [Java concurrency](http://www.vogella.com/tutorials/JavaConcurrency/article.html) 
* [Java 8 Concurrency Tutorial: Threads and Executors (3 Parts)](http://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/) 

# 2. Exception
# 3. Collection