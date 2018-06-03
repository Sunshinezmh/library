import { Component, OnInit } from '@angular/core';
import{Router} from '@angular/router';

@Component({
  selector: 'app-qrcode-show',
  templateUrl: './qrcode-show.component.html',
  styleUrls: ['./qrcode-show.component.css']
})
export class QrcodeShowComponent implements OnInit {

  constructor(
    public router: Router
  ) { }

  ngOnInit() {
  }

  public Jump():void{     
    this.router.navigateByUrl("workspace/borrow");   
 }
}