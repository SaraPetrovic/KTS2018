import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ZoneFormComponent } from './zone-form.component';

describe('ZoneDialogComponent', () => {
  let component: ZoneFormComponent;
  let fixture: ComponentFixture<ZoneFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ZoneFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZoneFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
