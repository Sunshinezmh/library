import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookDetailsManagerComponent } from './book-details-manager.component';

describe('BookDetailsManagerComponent', () => {
  let component: BookDetailsManagerComponent;
  let fixture: ComponentFixture<BookDetailsManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookDetailsManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookDetailsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
