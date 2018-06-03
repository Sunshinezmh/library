import { Component, OnInit, AfterViewInit } from '@angular/core';

import { BarcodeModel } from '../../models/barcode.model';

declare var JsBarcode: any;

@Component({
  selector: 'app-print-page',
  templateUrl: './print-page.component.html',
  styleUrls: ['./print-page.component.css']
})
export class PrintPageComponent implements OnInit, AfterViewInit {

  barcodeList = new Array<BarcodeModel>();

  //显示书的类别
  typeNameList = new Array();
  constructor() { }

  ngOnInit() {
    let print = localStorage.getItem("printbarcode");
    if (print == null) {
      return;
    }
    this.barcodeListInit(print);
  }

  //界面初始化后，显示条形码
  ngAfterViewInit() {
    this.barcodeInit();

  }


  //循环显示条形码
  barcodeInit() {
    for (let i = 0; i < this.barcodeList.length; i++) {
      this.generateBarcode("bc_" + i, this.barcodeList[i].printSearchNum.toString());
    }
  }

  //初始化索书号
  barcodeListInit(print: string) {
    let objList = new Array<BarcodeModel>()
    objList = JSON.parse(print);
    for (let i = 0; i < objList.length; i++) {

      //前13为索书号
      let printSearchNum = objList[i].printSearchNum.substring(0, objList[i].printSearchNum.length - 2);
      //索书号后两位
      let extPrintSearchNum = objList[i].printSearchNum.substring(objList[i].printSearchNum.length - 2, objList[i].printSearchNum.length);
      let printNumber = objList[i].printNumber;
      let printBookName = objList[i].printBookName;
      let typeName = objList[i].typeName;
      for (let j = 0; j < printNumber; j++) {
        let model = new BarcodeModel();
        model.printNumber = printNumber;
        model.printBookName = printBookName;
        let extNum = parseInt(extPrintSearchNum) + j;
        if (extNum < 10) {
          model.printSearchNum = printSearchNum + "0" + extNum;
        } else {
          model.printSearchNum = printSearchNum + extNum;
        }
        model.typeName = typeName;
        
        this.barcodeList.push(model);
      }
    }

    localStorage.removeItem("printbarcode")
  }



  /* 生成条形码 */
  generateBarcode(id: string, code: string) {
    var barcode = document.getElementById(id),
      options = {
        format: "CODE128",
        displayValue: true,
        fontSize: 18,
        height: 100
      };
    JsBarcode(barcode, code, options);//原生
  }

  /* 打印 */
  print(): void {
    let printContents, popupWin;
    printContents = document.getElementById('print-section').innerHTML;
    popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
    popupWin.document.open();
    popupWin.document.write(`
      <html>
        <head>
          <title>Print Page</title>
          <style>
          </style>
        </head>
        <body onload="window.print();window.close()">${printContents}</body>
      </html>`
    );
    popupWin.document.close();
  }


  public toBarcodeJson(isbn: String): String {
    //如果包含-或不是13位,则返回
    if (isbn.indexOf("-") != -1 || isbn.length != 13) {
      return isbn;

    }
    let newstr = isbn.substring(3, isbn.length - 1);
    let sum = 0;
    for (let i = 0, j = 10; i < newstr.length; i++ , j--) {
      let a = parseInt(newstr.substring(i, i + 1));
      sum = sum + a * j;
    }
    //得到校验位
    let b = 11 - sum % 11; 
    let c= this.toisbn2(newstr + b);
    //System.out.println(c);
    return c;
  }
  public toisbn2(srcISBN: String): String {
    let retISBN = srcISBN;
    if (srcISBN.charAt(1) != '-') {
      switch (srcISBN.charAt(1)) {
        case '0':
          retISBN = srcISBN.charAt(0)
            + "-"
            + srcISBN.charAt(1)
            + srcISBN.charAt(2)
            + "-"
            + srcISBN.charAt(3)
            + srcISBN.charAt(4)
            + srcISBN.charAt(5)
            + srcISBN.charAt(6)
            + srcISBN.charAt(7)
            + srcISBN.charAt(8)
            + "-"
            + srcISBN.charAt(9);
          break;

        case '1':
        case '2':
        case '3':
          retISBN = srcISBN.charAt(0)
            + "-"
            + srcISBN.charAt(1)
            + srcISBN.charAt(2)
            + srcISBN.charAt(3)
            + "-"
            + srcISBN.charAt(4)
            + srcISBN.charAt(5)
            + srcISBN.charAt(6)
            + srcISBN.charAt(7)
            + srcISBN.charAt(8)
            + "-"
            + srcISBN.charAt(9);
          break;

        case '5':
          retISBN = srcISBN.charAt(0)
            + "-"
            + srcISBN.charAt(1)
            + srcISBN.charAt(2)
            + srcISBN.charAt(3)
            + srcISBN.charAt(4)
            + "-"
            + srcISBN.charAt(5)
            + srcISBN.charAt(6)
            + srcISBN.charAt(7)
            + srcISBN.charAt(8)
            + "-"
            + srcISBN.charAt(9);
          break;
        case '4':
        case '8':
          retISBN = srcISBN.charAt(0)
            + "-"
            + srcISBN.charAt(1)
            + srcISBN.charAt(2)
            + srcISBN.charAt(3)
            + srcISBN.charAt(4)
            + srcISBN.charAt(5)
            + "-"
            + srcISBN.charAt(6)
            + srcISBN.charAt(7)
            + srcISBN.charAt(8)
            + "-"
            + srcISBN.charAt(9);
          break;
        case '7'://add Wang .CD or DVD more
          retISBN = srcISBN.charAt(0)
            + "-"
            + srcISBN.charAt(1)
            + srcISBN.charAt(2)
            + srcISBN.charAt(3)
            + srcISBN.charAt(4)
            + "-"
            + srcISBN.charAt(5)
            + srcISBN.charAt(6)
            + srcISBN.charAt(7)
            + srcISBN.charAt(8)
            + "-"
            + srcISBN.charAt(9);
          break;
        default:
          retISBN = srcISBN;
          break;
      }
    }
    return retISBN;
  }

}

