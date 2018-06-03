import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationManagerComponent } from './reservation-management.component';

describe('ReservationManagementComponent', () => {
  let component: ReservationManagerComponent;
  let fixture: ComponentFixture<ReservationManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
