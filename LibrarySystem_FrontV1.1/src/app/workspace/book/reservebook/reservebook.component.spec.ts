import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservebookComponent } from './reservebook.component';

describe('ReservebookComponent', () => {
  let component: ReservebookComponent;
  let fixture: ComponentFixture<ReservebookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservebookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservebookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
