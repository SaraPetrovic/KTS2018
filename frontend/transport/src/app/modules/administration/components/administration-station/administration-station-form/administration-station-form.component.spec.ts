import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationStationFormComponent } from './administration-station-form.component';

describe('AdministrationStationFormComponent', () => {
  let component: AdministrationStationFormComponent;
  let fixture: ComponentFixture<AdministrationStationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationStationFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationStationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
