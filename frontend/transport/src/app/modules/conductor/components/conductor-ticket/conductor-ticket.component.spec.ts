import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConductorTicketComponent } from './conductor-ticket.component';

describe('ConductorTicketComponent', () => {
  let component: ConductorTicketComponent;
  let fixture: ComponentFixture<ConductorTicketComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConductorTicketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConductorTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
