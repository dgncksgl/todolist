# TODOLIST

## Proje Özeti

* Proje restful web uygulaması olarak geliştirilmiştir.
* Uygulama Spring Boot framework'ünde geliştirilmiştir ve Spring Boot 3.0.6 versiyonu kullanılmıştır.
* Java 17 kullanılmıştır. Uygulamanın ayağa kalkması için JDK-17'nin kurulu olması gerekmektedir.
* Verilerin saklanması için Couchbase veritabanı kullanılmıştır.
* Yazılan restful servislerin açıklamalı biçimde görüntülenmesi ve tetiklenmesi için Swagger kullanılmıştır.
* Uygulama 8081 portunda çalışmaktadır.
* IntelliJ IDE'si üzerinde geliştirme yapılmıştır.

## Uygulamanın Ayağa Kaldırılması

**NOTLAR**

**Uygulama IDE üzerinden değilde docker üzerinden ayağa kaldırılacak ise ayağa kaldırılacak makinede Docker Server'ın
kurulu olması gerekmektedir.**

* Proje içeriğinde bulunan Dockerfile dosyası vasıtasıyla, uygulamanın Docker Image'i oluşturulmuştur.
* Oluşturulan image Docker Hub'a yüklenmiştir. [buradaki link'e tıklayarak](https://hub.docker.com/r/docker0651/todolist)
  docker image'e erişim sağlanıp görüntüleme yapılabilir.
* Uygulama içeriğinde bulunan `docker-compose.yaml` dosyasının bulunduğu dizinde `docker-compose up -d` komutu
  çalıştırılarak uygulama ve uygumanın kullandığı Couchbase server'ın ayağa kaldırılması sağlanabilir.
* Uygulamalar ayağa kaldırıldıktan sonra [buradaki adrese gidilerek](http://localhost:8091) couchbase içerisinde
  `todolist_bucket` adında bucket oluşturulması gerekmektedir.
* Bir süre sonra uygulama connection sağlayarak sağlıklı şekilde çalışmaya başlayacaktır.
* Eğer var olan bir couchbase server var ve bu server içerisinde uygun bir bucket var ise docker-compose.yaml dosyası
  içerisinde `todolist` tag'i altında bulunan `environment` tag'i altındaki parametreler değiştirilerek istenilen counchbase'e
  bağlantı sağlanabilir. Bu durumda docker-compose.yaml dosyası içerisindeki couchbase tag'i kaldırılarak
  `docker-compose up -d` komutu çalıştırılabilir.

## Servislere erişim

* Uygulama ayağa kalktıktan sonra [buradaki link'e tıklayarak](http://localhost:8081/api/swagger-ui/index.html)
  swagger'a erişebilirsiniz.
* Swagger üzerinden incelendiğinde geliştirilen servisler 3 grup altında toplanmıştır. Bunlar;
    * User Services: Kullanıcı CRUD işlemlerinin yapılabileceği servislerdir. Bunlar get, put, post, delete
      işlemleridir. Kullanıcıları listeleyebilir, kaydedebilir, güncelleyebilir ve silebilirsiniz.
    * TodoList Services: TodoList CRUD işlemlerinin yapılabileceği servislerdir. Bunlar get, put, post, delete
      işlemleridir. TodoList kayıtlarını listeyebilir, kaydedebilir, güncelleyebilir ve silebilirsiniz.
    * Auth Services: Kullanıcıların `Authentication` işlemlemlerinin yapılabileceği servislerdir.
        * `username` ve `password` bilgileri ile `sign-in` servisi üzerinden `authenticate` işlemini gerçekleştirip,
          `accessToken` ve `refresToken` üreterek diğer servislere erişim sağlanabilir.
        * Üretilen `accessToken` bilgisi, diğer servislerin `401` hatası almadan çalışması için swagger'da bulunan
          'Authorize' butonuna tıklandıktan sonra çıkan pop-up'ın `value` alanına girilerek `Authorize` butonuna tıklanılmalıdır.
          Bu şekilde token bilgisi diğer servisler içinde kullanılabilir hale gelecektir.

## Proje Akışı

**NOTLAR**

* **Servis endpoint'lerinde `/api` prefix'i bulunmaktadır.**
* **Servislerde kullanılan request ve response yapıları ve bu yapılardaki parameterler swagger üzerinde açıklanmıştır.**

### Authentication İşlemleri Akışı

* Uygulama ayağa kalktığında, bir kullanıcı otomatik insert edilecetir.
  `username:admin` ve `password:admin` kullanıcısı sign-in servisinden accessToken alınabilir.
* POST `/sign-in/` servislere erişim için authentication işlemlerinin yapıldığı ve token üretilen servistir.
* `todolist.jwt.secret` parametresi kullanılarak accessToken üretiminde kullanılacak secretKey değeri girilebilir.
* `todolist.jwt.expiration-time` parametresi kullanılarak accessToken'ın kullanım süresi milisaniye cinsinden verilebilir.
* POST `/refresh-token/` accessToken'in kullanım süresi dolduğunda, kullanıcının sign-out olmadan servisleri kullanmaya
  devam etmesi için refresh-token bilgisi üzerinden tekrar accessToken üretilmesini sağlayan servistir.
* `todolist.jwt.refresh-expiration-time` parametersi kullanılarak refreshToken'ın kullanım süresi milisaniye cinsinden verilebilir.

### Kullanıcı İşlemleri Akışı

* Servis Erişimi başlığı altında anlatılan şekilde AccessToken kullanılarak,
  `User Services` tag'i altında bulunan servisler kullanılabilir.
* POST `/users/` servisi kullanılarak yeni kullanıcılar eklenebilir.
* PUT `/users/` servisi kullanılarak kaydedilen kullanıcı bilgileri güncellenebilir.
* DELETE `/users/{id}` servisi kullanılarak kaydedilen kullanıcı silinebilir.
* GET `/users/{id}` servisi kullanılarak kaydedilen kullanıcının bilgi detayları görüntülebilir.
* GET `/users/` servisi kullanılarak kaydedilen kullanıcıların listesi görüntülenebilir.
* Kullanıcı üzerinde todolist kaydı bulunuyorsa silme işleminde `User is not deleted due to the existence of related data`
  hatası alınacaktır.

### TodoList İşlemleri Akışı

* Servis Erişimi başlığı altında anlatılan şekilde AccessToken kullanılarak,
  `TodoList Services` tag'i altında bulunan servisler kullanılabilir.
* POST `/todo-lists/` servisi kullanılarak yeni todolist kayıtları eklenebilir.
* todolist kaydı oluşturulurken o onda login olan kullanıcı üzerine kayıt oluşturulmaktadır.
* PUT `/todo-lists/` servisi kullanılarak kaydedilen todolist kayıt bilgileri güncellenebilir.
* DELETE `/todo-lists/{id}` servisi kullanılarak kaydedilen todolist kaydı silinebilir.
* GET `/todo-lists/{id}` servisi kullanılarak kaydedilen todolist kaydının bilgi detayları görüntülebilir.
* GET `/todo-lists/` servisi kullanılarak kaydedilen todolist kayıtlarının listesi görüntülenebilir.
* GET `/todo-lists/item-priority` servisi kullanılarak todolist kayıtlarında kullanılacak kayıt önceliklendirmesi için
  kullanılacak enum listesi görüntülenebilir.

## Test

* Proje içi yazılan unit testlere `/src/test` klasörü altında bulunmaktadır.
* Unit testler JUnit 5 ile yazılmıştır.