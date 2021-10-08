package com.example.sampleproject_mob2041

import android.content.Context
import com.example.sampleproject_mob2041.database.*
import com.example.sampleproject_mob2041.model.*
import kotlin.random.Random

class InitDataExample {
    private lateinit var database: Database
    private lateinit var bookDB: BookDB
    private lateinit var callCardDB: CallCardDB
    private lateinit var customerDB: CustomerDB
    private lateinit var genreDB: GenreDB
    private lateinit var librarianDB: LibrarianDB

    fun initData(mContext: Context) {
        database = Database(mContext)

        genreDB = GenreDB(database)
        initGenre()

        bookDB = BookDB(database)
        initBookDB()

        customerDB = CustomerDB(database)
        initCustomer()

        librarianDB = LibrarianDB(database)
        initLibrarian()

        callCardDB = CallCardDB(database)
        initCallCard()
    }

    private fun initGenre() {
        val list = ArrayList<Genre>()
        list.apply {
            add(Genre("Tiểu thuyết"))
            add(Genre("Chính trị – pháp luật"))
            add(Genre("Khoa học công nghệ – Kinh tế"))
            add(Genre("Văn học nghệ thuật"))
            add(Genre("Văn hóa xã hội – Lịch sử"))
            add(Genre("Giáo trình"))
            add(Genre(" Truyện, tiểu thuyết"))
            add(Genre("Tâm lý, tâm linh, tôn giáo"))
            add(Genre("Sách thiếu nhi"))
        }
        for (element in list) {
            genreDB.addGenre(element.name)
        }
    }

    private fun initBookDB() {
        val list = ArrayList<Book>()
        val genreList = genreDB.getAllGenres()

        list.apply {
            add(Book("Chiến tranh và hòa bình", genreList[0].name, 15000.0))
            add(Book("Điều lệ Đảng Cộng sản Việt Nam", genreList[1].name, 5000.0))
            add(Book("Tôi tài giỏi, bạn cũng thế!", genreList[2].name, 10000.0))
            add(Book("Chí Phèo", genreList[3].name, 5000.0))
            add(Book("Lịch sử loài người", genreList[4].name, 20000.0))
            add(Book("Giáo trình Java cơ bản", genreList[5].name, 12000.0))
            add(Book("Dế mèn phiêu lưu ký", genreList[6].name, 5000.0))
            add(Book("Nhà nàng ở cạnh nhà tôi", genreList[7].name, 10000.0))
            add(Book("Cẩm nang làm việc nhà cho bé", genreList[8].name, 5000.0))
        }
        for (element in list) {
            bookDB.addBook(element.name, element.genre, element.price)
        }
    }

    private fun initCustomer() {
        val list = ArrayList<Customer>()
        list.apply {
            add(Customer("Nông Ngọc Diệu", "0965343641", "Phúc Diễn, Bắc Từ Liêm, Hà Nội"))
            add(Customer("Phạm Lưu Huỳnh", "0942756351", "Phúc Diễn, Bắc Từ Liêm, Hà Nội"))
            add(Customer("Nguyễn Mạnh Hùng", "03525773216", "Mậu A, Văn Yên, Yên Bái"))
            add(Customer("Mai Quang Anh", "0351538503", "Quang Minh, Văn Yên, Yên Bái"))
        }

        for (element in list) {
            customerDB.addCustomer(element.name, element.phoneNumber, element.address)
        }
    }

    private fun initLibrarian() {
        val list = ArrayList<Librarian>()
        list.apply {
            add(Librarian("nongngocdieu", "Nông Ngọc Diệu", "nongngocdieu"))
            add(Librarian("phamluuhuynh", "Phạm Lưu Huỳnh", "phamluuhuynh"))
        }

        for (element in list) {
            librarianDB.addLibrarian(element.loginName, element.displayName, element.password)
        }
    }

    private fun initCallCard() {
        val bookList = bookDB.getAllBooks()
        val customerList = customerDB.getAllCustomer()
        val genreList = genreDB.getAllGenres()
        val librarianList = librarianDB.getAllLibrarians()

        val callCardList = ArrayList<CallCard>()
        callCardList.apply {
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[0].name,
                    genre = genreList[0].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "12/03/2021",
                    isReturned = "0",
                    price = bookList[0].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[1].name,
                    genre = genreList[1].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "17/05/2021",
                    isReturned = "1",
                    price = bookList[1].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[2].name,
                    genre = genreList[2].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "07/09/2021",
                    isReturned = "0",
                    price = bookList[2].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[4].name,
                    genre = genreList[4].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "12/03/2021",
                    isReturned = "0",
                    price = bookList[4].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[5].name,
                    genre = genreList[5].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "04/09/2021",
                    isReturned = "0",
                    price = bookList[5].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[6].name,
                    genre = genreList[6].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "11/02/2021",
                    isReturned = "0",
                    price = bookList[6].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[0].name,
                    genre = genreList[0].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "12/03/2021",
                    isReturned = "0",
                    price = bookList[0].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[1].name,
                    genre = genreList[1].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "17/05/2021",
                    isReturned = "1",
                    price = bookList[1].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[2].name,
                    genre = genreList[2].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "07/09/2021",
                    isReturned = "0",
                    price = bookList[2].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[4].name,
                    genre = genreList[4].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "12/03/2021",
                    isReturned = "0",
                    price = bookList[4].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[5].name,
                    genre = genreList[5].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "04/09/2021",
                    isReturned = "0",
                    price = bookList[5].price
                )
            )
            add(
                CallCard(
                    ID = null,
                    customerName = customerList[(0..3).random()].name,
                    bookName = bookList[6].name,
                    genre = genreList[6].name,
                    librarianName = librarianList[(0..1).random()].displayName,
                    borrowTime = "11/02/2021",
                    isReturned = "0",
                    price = bookList[6].price
                )
            )
        }
        for (element in callCardList) {
            callCardDB.addCallCard(
                element.ID,
                element.customerName,
                element.bookName,
                element.genre,
                element.librarianName,
                element.borrowTime,
                element.isReturned,
                element.price
            )
        }
    }
}