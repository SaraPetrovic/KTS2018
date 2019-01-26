import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationDocumentAcceptanceComponent } from './administration-document-acceptance.component';

describe('AdministrationDocumentAcceptanceComponent', () => {
  let component: AdministrationDocumentAcceptanceComponent;
  let fixture: ComponentFixture<AdministrationDocumentAcceptanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationDocumentAcceptanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationDocumentAcceptanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
