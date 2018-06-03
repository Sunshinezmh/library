import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookInfoManagerComponent } from './book-info-manager.component';
import { BookInfoManagerService } from './book-info-manager.service';

describe('BookInfoManagerComponent', () => {
  let component: BookInfoManagerComponent;
  let fixture: ComponentFixture<BookInfoManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookInfoManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookInfoManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
