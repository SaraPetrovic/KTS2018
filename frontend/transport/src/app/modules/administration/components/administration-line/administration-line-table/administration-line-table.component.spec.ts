import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationLineTableComponent } from './administration-line-table.component';

describe('LineTableComponent', () => {
  let component: AdministrationLineTableComponent;
  let fixture: ComponentFixture<AdministrationLineTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationLineTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationLineTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
