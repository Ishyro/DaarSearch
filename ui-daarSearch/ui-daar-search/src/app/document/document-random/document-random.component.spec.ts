import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentRandomComponent } from './document-random.component';

describe('DocumentRandomComponent', () => {
  let component: DocumentRandomComponent;
  let fixture: ComponentFixture<DocumentRandomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentRandomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentRandomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
