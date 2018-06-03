import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReserveInfoComponent } from './reserve-info.component';

describe('ReserveInfoComponent', () => {
  let component: ReserveInfoComponent;
  let fixture: ComponentFixture<ReserveInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReserveInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReserveInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
