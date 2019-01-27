import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationLineFormComponent } from './administration-line-form.component';

describe('AdministrationLineFormComponent', () => {
  let component: AdministrationLineFormComponent;
  let fixture: ComponentFixture<AdministrationLineFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationLineFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationLineFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
