import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConductorCheckinComponent } from './conductor-checkin.component';

describe('ConductorCheckinComponent', () => {
  let component: ConductorCheckinComponent;
  let fixture: ComponentFixture<ConductorCheckinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConductorCheckinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConductorCheckinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
