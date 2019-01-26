import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LineTableComponent } from './line-table.component';

describe('LineTableComponent', () => {
  let component: LineTableComponent;
  let fixture: ComponentFixture<LineTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LineTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LineTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
