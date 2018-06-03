export class Reservationmodel {
    code: string;
    message: string;
    data:string;
    id: string;
    userId: string;
    reservationcount: string;
    bookname: string;
    bookId: string;
    userName: string;
    isbn: string;
    picture:string;
    location:string;
}



    export class ReservationViewModel {
        id: string;
        isDelete: number;
        createTime: number;
        operator?: any;
        updateTime: number;
        isbn: string;
        isannounce: number;
        remark?: any;
        status: number;
        userId: string;
        viewUserId: string;
        studentNo?: any;
        studentName?: any;
        professionName?: any;
        classesName?: any;
        bookName: string;
        viewCreateTime?: any;
        picture: string;
        count?: any;
        borrowDate?: any;
        //0可借 1不可借
        isBorrowed: number;
        authorName: string;
        viewIsbn?: any;
        location:string;
    }




