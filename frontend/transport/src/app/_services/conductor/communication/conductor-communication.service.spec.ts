import { TestBed } from '@angular/core/testing';

import { ConductorCommunicationService } from './conductor-communication.service';

describe('ConductorCommunicationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConductorCommunicationService = TestBed.get(ConductorCommunicationService);
    expect(service).toBeTruthy();
  });
});
