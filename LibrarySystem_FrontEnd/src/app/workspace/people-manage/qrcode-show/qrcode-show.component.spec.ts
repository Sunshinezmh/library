import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QrcodeShowComponent } from './qrcode-show.component';

describe('QrcodeShowComponent', () => {
  let component: QrcodeShowComponent;
  let fixture: ComponentFixture<QrcodeShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QrcodeShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QrcodeShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
