import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationZoneComponent } from './administration-zone.component';

describe('AdministrationZoneComponent', () => {
  let component: AdministrationZoneComponent;
  let fixture: ComponentFixture<AdministrationZoneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationZoneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationZoneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
