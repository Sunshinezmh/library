import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarehuseMangerComponent } from './warehuse-manger.component';

describe('WarehuseMangerComponent', () => {
  let component: WarehuseMangerComponent;
  let fixture: ComponentFixture<WarehuseMangerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarehuseMangerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarehuseMangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
