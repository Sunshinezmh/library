import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowingManagerComponent } from './borrowing-manager.component';

describe('BorrowingManagerComponent', () => {
  let component: BorrowingManagerComponent;
  let fixture: ComponentFixture<BorrowingManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BorrowingManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BorrowingManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
