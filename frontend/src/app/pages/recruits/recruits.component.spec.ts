import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecruitsComponent } from './recruits.component';

describe('RecruitsComponent', () => {
  let component: RecruitsComponent;
  let fixture: ComponentFixture<RecruitsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecruitsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecruitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
