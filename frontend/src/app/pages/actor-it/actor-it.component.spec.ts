import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActorItComponent } from './actor-it.component';

describe('ActorItComponent', () => {
  let component: ActorItComponent;
  let fixture: ComponentFixture<ActorItComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActorItComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActorItComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
