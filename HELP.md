# TODOLIST

### Proje Özeti

* Proje restful web uygulaması olarak geliştirilmiştir.
* Spring Boot framework'ünde geliştirme yapılmıştır ve Spring Boot versiyonu 3.0.6'dir.
* Java 17 kullanılmıştır. Uygulamanın ayağa kalkması için JDK-17'nin kurulu olması gerekmektedir.
* Verilerin saklanması için In-memory veritabanı kullanılmıştır.
* Yazılan restful servislerin açıklamalı biçimde görüntülenmesi ve tetiklenmesi için Swagger kullanılmıştır.
* Uygulama 8081 portunda çalışmaktadır.
* IntelliJ IDE'si üzerinde geliştirme yapılmıştır.

### Servislere erişim

* Uygulama ayağa kalktıktan sonra [buradaki link'e tıklayarak](http://localhost:8081/api/swagger-ui/index.html) swagger'a
  erişebilirsiniz.
* Swagger üzerinden incelendiğinde geliştirilen servisler 3 grup altında toplanmıştır. Bunlar;
  * User Services: Kullanıcı CRUD işlemlerinin yapılabileceği servislerdir. Bunlar get, put, post, delete
    işlemleridir. Kullanıcıları listeleyebilir, kaydedebilir, güncelleyebilir ve silebilirsiniz.
  * TodoList Services: TodoList CRUD işlemlerinin yapılabileceği servislerdir. Bunlar get, put, post, delete
    işlemleridir. TodoList kayıtlarını listeyebilir, kaydedebilir, güncelleyebilir ve silebilirsiniz.
  * Auth Services: Kullanıcıların `Authentication` işlemlemlerinin yapılabileceği servislerdir. 
    * `username` ve `password` bilgileri ile `sign-in` servisi üzerinden `authenticate` işlemini gerçekleştirip,
    `accessToken` ve `refresToken` üreterek diğer servislere erişim sağlanabilir.
    * Üretilen `accessToken` bilgisi diğer servislerin `401` hatası almadan çalışması için swagger'da bulunan 'Authorize'
    butonuna tıklandıktan sonra çıkan pop-up'ın `value` alanına girilerek `Authorize` butonuna tıklanılmalıdır. 
    Bu şekilde token bilgisi diğer servisler içinde kullanılabilir hale gelecektir.
 