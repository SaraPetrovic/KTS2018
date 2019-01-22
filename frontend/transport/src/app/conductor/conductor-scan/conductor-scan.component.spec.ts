import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConductorScanComponent } from './conductor-scan.component';

describe('ConductorScanComponent', () => {
  let component: ConductorScanComponent;
  let fixture: ComponentFixture<ConductorScanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConductorScanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConductorScanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
