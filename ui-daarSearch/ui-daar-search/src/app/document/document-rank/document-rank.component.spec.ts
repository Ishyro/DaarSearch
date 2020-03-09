import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentRankComponent } from './document-rank.component';

describe('DocumentRankComponent', () => {
  let component: DocumentRankComponent;
  let fixture: ComponentFixture<DocumentRankComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentRankComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentRankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
