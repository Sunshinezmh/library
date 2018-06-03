export class BorrowingModel{
    bookId: string;
    endDate: Date;
    isOverdue: number;
    realDate: Date;
    autor: string;
    professionName: string;
    bookName?: string;
    picture: string;
    total: number;
    studentCode: number;
    remain: String;//剩余数量
    pagesize: number;
    pagenum: number;
    borrowTime: Date;
    renew: number;
    searchNum: string;
    studentId: string;
    studentName: string;
    name: string;
    id: string;
    className: string;
    sex?:string;
    count?:number;
    version?:string;//版次
    code: string;
    classesName:string;
    userId:string; //学号，编辑用
    location:string; //书籍位置
    isbn:string;
    
    }
    declare module namespace {
        
            export interface RootObject {
                content: string;
                code: string;
                picture: string;
                author: string;
                searchNum: string;
                renew: number;
                borrowStatus: number;
                borrowTime: Date;
                endDate: Date;
                realDate: Date;
                bookId: string;
                isOverdue: number;
                bookName: string;
                classesName: string;
                studentName: string;
                professionName: string;
                id: string;
                createTime: Date;
                operator: string;
                updateTime: Date;
                isDelete: number;
            }
        
        }
        
        