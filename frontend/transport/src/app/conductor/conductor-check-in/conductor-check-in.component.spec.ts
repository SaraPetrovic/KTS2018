import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConductorCheckInComponent } from './conductor-check-in.component';

describe('ConductorCheckInComponent', () => {
  let component: ConductorCheckInComponent;
  let fixture: ComponentFixture<ConductorCheckInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConductorCheckInComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConductorCheckInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
