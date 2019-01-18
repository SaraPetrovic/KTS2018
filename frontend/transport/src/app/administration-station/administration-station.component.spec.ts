import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationStationComponent } from './administration-station.component';

describe('AdministrationStationComponent', () => {
  let component: AdministrationStationComponent;
  let fixture: ComponentFixture<AdministrationStationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationStationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationStationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
