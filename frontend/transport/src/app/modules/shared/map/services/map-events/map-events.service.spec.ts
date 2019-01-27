import { TestBed } from '@angular/core/testing';

import { MapEventsService } from './map-events.service';

describe('MapEventsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MapEventsService = TestBed.get(MapEventsService);
    expect(service).toBeTruthy();
  });
});
