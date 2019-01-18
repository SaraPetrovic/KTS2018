import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationLineComponent } from './administration-line.component';

describe('AdministrationLineComponent', () => {
  let component: AdministrationLineComponent;
  let fixture: ComponentFixture<AdministrationLineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationLineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
