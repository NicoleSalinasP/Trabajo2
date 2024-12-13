import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDeparmento } from '../deparmento.model';

@Component({
  standalone: true,
  selector: 'jhi-deparmento-detail',
  templateUrl: './deparmento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DeparmentoDetailComponent {
  deparmento = input<IDeparmento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
