export class CollectionModel{
    isbn: string;
    collectionId:string;
    summary: string;
    author: string;
    bid:number;
    searchNum: string;
    remark: string;
    owner: string;
    bookName: string;
    totalpage: number;
    publishtime:Date;
    updatetime:Date ;
    typeName: string;
    publishPlace: string;
    picture: string;
    origin: string;
    useNum: number;
    primCost: number;
    rank: number;
    sellPrice: number;
    language: string;
    id: string;
    content: string;
    location:string;
}

 export class CollectionAddModel {
        userId: string;
        remark: string;
        isbn: string;
        id: string;
        createTime: Date;
        operator: string;
        updateTime: Date;
        isDelete: number;
        location:string;
}

