import { TestBed, inject } from '@angular/core/testing';

import { BookTypeManagerService } from './book-type-manager.service';


describe('BookTypeManagerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BookTypeManagerService]
    });
  });

  it('should ...', inject([BookTypeManagerService], (service: BookTypeManagerService) => {
    expect(service).toBeTruthy();
  }));
});
