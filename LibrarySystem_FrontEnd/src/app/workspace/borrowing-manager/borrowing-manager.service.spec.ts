import { TestBed, inject } from '@angular/core/testing';

import { BorrowingManagerService } from './borrowing-manager.service';

describe('BorrowingManagerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BorrowingManagerService]
    });
  });

  it('should ...', inject([BorrowingManagerService], (service: BorrowingManagerService) => {
    expect(service).toBeTruthy();
  }));
});
