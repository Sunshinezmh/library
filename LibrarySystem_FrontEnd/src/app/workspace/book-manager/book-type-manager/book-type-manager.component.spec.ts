import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookTypeManagerComponent } from './book-type-manager.component';

describe('BookTypeManagerComponent', () => {
  let component: BookTypeManagerComponent;
  let fixture: ComponentFixture<BookTypeManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookTypeManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookTypeManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
